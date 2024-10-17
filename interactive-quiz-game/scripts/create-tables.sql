CREATE DATABASE IF NOT EXISTS quizdatabase;
USE quizdatabase;

DROP TABLE IF EXISTS UserResponse;
DROP TABLE IF EXISTS `Option`;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Quiz;
DROP TABLE IF EXISTS User;

CREATE TABLE User (
	userId INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (userId)
);

INSERT INTO User (username) VALUES (
	"Madison"
);
INSERT INTO User (username) VALUES (
	"Jenan"
);
INSERT INTO User (username) VALUES (
	"Tariq"
);

CREATE TABLE Quiz (
	quizId INT NOT NULL AUTO_INCREMENT,
    userId INT NOT NULL,
    category VARCHAR(255) NOT NULL,
    difficulty VARCHAR(6) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (quizId),
    FOREIGN KEY (userId) REFERENCES User(userId)
);

INSERT INTO Quiz (userId, category, difficulty, score) VALUES (
	1,
    "music",
    "easy",
    85
);
INSERT INTO Quiz (userId, category, difficulty, score) VALUES (
	2,
    "sport_and_leisure",
    "medium",
    70
);
INSERT INTO Quiz (userId, category, difficulty, score) VALUES (
	3,
    "film_and_tv",
    "hard",
    65
);

CREATE TABLE Question (
	questionId INT NOT NULL AUTO_INCREMENT,
    quizId INT NOT NULL,
    difficulty VARCHAR(6) NOT NULL,
    question VARCHAR(255) NOT NULL,
    PRIMARY KEY (questionId),
    FOREIGN KEY (quizId) REFERENCES Quiz(quizId)
);

INSERT INTO Question (quizId, difficulty, question) VALUES (
	1,
    "easy",
    "Who sang the song Fireworks?"
);
INSERT INTO Question (quizId, difficulty, question) VALUES (
	2,
    "medium",
    "What sport is considered the most popular in the world?"
);
INSERT INTO Question (quizId, difficulty, question) VALUES (
	3,
    "hard",
    "What film was released in 2021 and directed by Denis Villeneuve?"
);

CREATE TABLE `Option` (
	optionId INT NOT NULL AUTO_INCREMENT,
    questionId INT NOT NULL,
    `option` VARCHAR(255) NOT NULL,
    isCorrect BOOLEAN NOT NULL,
    PRIMARY KEY(optionId),
    FOREIGN KEY (questionId) REFERENCES Question(questionId)
);

INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	1,
    "Katy Perry",
    1
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	1,
    "Taylor Swift",
    0
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	1,
    "Kesha",
    0
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	2,
    "Football",
    1
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	2,
    "Basketball",
    0
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	2,
    "Tennis",
    0
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	3,
    "It",
    0
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	3,
    "Dune: Part One",
    1
);
INSERT INTO `Option` (questionId, `option`, isCorrect) VALUES (
	3,
    "Get Out",
    0
);

CREATE TABLE UserResponse (
	userResponseId INT NOT NULL AUTO_INCREMENT,
    userId INT NOT NULL,
    questionId INT NOT NULL,
    optionId INT NOT NULL,
    isCorrect BOOLEAN NOT NULL,
    PRIMARY KEY (userResponseId),
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (questionId) REFERENCES Question(questionId),
    FOREIGN KEY (optionId) REFERENCES `Option`(optionId)
);

INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	1,
    1,
    2,
    0
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	1,
    2,
    4,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	1,
    3,
    8,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	2,
    1,
    1,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	2,
    2,
    4,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	2,
    3,
    8,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	3,
    1,
    2,
    0
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	3,
    2,
    4,
    1
);
INSERT INTO UserResponse (userId, questionId, optionId, isCorrect) VALUES (
	3,
    3,
    9,
    0
);


