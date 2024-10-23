import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Buttons.css";

export const QuestionsPage = ({ categories, difficulties, userId, quizId }) => {
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0); 
    const [selectedAnswerLocal, setSelectedAnswerLocal] = useState(null);
    const [score, setScore] = useState(0);
    const [timeLeft, setTimeLeft] = useState(30);
    const [quizCompleted, setQuizCompleted] = useState(false);
    const [shuffledOptions, setShuffledOptions] = useState([]);

    let finalAnswer = null;
    const navigate = useNavigate();

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const response = await fetch(`http://localhost:8080/questions?categories=${categories}&difficulties=${difficulties}&quizId=${quizId}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch questions");
                }

                const data = await response.json();
                setQuestions(data);
                setLoading(false);
            } catch (err) {
                console.error("Error fetching questions:", err);
                setError(err.message);
                setLoading(false);
            }
        };

        fetchQuestions();
    }, [categories, difficulties, quizId]);

    useEffect(() => {
        if (questions.length > 0 && currentQuestionIndex < questions.length) {
            const currentQuestion = questions[currentQuestionIndex];
            const allOptions = [...currentQuestion.incorrectAnswers, currentQuestion.correctAnswer];

            const shuffled = allOptions.sort(() => Math.random() - 0.5);
            setShuffledOptions(shuffled);
        }
    }, [currentQuestionIndex, questions]);

    useEffect(() => {
        if (timeLeft === 0) {
            handleNextQuestion();
        }

        const timer = setInterval(() => {
            setTimeLeft((prevTimeLeft) => (prevTimeLeft > 0 ? prevTimeLeft - 1 : 0));
        }, 1000);

        return () => clearInterval(timer);
    });

    const handleAnswerClick = (selectedOption) => {
        setSelectedAnswerLocal(selectedOption);
        console.log(`Selected answer: ${selectedOption}`);
    };

    const handleSubmit = () => {
        console.log(`Local answer: ${selectedAnswerLocal}`);
        finalAnswer = selectedAnswerLocal;
        handleNextQuestion();
    }

    const handleNextQuestion = async () => {
        console.log(finalAnswer);
        const currentQuestion = questions[currentQuestionIndex];
        if (!currentQuestion) return;

        const isCorrect = finalAnswer === currentQuestion.correctAnswer;
        let optionId = null;

        if (isCorrect) {
            setScore((prevScore) => prevScore + 1);
            optionId = currentQuestion.correctAnswerId;
        } else {
            const index = currentQuestion.incorrectAnswers.indexOf(finalAnswer);
            if (index !== -1) {
                optionId = currentQuestion.incorrectAnswersIds[index];
            }
        }

        console.log(`UserId: ${userId}, QuizId: ${quizId}, QuestionId: ${currentQuestion.questionId}, OptionId: ${optionId}, isCorrect: ${isCorrect}`);

        try {
            await fetch("http://localhost:8080/responses", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    userId: userId,
                    quizId: quizId,
                    questionId: currentQuestion.questionId,
                    optionId: (optionId != null) ? optionId : "",
                    isCorrect: isCorrect,
                }),
            });
        } catch (error) {
            console.error("Error submitting user response:", error);
        }

        if (currentQuestionIndex === questions.length - 1) {
            const finalScore = score + (isCorrect ? 1 : 0);
            setQuizCompleted(true);
            try {
                await fetch("http://localhost:8080/scores", {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    body: new URLSearchParams({
                        quizId: quizId,
                        score: finalScore,
                    }),
                });
            } catch (error) {
                console.error("Error updating score:", score);
            }
        }

        setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
        setSelectedAnswerLocal(null);
        setTimeLeft(30);
    };

    const handleRestartQuiz = () => {
        navigate("/categories");
    }

    if (loading) {
        return <div>Loading questions...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (questions.length === 0) {
        return <div>No questions available.</div>;
    }

    const currentQuestion = questions[currentQuestionIndex];

    return (
        <div className="questions-page">
            <div className="question-container">
                {currentQuestionIndex > 0 && currentQuestionIndex < 10 && (
                    <div>
                        <p>Current score: {score} out of {currentQuestionIndex}</p>
                    </div>
                )}
                {quizCompleted ? (
                    <div>
                        <h3>Quiz Completed!</h3>
                        <p>Your score: {score} out of {questions.length}</p>
                        <button onClick={handleRestartQuiz}>Start Another Quiz</button>
                        <button onClick={(() => navigate("/"))}>Home</button>
                    </div>
                ) : (
                    currentQuestion && (
                        <div className="question">
                            <p>{currentQuestion.question.text}</p>
                            <div className="options">
                                {shuffledOptions.map((option, i) => (
                                    <div key={i}>
                                        <button onClick={() => handleAnswerClick(option)}
                                            className={selectedAnswerLocal === option ? "selected" : ""}>
                                            {option}
                                        </button>
                                    </div>
                                ))}
                            </div>
                            {selectedAnswerLocal !== null && (
                                <button onClick={handleSubmit}>
                                    {currentQuestionIndex === questions.length - 1 ? "Finish Quiz" : "Next Question"}
                                </button>
                            )}
                            {timeLeft > 9 ? <p>Time Left: 0:{timeLeft}</p> : <p>Time Left: 0:0{timeLeft}</p>}
                        </div>
                    )
                )}
            </div>
        </div>
    );
};
