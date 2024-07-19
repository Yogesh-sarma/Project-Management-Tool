CREATE TABLE ProjectTask (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    projectSequence VARCHAR(255) NOT NULL,
    summary TEXT NOT NULL,
    acceptanceCriteria TEXT,
    status VARCHAR(255),
    priority INT,
    backlog_id BIGINT NOT NULL,
    projectIdentifier VARCHAR(255) NOT NULL,
    dueDate DATE,
    created_At DATE NOT NULL,
    updated_At DATE
);
