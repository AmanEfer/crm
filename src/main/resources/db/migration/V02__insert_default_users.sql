insert into task_system.users (id, email, name, password)
values (1, 'aaa@gmail.com', 'aaa', '$2a$10$9hkkP3UdM/zNrA6CkH5J3ePmI1xMo9S5WJtrKmOCbM3rfqYKU7dEq'), --password: aaa
       (2, 'bbb@gmail.com', 'bbb', '$2a$10$9Isl4ZBxOScUibstMt5eCuFiRxUy6XGzpnhPAfmnhunjrqQKVjiMa'); --password: bbb


insert into task_system.roles (id, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

insert into task_system.users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

insert into task_system.tasks (title, description, status, priority, author_id, assignee_id)
values ('task1', 'some description1', 'IN_WAITING', 'MEDIUM', 1, 1),
       ('task2', 'some description2', 'IN_WAITING', 'MEDIUM', 1, 2),
       ('task3', 'some description4', 'IN_WAITING', 'MEDIUM', 2, 1),
       ('task4', 'some description5', 'IN_WAITING', 'MEDIUM', 2, 2);

select setval(pg_get_serial_sequence('task_system.users', 'id'), coalesce(max(id), 1)) from task_system.users;
select setval(pg_get_serial_sequence('task_system.roles', 'id'), coalesce(max(id), 1)) from task_system.roles;
select setval(pg_get_serial_sequence('task_system.users_roles', 'id'), coalesce(max(id), 1)) from task_system.users_roles;
select setval(pg_get_serial_sequence('task_system.tasks', 'id'), coalesce(max(id), 1)) from task_system.tasks;
select setval(pg_get_serial_sequence('task_system.comments', 'id'), coalesce(max(id), 1)) from task_system.comments;