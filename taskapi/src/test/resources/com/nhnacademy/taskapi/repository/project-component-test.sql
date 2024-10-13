insert into account values (1);

insert into project(account_id, project_name, project_status)
values (1,'dooray-active1', 'ACTIVE');

insert into milestone(project_id, progress)
values (1, 'UI 설계'),
       (1, 'DB 설계'),
       (1, '테스트');

insert into tag (project_id, tag_name)
values (1, 'DB'), (1, 'UI');
       