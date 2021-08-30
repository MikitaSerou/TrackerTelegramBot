drop table users;

create table users
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id BIGINT,
    user_name          VARCHAR(255),
    PRIMARY KEY(id)
);