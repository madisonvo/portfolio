import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Pages.css";

export const UsernamePage = ({ setUserId }) => {
    const [username, setUsernameLocal] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (username.trim() === "") {
            alert("Username cannot be empty!");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    username: username,
                }),
            });

            const userId = await response.json();
            setUserId(userId);
            console.log(`Username: ${username}, userId: ${userId}`);

            navigate("/categories");
        } catch (error) {
            console.error("Error inserting user: ", error);
        }
    };

    return (
        <div className="starter-page">
            <form onSubmit={handleSubmit} name="username-form">
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsernameLocal(e.target.value)}
                    placeholder="Enter your username"/>
                <button type="submit">Next</button>
            </form>
        </div>
    );
};
