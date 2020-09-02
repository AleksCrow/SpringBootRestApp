-- add data to bd
DELETE FROM users;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

INSERT INTO users (name, email, age, created, password) VALUES
('Bob', 'admin@gmail.com', 35, to_timestamp('16-07-2020 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), '$2a$10$dPx51b.N3ePN7z8VbWYXl.IL4JC.VoIGDGJE38WZKazGoUgp1vGW.'),
('Mike', 'user@gmail.com', 40, to_timestamp('17-08-2020 12:00:38', 'dd-mm-yyyy hh24:mi:ss'), '$2a$10$dPx51b.N3ePN7z8VbWYXl.IL4JC.VoIGDGJE38WZKazGoUgp1vGW.'),
('Vasiliy', 'user@yandex.ru', 25, now(), '$2a$10$dPx51b.N3ePN7z8VbWYXl.IL4JC.VoIGDGJE38WZKazGoUgp1vGW.');