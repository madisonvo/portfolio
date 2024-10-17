import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export const CategoriesPage = ({setCategories}) => {
    const [categories, setCategoriesLocal] = useState("");
    const navigate = useNavigate();

    const handleClick = (categories) => {
        setCategoriesLocal(categories);
        console.log(`Local category: ${categories}`);
    }

    const handleSubmit = () => {
        setCategories(categories);
        console.log(`Category: ${categories}`);
        navigate("/difficulties");
    }

    return (
        <div className="categories-page">
            Pick a category:
            <div className="button-container1">
                <button onClick={() => handleClick("music")}>Music</button>
                <button onClick={() => handleClick("sport_and_leisure")}>Sport and Leisure</button>
                <button onClick={() => handleClick("film_and_tv")}>Film and TV</button>
            </div>
            <div className="button-container2">
                <button onClick={() => handleClick("arts_and_literature")}>Arts and Literature</button>
                <button onClick={() => handleClick("history")}>History</button>
                <button onClick={() => handleClick("society_and_culture")}>Society and Culture</button>
                <button onClick={() => handleClick("science")}>Science</button>
            </div>
            <div className="button-container2">
                <button onClick={() => handleClick("geography")}>Geography</button>
                <button onClick={() => handleClick("food_and_drink")}>Food and Drink</button>
                <button onClick={() => handleClick("general_knowledge")}>General Knowledge</button>
            </div>
            <button onClick={handleSubmit}>Next</button>
        </div>
    );
};
