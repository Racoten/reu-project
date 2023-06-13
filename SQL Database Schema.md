```sql
CREATE DATABASE `cybersafe`;

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
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestions` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
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
    `UserID` INT,
    `Answer` VARCHAR(255),
    PRIMARY KEY(`QuestionnaireID`, `QuestionID`, `UserID`),
    FOREIGN KEY(`QuestionnaireID`) REFERENCES `Questionnaires`(`QuestionnaireID`),
    FOREIGN KEY(`UserID`) REFERENCES `Users`(`UserID`)
);

CREATE TABLE `Recommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `AttackTypeID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`AttackTypeID`) REFERENCES `AttackTypes`(`AttackTypeID`)
);

-- Inserting questions for Email Security
INSERT INTO `EmailQuestions` (`QuestionText`) VALUES
('Are there any malicious links in the email?'),
('Is the sender address legitimate?'),
('Is the email asking you to do anything that would lead to account compromise?');

-- Inserting questions for Browser Security
INSERT INTO `BrowserSecurityQuestions` (`QuestionText`) VALUES
('Is the user currently on an open wifi network?'),
('Is the user on secure websites (https)?'),
('Do you notice a typo in the domain part of the URL?'),
('Is the structure of the page sort of dismantled or out of place?'),
('Are there any advertisements that redirect to an unfamiliar site?');

-- Inserting recommendations
-- The first two attack types are related to email and browser security respectively. You should modify the recommendations and attack types as needed.
INSERT INTO `Recommendations` (`AttackTypeID`, `RecommendationText`) VALUES
(1, 'Avoid clicking on unfamiliar links in emails, even if they appear to be from a known contact.'),
(1, 'Always check the sender address of the email. If it looks suspicious, do not interact with the email.'),
(1, 'Never share your personal or financial information through email unless you are absolutely certain about the recipient\'s identity.'),
(1, 'Be cautious about opening attachments in emails, especially if they are from unknown senders. They may contain malware.'),
(1, 'Always check the domain of the login link in the email. Make sure it matches exactly with the known domain of the website.'),

(2, 'Avoid using open wifi networks for sensitive activities as they are usually not secure.'),
(2, 'Always make sure the website is secure (https) before entering any sensitive information.'),
(2, 'Always double-check the domain of the website you are visiting. A typo in the domain could mean that the site is fake.'),
(2, 'If the page layout seems out of place or dismantled, it could be a sign that the site has been tampered with.'),
(2, 'Be cautious about clicking on advertisements. They might redirect you to malicious sites.');
```
