CREATE SCHEMA IF NOT EXISTS manager;

CREATE TABLE IF NOT EXISTS manager.users
(
    id serial primary key,
    name varchar(16) not null unique,
    password varchar(16) not null
);

CREATE TABLE IF NOT EXISTS manager.role
(
    id serial primary key,
    name varchar(16) not null unique
);

CREATE TABLE IF NOT EXISTS manager.user_role
(
    id serial primary key,
    user_id int not null references manager.users(id),
    role_id int not null references manager.role(id)
);

INSERT INTO manager.users (name, password)
VALUES
    ('user', 'password');

INSERT INTO manager.role (name)
VALUES
    ('USER');

INSERT INTO manager.user_role (user_id, role_id)
VALUES
    (1,1);

