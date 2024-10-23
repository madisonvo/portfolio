import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Buttons.css";

export const CategoriesPage = ({setCategories}) => {
    const [categories, setCategoriesLocal] = useState(null);
    const navigate = useNavigate();

    const handleClick = (categories) => {
        const formattedCategories = categories.toLowerCase().replaceAll(" ", "_");
        setCategoriesLocal(formattedCategories);
        console.log(`Local category: ${formattedCategories}`);
    }

    const handleSubmit = () => {
        if (categories === "") {
            alert("You need to choose a category!");
            return;
        }

        const formattedCategories = categories.toLowerCase().replaceAll(" ", "_");
        setCategories(formattedCategories);
        console.log(`Category: ${formattedCategories}`);
        navigate("/difficulties");
    }

    const allCategories = ["Music", "Sport and Leisure", "Film and TV", 
        "Arts and Literature", "History", "Society and culture", 
        "Science", "Geography", "Food and Drink", "General Knowledge"];

    return (
        <div className="categories-page">
            Pick a category:
            {allCategories.map((category, i) => (
                <div key={i}>
                    <button onClick={() => handleClick(category)}
                        className={categories === category.toLowerCase().replaceAll(" ", "_") ? "selected" : ""}>
                        {category}
                    </button>
                </div>
            ))}
            <button onClick={handleSubmit}>Next</button>
        </div>
    );
};
