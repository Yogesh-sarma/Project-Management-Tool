CREATE TABLE Project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectName VARCHAR(255) NOT NULL,
    projectIdentifier VARCHAR(5) NOT NULL,
    description TEXT NOT NULL,
    start_date DATE,
    end_date DATE,
    created_At DATE NOT NULL,
    updated_At DATE,
    backlog_id BIGINT,
    user_id BIGINT
);
