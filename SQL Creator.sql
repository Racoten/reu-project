CREATE TABLE `Users` (
    `UserName` VARCHAR(255) NOT NULL,
    `Email` VARCHAR(255) UNIQUE,
    `PasswordHash` MEDIUMTEXT NOT NULL,
    `token` MEDIUMTEXT,
    `RegistrationDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`UserName`)
);

CREATE TABLE `AttackTypes` (
    `AttackTypeID` INT AUTO_INCREMENT,
    `AttackTypeName` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`AttackTypeID`)
);

CREATE TABLE `Questionnaires` (
    `QuestionnaireID` INT AUTO_INCREMENT,
    `UserName` VARCHAR(255),
    `AttackTypeID` INT,
    `Date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`QuestionnaireID`),
    FOREIGN KEY(`UserName`) REFERENCES `Users`(`UserName`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

CREATE TABLE `EmailQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `EmailQuestionsTargeted1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestionsTargeted1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `EmailQuestionsTargeted2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestionsTargeted2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `EmailQuestionsTargeted3` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestionsTargeted3` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `QuestionnaireQuestions` (
    `QuestionnaireID` INT,
    `QuestionID` INT,
    PRIMARY KEY(`QuestionnaireID`, `QuestionID`),
    FOREIGN KEY(`QuestionnaireID`) REFERENCES `Questionnaires`(`QuestionnaireID`)
);

CREATE TABLE `UserAnswers` (
    `QuestionnaireID` INT,
    `QuestionID` INT,
    `UserName` VARCHAR(255),
    `Answer` VARCHAR(255),
    PRIMARY KEY(`QuestionnaireID`, `QuestionID`, `UserName`),
    FOREIGN KEY(`QuestionnaireID`) REFERENCES `Questionnaires`(`QuestionnaireID`),
    FOREIGN KEY(`UserName`) REFERENCES `Users`(`UserName`)
);

CREATE TABLE `EmailRecommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `BrowserSecurityRecommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `EmailRecommendationsTargeted1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `BrowserSecurityRecommendationsTargeted1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `EmailRecommendationsTargeted2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `BrowserSecurityRecommendationsTargeted2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `EmailRecommendationsTargeted3` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `BrowserSecurityRecommendationsTargeted3` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `EGQuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `EmailQuestions`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `EmailRecommendations`(`RecommendationID`)
);

CREATE TABLE `ET1QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `EmailQuestionsTargeted1`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `EmailRecommendationsTargeted1`(`RecommendationID`)
);

CREATE TABLE `ET2QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `EmailQuestionsTargeted2`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `EmailRecommendationsTargeted2`(`RecommendationID`)
);

CREATE TABLE `BSGQuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `BrowserSecurityQuestions`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `BrowserSecurityRecommendations`(`RecommendationID`)
);

CREATE TABLE `BST1QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `BrowserSecurityQuestionsTargeted1`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `BrowserSecurityRecommendationsTargeted1`(`RecommendationID`)
);

CREATE TABLE `BST2QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `BrowserSecurityQuestionsTargeted2`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `BrowserSecurityRecommendationsTargeted2`(`RecommendationID`)
);

CREATE TABLE `SMSQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `SMSTargetedQuestions1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `SMSTargetedQuestions2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `SMSRecommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `SMSTargetedRecommendations1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `SMSTargetedRecommendations2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `SMGQuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `SMSQuestions`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `SMSRecommendations`(`RecommendationID`)
);

CREATE TABLE `ST1QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `SMSTargetedQuestions1`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `SMSTargetedRecommendations1`(`RecommendationID`)
);

CREATE TABLE `ST2QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `SMSTargetedQuestions2`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `SMSTargetedRecommendations2`(`RecommendationID`)
);
----------------------------------------------------------------------------------------------------

CREATE TABLE `WiFiQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `WiFiTargetedQuestions1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `WiFiTargetedQuestions2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    `Weight` INT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `WiFiRecommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `WiFiTargetedRecommendations1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `WiFiTargetedRecommendations2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`)
);

CREATE TABLE `WFGQuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `WiFiQuestions`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `WiFiRecommendations`(`RecommendationID`)
);

CREATE TABLE `WT1QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `WiFiTargetedQuestions1`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `WiFiTargetedRecommendations1`(`RecommendationID`)
);

CREATE TABLE `WT2QuestionRecommendation` (
    `QuestionID` INT,
    `RecommendationID` INT,
    PRIMARY KEY(`QuestionID`, `RecommendationID`),
    FOREIGN KEY(`QuestionID`) REFERENCES `WiFiTargetedQuestions2`(`QuestionID`),
    FOREIGN KEY(`RecommendationID`) REFERENCES `WiFiTargetedRecommendations2`(`RecommendationID`)
);