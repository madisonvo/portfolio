import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Pages.css";

export const CategoriesPage = ({ setCategories }) => {
    const [categories, setCategoriesLocal] = useState(null);
    const navigate = useNavigate();

    const handleClick = (categories) => {
        const formattedCategories = categories.toLowerCase().replaceAll(" ", "_");
        setCategoriesLocal(formattedCategories);
        console.log(`Local category: ${formattedCategories}`);
    }

    const handleSubmit = () => {
        if (categories === null) {
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
            <div>
                {allCategories.slice(0, 3).map((category, i) => (
                    <button key={i} onClick={() => handleClick(category)}
                        className={categories === category.toLowerCase().replaceAll(" ", "_") ? "selected" : ""}
                        id="first-row">
                        {category}
                    </button>
                ))}
            </div>
            <div>
                {allCategories.slice(3, 7).map((category, i) => (
                    <button key={i + 3} onClick={() => handleClick(category)}
                        className={categories === category.toLowerCase().replaceAll(" ", "_") ? "selected" : ""}
                        id="second-row">
                        {category}
                    </button>
                ))}
            </div>
            <div>
                {allCategories.slice(7, 10).map((category, i) => (
                    <button key={i + 7} onClick={() => handleClick(category)}
                        className={categories === category.toLowerCase().replaceAll(" ", "_") ? "selected" : ""}
                        id="third-row">
                        {category}
                    </button>
                ))}
            </div>
            <button onClick={handleSubmit} id="submit">Next</button>
        </div>
    );
};
