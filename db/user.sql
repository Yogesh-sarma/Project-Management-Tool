DROP TABLE User;
CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    fullName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    create_At DATE,
    update_At DATE,
    confirmPassword VARCHAR(255) NOT NULL
);
