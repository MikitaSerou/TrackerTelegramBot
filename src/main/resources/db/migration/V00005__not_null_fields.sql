drop table users;

create table users
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id   BIGINT unique NOT NULL,
    user_name VARCHAR(255),
    is_active BOOLEAN       NOT NULL,
    PRIMARY KEY (id)
);