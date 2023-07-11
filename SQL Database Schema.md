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

INSERT INTO `AttackTypes` (`AttackTypeName`) VALUES 
    ('Email'), 
    ('BrowserSecurity');

-- Start a transaction
START TRANSACTION;

-- Insert the first email question
INSERT INTO `EmailQuestions` (`QuestionText`, `Weight`) 
VALUES ('Are there any links in the email body?', 5);

-- Get the ID of the question we just inserted
SET @last_email_question_id = LAST_INSERT_ID();

-- Insert the recommendation corresponding to the first email question
INSERT INTO `EmailRecommendations` (`RecommendationText`) 
VALUES ('Be cautious of links in unsolicited emails, especially if they are asking for sensitive information. Consider contacting the sender directly through another method.');

-- Get the ID of the recommendation we just inserted
SET @last_email_recommendation_id = LAST_INSERT_ID();

-- Insert into the join table
INSERT INTO `EGQuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question_id, @last_email_recommendation_id);

-- Repeat the process for the second email question and recommendation
INSERT INTO `EmailQuestions` (`QuestionText`, `Weight`) 
VALUES ('Is this spam - are you expecting an email from this source?', 6);

SET @last_email_question_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendations` (`RecommendationText`) 
VALUES ('Always question the legitimacy of emails from unknown sources. When in doubt, verify through other means.');

SET @last_email_recommendation_id = LAST_INSERT_ID();

INSERT INTO `EGQuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question_id, @last_email_recommendation_id);

INSERT INTO `EmailQuestions` (`QuestionText`, `Weight`) 
VALUES ('Are there any documents attached?', 6);

INSERT INTO `EmailRecommendations` (`RecommendationText`) 
VALUES ('Make sure attachments are from a trusted source from which you have recieved documents from before');

SET @last_email_recommendation_id = LAST_INSERT_ID();

INSERT INTO `EGQuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question_id, @last_email_recommendation_id);

-- Now, do the same for the browser security questions and recommendations
INSERT INTO `BrowserSecurityQuestions` (`QuestionText`, `Weight`) 
VALUES ('Are you being redirected to another page?', 8);

SET @last_browser_question_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendations` (`RecommendationText`) 
VALUES ('Unexpected redirects could be a sign of an attack. Try to avoid entering sensitive information after being redirected.');

SET @last_browser_recommendation_id = LAST_INSERT_ID();

INSERT INTO `BSGQuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question_id, @last_browser_recommendation_id);

INSERT INTO `BrowserSecurityQuestions` (`QuestionText`, `Weight`) 
VALUES ('Is the user currently on an open wifi network?', 8);

SET @last_browser_question_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendations` (`RecommendationText`) 
VALUES ('When using open wifi networks, avoid accessing sensitive information due to the risk of man-in-the-middle attacks.');

SET @last_browser_recommendation_id = LAST_INSERT_ID();

INSERT INTO `BSGQuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question_id, @last_browser_recommendation_id);

-- Commit the transaction
-- COMMIT;

-- Insert questions and recommendations for EmailQuestionsTargeted1

INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Does the sender email belong to a public webmail server (gmail, yahoo, hotmail)?', 8);
SET @last_email_question1_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Be extra cautious when receiving emails from public webmail servers, as they are more likely to be from unknown sources.');
SET @last_email_recommendation1_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question1_id, @last_email_recommendation1_id);


INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Does the URL use https?', 5);
SET @last_email_question2_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Always ensure the URL uses https for secure communication.');
SET @last_email_recommendation2_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question2_id, @last_email_recommendation2_id);


INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Top level domain - is it a familiar extension (.com, .edu, .net, .mil), foreign/uncommon TLDs could be red flag', 7);
SET @last_email_question3_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Verify the domain extension and company name in the URL to avoid phishing attempts.');
SET @last_email_recommendation3_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question3_id, @last_email_recommendation3_id);


INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Second level domain - is it of a known company/org., is it misspelled?', 9);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Verify the domain name and ensure it is spelled correctly to avoid phishing attempts.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question4_id, @last_email_recommendation4_id);


INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('If Path - What type of file does it appear to open - pdf or image more benign, scripts could be malicious (.php, .js, etc.)', 10);
SET @last_email_question5_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Be cautious about the type of files being opened from the URL. Scripts could be malicious.');
SET @last_email_recommendation5_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question5_id, @last_email_recommendation5_id);

INSERT INTO `EmailQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Query strings - do any of these parameters have a username, password, or other piece of account information in them (sign query params will be used to transfer data to redirect link)', 10);
SET @last_email_question6_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Check for sensitive account information in the query parameters of the URL. These could be used to transfer data to a redirect link.');
SET @last_email_recommendation6_id = LAST_INSERT_ID();

INSERT INTO `ET1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question6_id, @last_email_recommendation6_id);

INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the email convey a sense of urgency?', 8);
SET @last_email_question1_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Beware of urgent or threatening language in the email. Invoking a sense of urgency or fear is a common phishing tactic.');
SET @last_email_recommendation1_id = LAST_INSERT_ID();

INSERT INTO `ET2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question1_id, @last_email_recommendation1_id);


INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Is the email asking to give up sensitive information (financial, SSN, medical records, etc.)? (look at links to company site/login portal)', 10);
SET @last_email_question2_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Never share sensitive information through email. Legitimate organizations will never ask for this information through email.');
SET @last_email_recommendation2_id = LAST_INSERT_ID();

INSERT INTO `ET2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question2_id, @last_email_recommendation2_id);


INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the email convey a sense of urgency in its message?', 8);
SET @last_email_question3_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Be cautious of emails conveying a sense of urgency. It is a common tactic used in phishing attacks.');
SET @last_email_recommendation3_id = LAST_INSERT_ID();

