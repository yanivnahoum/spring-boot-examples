CREATE TABLE IF NOT EXISTS app.users
(
    id         INT PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS app.emails
(
    id      SERIAL PRIMARY KEY,
    user_id INT          NOT NULL,
    email   VARCHAR(100) NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES app.users (id)
);