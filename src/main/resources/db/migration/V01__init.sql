create table if not exists task_system.users
(
    id       bigserial primary key,
    name     varchar(20) not null,
    password varchar(20) not null,
    email    varchar(20) not null unique
);

create table if not exists task_system.tasks
(
    id          bigserial primary key,
    title       varchar(255),
    description text,
    status      varchar(255),
    priority    varchar(255),
    author_id   bigint references users(id) on delete set null,
    assignee_id bigint references users(id) on delete set null
);

create table if not exists task_system.comments
(
    id        bigserial primary key,
    task_id   bigint references tasks(id) on delete cascade,
    author_id bigint references users(id) on delete set null,
    content   text
);