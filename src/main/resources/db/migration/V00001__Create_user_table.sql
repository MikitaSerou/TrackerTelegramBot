create table users
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    user_name          VARCHAR(255),
    PRIMARY KEY(id)
);