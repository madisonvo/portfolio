import React, {useState} from "react";
import "./App.css";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {NavBar} from "./components/NavBar";
import {UsernamePage} from "./components/UsernamePage";
import {CategoriesPage} from "./components/CategoriesPage";
import {DifficultiesPage} from "./components/DifficultiesPage";
import {QuestionsPage} from "./components/QuestionsPage";

function App() {
  // eslint-disable-next-line no-unused-vars
  const [username, setUsername] = useState("");
  const [userId, setUserId] = useState(null);
  const [categories, setCategories] = useState("");
  const [difficulties, setDifficulties] = useState("");
  const [quizId, setQuizId] = useState(null);

  return (
    <Router>
      <div>
        <NavBar/>
        <Routes>
          <Route path="/" element={<UsernamePage setUsername={setUsername} setUserId={setUserId} />} />
          <Route path="/categories" element={<CategoriesPage setCategories={setCategories} />} />
          <Route path="/difficulties" element={<DifficultiesPage userId={userId} categories={categories} setDifficulties={setDifficulties} setQuizId={setQuizId} />} />
          <Route path="/questions" element={<QuestionsPage categories={categories} difficulties={difficulties} userId={userId} quizId={quizId} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
