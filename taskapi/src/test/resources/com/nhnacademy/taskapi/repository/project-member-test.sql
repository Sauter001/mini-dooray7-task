insert into account
values (1),
       (2),
       (3);

insert into project(name, project_status)
values ('dooray-active1', 'ACTIVE');

insert
into project_member(account_id, project_id, project_auth)
values (1, 1, 'ADMIN'),
       (2, 1, 'MEMBER'),
       (3, 1, 'MEMBER'),

