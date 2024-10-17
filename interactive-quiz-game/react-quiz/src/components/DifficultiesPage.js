import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const DifficultiesPage = ({userId, categories, setDifficulties, setQuizId}) => {
    const [difficulties, setDifficultiesLocal] = useState("");
    const navigate = useNavigate();

    const handleClick = (difficulties) => {
        setDifficultiesLocal(difficulties);
        console.log(`Local difficulty: ${difficulties}`);
    }

    const handleSubmit = async () => {
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

    return (
        <div>
            <div>
                Pick a difficulty:
                <button onClick={() => handleClick("easy")}>Easy</button>
                <button onClick={() => handleClick("medium")}>Medium</button>
                <button onClick={() => handleClick("hard")}>Hard</button>
            </div>
            <button onClick={handleSubmit}>Submit</button>
        </div>
    );
};
