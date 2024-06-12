--@block
CREATE TABLE user_level_of_access (
    user_role_id CHAR(1) PRIMARY KEY NOT NULL,
    user_role_name VARCHAR(50) NOT NULL
);
--@block
INSERT INTO user_level_of_access (user_role_id, user_role_name)
VALUES ('A', 'Admin'),
    ('C', 'Cashier');
--@block
CREATE TABLE user_account_status (
    user_account_status_id CHAR(3) PRIMARY KEY NOT NULL,
    account_status VARCHAR(50) NOT NULL
);
--@block
INSERT INTO user_account_status (user_account_status_id, account_status)
VALUES ('ACT', 'Active'),
    ('INA', 'Inactive');
--@block
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_role_id CHAR(1),
    user_first_name VARCHAR(50) NOT NULL,
    user_last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255),
    user_account_status_id CHAR(3) DEFAULT 'ACT',
    FOREIGN KEY (user_role_id) REFERENCES user_level_of_access(user_role_id),
    FOREIGN KEY (user_account_status_id) REFERENCES user_account_status(user_account_status_id)
);
--@block
CREATE TABLE user_logs (
    user_log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    user_action VARCHAR(100),
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
--@block
CREATE TABLE report_type (
    report_type_id INT AUTO_INCREMENT PRIMARY KEY,
    report_type VARCHAR(100) NOT NULL
);
--@block
INSERT INTO report_type(report_type)
VALUES ('Sales Report'),
    ('Inventory Report'),
    ('Return Report');
--@block
CREATE TABLE reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_type_id INT,
    report_date DATE,
    user_id INT,
    FOREIGN KEY (report_type_id) REFERENCES report_type(report_type_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
--@block
CREATE TABLE security_question (
    security_question_id INT AUTO_INCREMENT PRIMARY KEY,
    security_question VARCHAR(255) UNIQUE
);
--@block
INSERT INTO security_question (security_question)
VALUES ('What is your mother''s maiden name?'),
    ('What is the name of your first pet?'),
    ('In what city were you born?'),
    ('What is your favorite movie?'),
    (
        'In what year did you graduate from high school?'
    ),
    ('What was the name of your first school?');
--@block
CREATE TABLE security_answer (
    security_answer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    security_question_id INT,
    security_answer VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (security_question_id) REFERENCES security_question(security_question_id)
);
SELECT *
FROM users;
SELECT *
FROM user_logs;