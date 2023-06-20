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

INSERT INTO `EmailQuestions` (`QuestionText`) VALUES 
('Are there any links in the email body? Yes/No/Maybe'),
('Is this spam - are you expecting an email from this source? Yes/No/Maybe');

INSERT INTO `BrowserSecurityQuestions` (`QuestionText`) VALUES 
('Is the user currently on an open wifi network? Yes/No'),
('Are there any suspicious elements/out of place components on the page? Yes/No/Maybe');


CREATE TABLE `EmailQuestionsTargeted1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `EmailQuestionsTargeted2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestionsTargeted1` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);

CREATE TABLE `BrowserSecurityQuestionsTargeted2` (
    `QuestionID` INT AUTO_INCREMENT,
    `QuestionText` TEXT NOT NULL,
    PRIMARY KEY(`QuestionID`)
);


-- Insert targeted questions into corresponding tables
INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`) VALUES 
('Does the URL use https?'),
('Top level domain - is it a familiar extension (.com, .edu, .net, .mil), foreign/uncommon TLDs could be red flag'),
('Second level domain - is it of a known company/org., is it misspelled?'),
('If Path - What type of file does it appear to open - pdf or image more benign, scripts could be malicious (.php, .js, etc.)'),
('Query strings - do any of these parameters have a username, password, or other piece of account information in them (sign query params will be used to transfer data to redirect link)'),
('Parts of URL can be highlighted so the user knows which parts to look at');

INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`) VALUES 
('Is the email asking to give up sensitive information (financial, SSN, medical records, etc.)? (look at links to company site/login portal)'),
('Does the email convey a sense of urgency in its message?'),
('Does this organization/company normally send messages like this by email?');

INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`) VALUES 
('Is that network password protected'),
('Is the network being used by lots of people'),
('What device are you accessing the network from? (work/personal device)');

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`) VALUES 
('If the site is masquerading as a common service (Amazon, Facebook, etc.), do you notice anything different about the site’s appearance?'),
('Are there advertisements that redirect to unfamiliar sites?'),
('Is the site requesting certain permissions (location, camera, microphone, etc.)');
```
