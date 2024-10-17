import React, { useState, useEffect } from "react";

export const QuestionsPage = ({ categories, difficulties, quizId }) => {
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

    const handleNextQuestion = () => {
        const isCorrect = selectedAnswer === questions[currentQuestionIndex].correctAnswer;
        if (isCorrect) {
            setScore((prevScore) => prevScore + 1); // TODO: Implement dynamic percentage score for when they are taking the quiz
        }
        setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
        setSelectedAnswer(null);
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

    // TODO: Fix selecting the correct button will mark as correct even if it is not the final answer that was selected
    return (
        <div className="questions-page">
            <div className="question-container">
                {currentQuestion ? (
                    <div className="question">
                        <p>{currentQuestion.question.text}</p>
                        <div className="options">
                            {/* TODO: Mix up ordering of options */}
                            {[...currentQuestion.incorrectAnswers, currentQuestion.correctAnswer].map((option, i) => (
                                <div key={i}>
                                    <button onClick={() => handleAnswerClick(option)}>
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
