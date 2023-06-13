```sql
CREATE DATABASE `cybersafe`;

use `cybersafe`;

CREATE TABLE `Users` (
    `UserID` INT AUTO_INCREMENT,
    `UserName` VARCHAR(255) NOT NULL,
    `Email` VARCHAR(255) UNIQUE,
    `PasswordHash` MEDIUMTEXT NOT NULL,
    `RegistrationDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`UserID`)
);

CREATE TABLE `AttackTypes` (
    `AttackTypeID` INT AUTO_INCREMENT,
    `AttackTypeName` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`AttackTypeID`)
);

CREATE TABLE `Questionnaires` (
    `QuestionnaireID` INT AUTO_INCREMENT,
    `UserID` INT,
    `AttackTypeID` INT,
    `Date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`QuestionnaireID`),
    FOREIGN KEY(`UserID`) REFERENCES `Users`(`UserID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `EmailQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `AttackTypeID` INT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `BrowserSecurityQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `AttackTypeID` INT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `QuestionnaireQuestions` (
    `QuestionnaireID` INT,
    `QuestionID` INT,
    `AttackTypeID` INT,
    PRIMARY KEY(`QuestionnaireID`, `QuestionID`, `AttackTypeID`),
    FOREIGN KEY(`QuestionnaireID`) REFERENCES `Questionnaires`(`QuestionnaireID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `UserAnswers` (
    `QuestionnaireID` INT,
    `QuestionID` INT,
    `AttackTypeID` INT,
    `UserID` INT,
    `Answer` VARCHAR(255),
    PRIMARY KEY(`QuestionnaireID`, `QuestionID`, `AttackTypeID`, `UserID`),
    FOREIGN KEY(`QuestionnaireID`) REFERENCES `Questionnaires`(`QuestionnaireID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`),
    FOREIGN KEY(`UserID`) REFERENCES `Users`(`UserID`)
);

CREATE TABLE `Recommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `AttackTypeID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `QuestionRecommendations` (
    `QuestionID` INT,
    `AttackTypeID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `AttackTypeID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `EmailQuestions`(`QuestionID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `Recommendations`(`RecommendationID`)
);


-- Add the attack type
INSERT INTO `AttackTypes` (`AttackTypeName`) VALUES 
('Email Security'),
('Browser Security');

-- Add the questions
INSERT INTO `EmailQuestions` (`AttackTypeID`, `QuestionText`) VALUES 
(1, 'Are there any malicious links?'),
(1, 'Is the sender address legitimate?'),
(1, 'Is the email asking you to do anything that would lead to account compromise?');

INSERT INTO `BrowserSecurityQuestions` (`AttackTypeID`, `QuestionText`) VALUES 
(2, 'Is the user currently on an open wifi network?'),
(2, 'Is the user on secure websites (https)?'),
(2, 'Do you notice a typo on the domain part of the URL?');

-- Add the recommendations
INSERT INTO `Recommendations` (`AttackTypeID`, `RecommendationText`) VALUES 
(1, 'Avoid clicking on the links from untrusted emails.'),
(1, 'Double check the sender email address for any irregularities.'),
(1, 'Do not provide any personal information or password in response to an email.');

INSERT INTO `Recommendations` (`AttackTypeID`, `RecommendationText`) VALUES 
(2, 'Avoid using open wifi networks for sensitive activities.'),
(2, 'Always ensure you are on a secure website when submitting sensitive information.'),
(2, 'Check the URL for typos to avoid phishing attacks.');

-- Link the questions to the recommendations
INSERT INTO `QuestionRecommendations` (`QuestionID`, `AttackTypeID`, `RecommendationID`) VALUES 
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(1, 2, 4),
(2, 2, 5),
(3, 2, 6);
```
