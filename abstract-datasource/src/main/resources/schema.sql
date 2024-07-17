CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.users1 (
    id      integer PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS app.users2 (
    id      integer PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);