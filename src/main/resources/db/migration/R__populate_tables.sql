TRUNCATE TABLE app.users CASCADE;

INSERT INTO app.users (id, first_name, last_name)
VALUES (1, 'John', 'Doe'),
       (2, 'Mary', 'Smith');

INSERT INTO app.emails (user_id, email)
VALUES (1, 'john1@gmail.com'),
       (1, 'john2@gmail.com'),
       (1, 'john3@gmail.com'),
       (2, 'mary1@gmail.com'),
       (2, 'mary2@gmail.com');
