import React, { useState, useEffect } from "react";
import "./Buttons.css";

export const QuestionsPage = ({ categories, difficulties, userId, quizId }) => {
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [selectedAnswer, setSelectedAnswer] = useState(null);
    const [score, setScore] = useState(0);

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

    const handleAnswerClick = (selectedOption) => {
        setSelectedAnswer(selectedOption);
        console.log(`Selected answer: ${selectedOption}`);
    };

    const handleNextQuestion = async () => {
        const isCorrect = selectedAnswer === questions[currentQuestionIndex].correctAnswer;
        
        let optionId = null;

        if (isCorrect) {
            setScore((prevScore) => prevScore + 1); // TODO: Implement dynamic percentage score for when they are taking the quiz
            console.log(`Current question ID: ${currentQuestion.correctAnswerId}`);
            optionId = currentQuestion.correctAnswerId;
        } else {
            const index = currentQuestion.incorrectAnswers.indexOf(selectedAnswer);
            console.log(`Index: ${index}`);
            optionId = currentQuestion.incorrectAnswersIds[index];
        }

        setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
        setSelectedAnswer(null);

        console.log(`UserId: ${userId}, QuizId: ${quizId}, QuestionId: ${currentQuestion.questionId}, OptionId: ${optionId}, isCorrect: ${isCorrect}`);

        try {
            const response = await fetch("http://localhost:8080/responses", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    userId: userId,
                    quizId: quizId,
                    questionId: currentQuestion.questionId, //
                    optionId: optionId, //
                    isCorrect: isCorrect,
                }),
            });

            if (!response.ok) {
                console.error("Failed to submit user response:", response.statusText);
            }
        } catch (error) {

        }
    };

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
                {currentQuestionIndex > 0 && currentQuestionIndex < 10 ? (
                    <div>
                        <p>Current score: {score} out of {currentQuestionIndex}</p>
                    </div>
                ) : (
                    <div>
                    </div>
                )}
                {currentQuestion ? (
                    <div className="question">
                        <p>{currentQuestion.question.text}</p>
                        <div className="options">
                            {[...currentQuestion.incorrectAnswers, currentQuestion.correctAnswer].map((option, i) => (
                                <div key={i}>
                                    <button onClick={() => handleAnswerClick(option, i)}
                                        className={selectedAnswer === option ? "selected" : ""}>
                                        {option}
                                    </button>
                                </div>
                            ))}
                        </div>
                        {selectedAnswer !== null && (
                            <button onClick={handleNextQuestion}>
                                {currentQuestionIndex === questions.length - 1 ? "Finish Quiz" : "Next Question"}
                            </button>
                        )}
                    </div>
                ) : (
                    <div>
                        <h3>Quiz Completed!</h3>
                        <p>Your score: {score} out of {questions.length}</p>
                    </div>
                )}
            </div>
        </div>
    );
};
