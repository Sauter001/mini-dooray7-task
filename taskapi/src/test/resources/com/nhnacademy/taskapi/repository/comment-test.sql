-- 태스크 데이터 삽입
INSERT INTO task (task_content, project_id, mile_stone_id)
VALUES ('Task 1 content', 1, 1);

INSERT INTO task (task_content, project_id, mile_stone_id)
VALUES ('Task 2 content', 1, 2);

INSERT INTO task (task_content, project_id, mile_stone_id)
VALUES ('Task 3 content', 2, 1);

-- 코멘트 데이터 삽입
INSERT INTO comment (comment_content, task_id)
VALUES ('This is a comment for Task 1', 1);

INSERT INTO comment (comment_content, task_id)
VALUES ('Another comment for Task 1', 1);

INSERT INTO comment (comment_content, task_id)
VALUES ('This is a comment for Task 2', 2);


