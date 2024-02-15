ALTER SESSION SET CONTAINER=XEPDB1;
ALTER SESSION SET CURRENT_SCHEMA =ORACLE;

CREATE SEQUENCE oracle.user_id_seq;

CREATE TABLE oracle.users
(
    id    NUMBER DEFAULT user_id_seq.NEXTVAL PRIMARY KEY,
    name  VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) NOT NULL
);

INSERT INTO users (name, email)
VALUES ('Alice', 'alice@example.com');
INSERT INTO users (name, email)
VALUES ('Bob', 'bob@example.com');

COMMIT;