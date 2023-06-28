```sql
DROP DATABASE `cybersafe`;

CREATE DATABASE `cybersafe`;

use `cybersafe`;

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
    `GeneralQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`GeneralQuestionID`) REFERENCES `EmailQuestions`(`QuestionID`)
);

CREATE TABLE `BrowserSecurityRecommendations` (
    `RecommendationID` INT AUTO_INCREMENT,
    `GeneralQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`GeneralQuestionID`) REFERENCES `BrowserSecurityQuestions`(`QuestionID`)
);

CREATE TABLE `EmailRecommendationsTargeted1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `TargetedQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`TargetedQuestionID`) REFERENCES `EmailQuestionsTargeted1`(`QuestionID`)
);

CREATE TABLE `BrowserSecurityRecommendationsTargeted1` (
    `RecommendationID` INT AUTO_INCREMENT,
    `TargetedQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`TargetedQuestionID`) REFERENCES `BrowserSecurityQuestionsTargeted1`(`QuestionID`)
);

CREATE TABLE `EmailRecommendationsTargeted2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `TargetedQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`TargetedQuestionID`) REFERENCES `EmailQuestionsTargeted2`(`QuestionID`)
);

CREATE TABLE `BrowserSecurityRecommendationsTargeted2` (
    `RecommendationID` INT AUTO_INCREMENT,
    `TargetedQuestionID` INT,
    `RecommendationText` TEXT NOT NULL,
    PRIMARY KEY(`RecommendationID`),
    FOREIGN KEY(`TargetedQuestionID`) REFERENCES `BrowserSecurityQuestionsTargeted2`(`QuestionID`)
);

INSERT INTO `AttackTypes` (`AttackTypeName`) VALUES 
    ('Email'), 
    ('BrowserSecurity');

INSERT INTO `EmailQuestions` (`QuestionText`, `Weight`) VALUES 
    ('Are there any links in the email body?', 5),
    ('Is this spam - are you expecting an email from this source?', 6);

INSERT INTO `BrowserSecurityQuestions` (`QuestionText`, `Weight`) VALUES 
    ('Are you being redirected to another page?', 8),
    ('Is the user currently on an open wifi network?', 8);q

INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) VALUES 
    ('Does the sender email belong to a public webmail server (gmail, yahoo, hotmail)?', 8),
    ('Does the URL use https?', 5),
    ('Top level domain - is it a familiar extension (.com, .edu, .net, .mil), foreign/uncommon TLDs could be red flag', 7),
    ('Second level domain - is it of a known company/org., is it misspelled?', 9),
    ('If Path - What type of file does it appear to open - pdf or image more benign, scripts could be malicious (.php, .js, etc.)', 10),
    ('Query strings - do any of these parameters have a username, password, or other piece of account information in them (sign query params will be used to transfer data to redirect link)', 10);

INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`, `Weight`) VALUES 
    ('Does the email convey a sense of urgency?', 8),
    ('Is the email asking to give up sensitive information (financial, SSN, medical records, etc.)? (look at links to company site/login portal)', 10),
    ('Does the email convey a sense of urgency in its message?', 8),
    ('Does this organization/company normally send messages like this by email?', 7);

INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`, `Weight`) VALUES 
    ('Is the site asking for any permissions?', 7),
    ('Is the site using https?', 5),
    ('Does the site have a valid certificate?', 8),
    ('Does the site use secure cookies?', 8),
    ('Does the site have a visible and clear privacy policy?', 6),
    ('Does the site have third party tracking scripts?', 9);

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) VALUES 
    ('Does the site have an SSL certificate?', 8),
    ('If the site is masquerading as a common service (Amazon, Facebook, etc.), do you notice anything different about the site’s appearance?', 10),
    ('Are there advertisements that redirect to unfamiliar sites?', 8),
    ('Is the site requesting certain permissions (location, camera, microphone, etc.)?', 8);

INSERT INTO `EmailRecommendations` (`GeneralQuestionID`, `RecommendationText`) VALUES 
    (1, 'Be cautious of links in unsolicited emails, especially if they are asking for sensitive information. Consider contacting the sender directly through another method.'),
    (2, 'Always question the legitimacy of emails from unknown sources. When in doubt, verify through other means.');

INSERT INTO `BrowserSecurityRecommendations` (`GeneralQuestionID`, `RecommendationText`) VALUES 
    (1, 'Unexpected redirects could be a sign of an attack. Try to avoid entering sensitive information after being redirected.'),
    (2, 'When using open wifi networks, avoid accessing sensitive information due to the risk of man-in-the-middle attacks.');

INSERT INTO `EmailRecommendationsTargeted1` (`TargetedQuestionID`, `RecommendationText`) VALUES 
    (1, 'Be extra cautious when receiving emails from public webmail servers, as they are more likely to be from unknown sources.'),
    (2, 'Always ensure the URL uses https for secure communication.'),
    (3, 'Verify the domain extension and company name in the URL to avoid phishing attempts.'),
    (4, 'Be cautious about the type of files being opened from the URL.'),
    (5, 'Check for sensitive account information in the query parameters of the URL.');

INSERT INTO `EmailRecommendationsTargeted2` (`TargetedQuestionID`, `RecommendationText`) VALUES 
    (1, 'Never share sensitive information through email. Legitimate organizations will never ask for this information through email.'),
    (2, 'Beware of urgent or threatening language in the subject line. Invoking a sense of urgency or fear is a common phishing tactic.'),
    (3, 'Consider whether you’re expecting an email from the sender.');

INSERT INTO `BrowserSecurityRecommendationsTargeted1` (`TargetedQuestionID`, `RecommendationText`) VALUES 
    (1, 'Only provide permissions to trustworthy sites. If unsure, deny the request.'),
    (2, 'Always ensure the site uses https for secure communication.'),
    (3, 'Verify the validity of the site certificate.'),
    (4, 'Check if the site uses secure cookies.'),
    (5, 'Always read the site’s privacy policy and terms of use.'),
    (6, 'Be cautious about third-party tracking scripts, as they can be used for malicious activities.');

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`TargetedQuestionID`, `RecommendationText`) VALUES 
    (1, 'Check for an SSL certificate to ensure the website is secure before entering any sensitive information.'),
    (2, 'Look for unusual or suspicious elements in the website design. This can be a sign of a phishing website.'),
    (3, 'Avoid clicking on advertisements that redirect to unfamiliar websites.'),
    (4, 'Always consider what permissions a website is asking for. Avoid granting permissions if they are not necessary for the site functionality.');
```
