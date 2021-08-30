drop table users;

create table users
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id   BIGINT unique,
    user_name VARCHAR(255),
    is_active BOOLEAN,
    PRIMARY KEY (id)
);