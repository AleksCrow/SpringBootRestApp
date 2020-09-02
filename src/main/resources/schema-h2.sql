-- create bd schema
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               SERIAL                  PRIMARY KEY ,
    name             VARCHAR(255)            NOT NULL,
    email            VARCHAR(255)            NOT NULL,
    age              INTEGER                 NOT NULL,
    created          TIMESTAMP               NOT NULL,
    password         VARCHAR(255)
);