CREATE SCHEMA wassup;

CREATE TABLE account (
                         id INT PRIMARY KEY,
                         user_name VARCHAR(255),
                         password VARCHAR(255),
                         INDEX idx_user_name (user_name)
);
