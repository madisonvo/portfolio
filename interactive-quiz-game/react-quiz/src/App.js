import React, {useState} from "react";
import "./App.css";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {NavBar} from "./components/NavBar";
import {UsernamePage} from "./components/UsernamePage";
import {CategoriesPage} from "./components/CategoriesPage";
import {DifficultiesPage} from "./components/DifficultiesPage";
import {QuestionsPage} from "./components/QuestionsPage";
import backgroundVideo from "./images/question-mark.mp4";

function App() {
  const [userId, setUserId] = useState(null);
  const [categories, setCategories] = useState("");
  const [difficulties, setDifficulties] = useState("");
  const [quizId, setQuizId] = useState(null);

  return (
    <Router>
      <video id="background-video" autoPlay loop muted>
          <source src={backgroundVideo} type="video/mp4"/>
      </video>
      <div className="main-content">
        <NavBar/>
        <Routes>
          <Route path="/" element={<UsernamePage setUserId={setUserId} />} />
          <Route path="/categories" element={<CategoriesPage setCategories={setCategories} />} />
          <Route path="/difficulties" element={<DifficultiesPage userId={userId} categories={categories} setDifficulties={setDifficulties} setQuizId={setQuizId} />} />
          <Route path="/questions" element={<QuestionsPage categories={categories} difficulties={difficulties} userId={userId} quizId={quizId} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
