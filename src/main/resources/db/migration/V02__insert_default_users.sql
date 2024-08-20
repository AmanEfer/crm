insert into task_system.users (id, email, name, password)
values (1, 'aaa@gmail.com', 'aaa', '$2a$10$9hkkP3UdM/zNrA6CkH5J3ePmI1xMo9S5WJtrKmOCbM3rfqYKU7dEq'), --password: aaa
       (2, 'bbb@gmail.com', 'bbb', '$2a$10$9Isl4ZBxOScUibstMt5eCuFiRxUy6XGzpnhPAfmnhunjrqQKVjiMa'), --password: bbb
       (3, 'ccc@gmail.com', 'ccc', '$2a$10$GWbuDFEh2qfOZkAgPsWYjOUuDiTNxrNBeDdxn184z04.qt9/8ozEK'); --password: ccc

insert into task_system.roles (id, name)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

insert into task_system.users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 2)