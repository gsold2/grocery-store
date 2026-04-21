CREATE SCHEMA IF NOT EXISTS catalog;

CREATE TABLE IF NOT EXISTS catalog.product
(
    id serial primary key,
    title varchar(50) not null check (length(trim(title)) >= 3),
    description varchar(1000)
);

INSERT INTO catalog.product (id, title, description)
VALUES
    (1, 'title 1', 'description 1'),
    (2, 'title 2', 'description 2'),
    (3, 'title 3', 'description 4');