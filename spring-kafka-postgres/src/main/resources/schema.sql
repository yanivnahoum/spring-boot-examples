CREATE TABLE products
(
    id    SERIAL PRIMARY KEY,
    code  varchar(255)  NOT NULL,
    name  varchar(255)  NOT NULL,
    price numeric(5, 2) NOT NULL,
    UNIQUE (code)
);
