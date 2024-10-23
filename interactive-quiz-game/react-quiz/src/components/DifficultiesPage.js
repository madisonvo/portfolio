import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Pages.css";

export const DifficultiesPage = ({userId, categories, setDifficulties, setQuizId}) => {
    const [difficulties, setDifficultiesLocal] = useState(null);
    const navigate = useNavigate();

    const handleClick = (difficulties) => {
        setDifficultiesLocal(difficulties);
        console.log(`Local difficulty: ${difficulties}`);
    }

    const handleSubmit = async () => {
        if (difficulties === null) {
            alert("You need to choose a difficulty!");
            return;
        }

        setDifficulties(difficulties);
        console.log(`Difficulty: ${difficulties}`);
        
        try {
            const response = await fetch("http://localhost:8080/quizzes", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    categories: categories,
                    difficulties: difficulties,
                    userId: userId,
                }),
            });

            const quizId = await response.json();
            setQuizId(quizId);
            console.log(`QuizId: ${quizId}`);
            
            navigate("/questions");
        } catch (error) {
            console.error("Error submitting quiz:", error);
        }
    }

    const allDifficulties = ["easy", "medium", "hard"];

    return (
        <div className="difficulties-page">
            Pick a difficulty:
            <div>
                {allDifficulties.map((difficulty, i) => (
                        <button key={i} onClick={() => handleClick(difficulty)}
                            className={difficulties === difficulty ? "selected" : ""}
                            id="difficulty">
                            {difficulty.charAt(0).toUpperCase() + difficulty.slice(1)}
                        </button>
                ))}
            </div>
            <button onClick={handleSubmit} id="submit">Submit</button>
        </div>
    );
};
