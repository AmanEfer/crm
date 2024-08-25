create schema if not exists task_system;

create table if not exists task_system.users
(
    id       serial primary key,
    name     varchar(30)  not null,
    password varchar(128) not null,
    email    varchar(30)  not null unique
);
create index if not exists idx_users_email on task_system.users (email);

create table if not exists task_system.roles
(
    id   serial primary key,
    name varchar(20) not null unique default 'ROLE_USER'
);

create table if not exists task_system.users_roles
(
    id      serial primary key,
    user_id int not null references task_system.users (id) on delete cascade,
    role_id int not null references task_system.roles (id),
    constraint uk_users_roles unique (user_id, role_id)
);

create table if not exists task_system.tasks
(
    id          serial primary key,
    title       varchar(255) not null,
    description text not null,
    status      varchar(20),
    priority    varchar(20),
    author_id   int references task_system.users (id) on delete set null,
    assignee_id int references task_system.users (id) on delete set null
);

create table if not exists task_system.comments
(
    id        serial primary key,
    task_id   int  not null references task_system.tasks (id) on delete cascade,
    author_id int  not null references task_system.users (id) on delete cascade,
    content   text not null
);