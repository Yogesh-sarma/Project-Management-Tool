DROP TABLE backlog;
CREATE TABLE Backlog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    PTSequence INT DEFAULT 0,
    projectIdentifier VARCHAR(255),
    project_id BIGINT NOT NULL
);
