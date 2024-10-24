import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Pages.css";

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
    const [buttonColors, setButtonColors] = useState({});
    const [answered, setAnswered] = useState(false);
    const [reviewing, setReviewing] = useState(false);

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
            setButtonColors({});
            setAnswered(false);
        }
    }, [currentQuestionIndex, questions]);

    useEffect(() => {
        if (timeLeft === 0 && !answered) {
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
        const currentQuestion = questions[currentQuestionIndex];
        const isCorrect = finalAnswer === currentQuestion.correctAnswer;

        const newButtonColors = shuffledOptions.reduce((acc, option) => {
            if (option === currentQuestion.correctAnswer) {
                acc[option] = "green";
            } else if (option === finalAnswer) {
                acc[option] = isCorrect ? "green" : "red";
            } else {
                acc[option] = "";
            }
            return acc;
        }, {});

        setButtonColors(newButtonColors);
        setAnswered(true);
        setReviewing(true);

        setTimeout(() => {
            handleNextQuestion();
            setReviewing(false);
        }, isCorrect ? 1000 : 2000);
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
        return <div style={{color: "white"}}>Loading questions...</div>;
    }

    if (error) {
        return <div style={{color: "white"}}>Error: {error}</div>;
    }

    const currentQuestion = questions[currentQuestionIndex];

    return (
        <div className="questions-page">
            <div className="current-score">
                {currentQuestionIndex > 0 && currentQuestionIndex < 10 && (
                    <div>
                        <p>Current score: {score}/{currentQuestionIndex}</p>
                    </div>
                )}
            </div>
            {quizCompleted ? (
                <div className="quiz-completed">
                    <h3>Quiz Completed!</h3>
                    <p>Your score: {score}/{questions.length}</p>
                    <button onClick={(() => navigate("/"))} id="quiz-completed">Home</button>
                    <button onClick={handleRestartQuiz} id="quiz-completed">Start Another Quiz</button>
                </div>
            ) : (
                currentQuestion && (
                    <div className="question">
                        <div className="current-question">
                            {currentQuestion.question.text}
                        </div>
                        <div>
                            {shuffledOptions.slice(0, 2).map((option, i) => (
                                <button key={i} onClick={() => handleAnswerClick(option)}
                                    className={selectedAnswerLocal === option ? "selected" : ""}
                                    id="first-row"
                                    style={{backgroundColor: buttonColors[option], 
                                        border: buttonColors[option]}}
                                    disabled={answered}>
                                    {option}
                                </button>
                            ))}
                        </div>
                        <div>
                            {shuffledOptions.slice(2, 4).map((option, i) => (
                                <button key={i + 2} onClick={() => handleAnswerClick(option)}
                                    className={selectedAnswerLocal === option ? "selected" : ""}
                                    id="first-row"
                                    style={{backgroundColor: buttonColors[option],
                                        border: buttonColors[option]}}
                                    disabled={answered}>
                                    {option}
                                </button>
                            ))}
                        </div>
                        {selectedAnswerLocal !== null && !reviewing && (
                            <button onClick={handleSubmit} id="submit">
                                {currentQuestionIndex === questions.length - 1 ? "Finish Quiz" : "Submit"}
                            </button>
                        )}
                        <div className="timer">
                            {!reviewing && (timeLeft > 9 ? <p>Time Left: 0:{timeLeft}</p> : <p>Time Left: 0:0{timeLeft}</p>)}
                        </div>
                    </div>
                )
            )}
        </div>
    );
};