INSERT INTO `ET2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question3_id, @last_email_recommendation3_id);

INSERT INTO `EmailQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does this organization/company normally send messages like this by email?', 7);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Consider whether you’re expecting an email from the organization or company, and if they usually communicate with you in this manner.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `ET2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_email_question4_id, @last_email_recommendation4_id);

INSERT INTO `EmailQuestionsTargeted3` (`QuestionText`, `Weight`) 
VALUES ('What is the file type/extension', 6);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted3` (`RecommendationText`) 
VALUES ('Even benign file extensions can be hiding malware.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `EmailQuestionsTargeted3` (`QuestionText`, `Weight`) 
VALUES ('How big is the file?', 7);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted3` (`RecommendationText`) 
VALUES ('Larger files can be sign that there is an additional program inside, most likely malware.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `EmailQuestionsTargeted3` (`QuestionText`, `Weight`) 
VALUES ('Does the file require a program running on the device?', 5);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted3` (`RecommendationText`) 
VALUES ('If necessary, opening files like word documents or pdfs should be done within the browser. Otherwise, it is best to not open these files.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `EmailQuestionsTargeted3` (`QuestionText`, `Weight`) 
VALUES ('Does the file name hint at its contents', 4);
SET @last_email_question4_id = LAST_INSERT_ID();

INSERT INTO `EmailRecommendationsTargeted3` (`RecommendationText`) 
VALUES ('Ambigious/vague filenames can cause users to take risks, prompting them to open them when they may be malicious.');
SET @last_email_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Is the network password protected?', 7);
SET @last_browser_question1_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Public WIFI networks that are password protected still provide some level of encryption.');
SET @last_browser_recommendation1_id = LAST_INSERT_ID();

INSERT INTO `BST1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question1_id, @last_browser_recommendation1_id);


INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Is the network being used by lots of people?', 5);
SET @last_browser_question2_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Public networks, especially ones with a large number of users, are not monitored as heavily.');
SET @last_browser_recommendation2_id = LAST_INSERT_ID();

INSERT INTO `BST1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question2_id, @last_browser_recommendation2_id);


INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('What device are you accessing the network from (work/personal)?', 8);
SET @last_browser_question3_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Depending on what the device is used for, this could expose information about your personal info or private business information.');
SET @last_browser_recommendation3_id = LAST_INSERT_ID();

INSERT INTO `BST1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question3_id, @last_browser_recommendation3_id);

INSERT INTO `BrowserSecurityQuestionsTargeted1` (`QuestionText`, `Weight`) 
VALUES ('Are you trying to access sensitive information (healthcare, banking, etc.)?', 8);
SET @last_browser_question4_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted1` (`RecommendationText`) 
VALUES ('Credentials can be stolen with Man in the Middle (MITM) attacks. Multi-factor authentication can help with this, but it is not guaranteed that every service will have it.');
SET @last_browser_recommendation4_id = LAST_INSERT_ID();

INSERT INTO `BST1QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question4_id, @last_browser_recommendation4_id);


INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the site have a visible and clear privacy policy?', 6);
SET @last_browser_question5_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Always read the site’s privacy policy and terms of use.');
SET @last_browser_recommendation5_id = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question5_id, @last_browser_recommendation5_id);


INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the site have third party tracking scripts?', 9);
SET @last_browser_question6_id = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Be cautious about third-party tracking scripts, as they can be used for malicious activities.');
SET @last_browser_recommendation6_id = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question6_id, @last_browser_recommendation6_id);

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the site have an SSL certificate?', 8);
SET @last_browser_question1_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Check for an SSL certificate to ensure the website is secure before entering any sensitive information.');
SET @last_browser_recommendation1_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question1_id2, @last_browser_recommendation1_id2);


INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('If the site is masquerading as a common service (Amazon, Facebook, etc.), do you notice anything different about the site’s appearance?', 10);
SET @last_browser_question2_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Look for unusual or suspicious elements in the website design. This can be a sign of a phishing website.');
SET @last_browser_recommendation2_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question2_id2, @last_browser_recommendation2_id2);

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Are there advertisements that redirect to unfamiliar sites?', 8);
SET @last_browser_question3_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Avoid clicking on advertisements that redirect to unfamiliar websites.');
SET @last_browser_recommendation3_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question3_id2, @last_browser_recommendation3_id2);


INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Is the site requesting certain permissions (location, camera, microphone, etc.)?', 8);
SET @last_browser_question4_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Always consider what permissions a website is asking for. Avoid granting permissions if they are not necessary for the site functionality.');
SET @last_browser_recommendation4_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question4_id2, @last_browser_recommendation4_id2);

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Is the site using https?', 7);
SET @last_browser_question4_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Beware of sites that do not use https, since it is fairly ubiquitious among modern websites.');
SET @last_browser_recommendation4_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question4_id2, @last_browser_recommendation4_id2);

INSERT INTO `BrowserSecurityQuestionsTargeted2` (`QuestionText`, `Weight`) 
VALUES ('Does the site use secure cookies?', 6);
SET @last_browser_question4_id2 = LAST_INSERT_ID();

INSERT INTO `BrowserSecurityRecommendationsTargeted2` (`RecommendationText`) 
VALUES ('Check if the site has secure cookies.');
SET @last_browser_recommendation4_id2 = LAST_INSERT_ID();

INSERT INTO `BST2QuestionRecommendation` (`QuestionID`, `RecommendationID`) 
VALUES (@last_browser_question4_id2, @last_browser_recommendation4_id2);
```
