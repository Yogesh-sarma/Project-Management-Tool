ALTER TABLE Project
ADD CONSTRAINT fk_backlog FOREIGN KEY (backlog_id) REFERENCES Backlog(id);

ALTER TABLE Project
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES User(id);

ALTER TABLE Backlog
ADD CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES Project(id);

ALTER TABLE ProjectTask
ADD CONSTRAINT fk_taskbacklog FOREIGN KEY (backlog_id) REFERENCES Backlog(id);
