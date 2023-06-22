-- liquibase formatted sql

-- changeSet see1rg:1
-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(30) NOT NULL,
                       password VARCHAR(500) NOT NULL,
                       enabled BOOLEAN,
                       first_name VARCHAR(30),
                       last_name VARCHAR(30),
                       account_expired BOOLEAN,
                       credentials_expired BOOLEAN,
                       phone VARCHAR(15),
                       role VARCHAR(10)
);

-- Таблица объявлений
CREATE TABLE ads (
                     id SERIAL PRIMARY KEY,
                     price DECIMAL(10, 2),
                     title VARCHAR(255) NOT NULL,
                     author_id BIGINT NOT NULL,
                     FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Таблица комментариев
CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          ads_id BIGINT NOT NULL,
                          author_id BIGINT NOT NULL,
                          FOREIGN KEY (ads_id) REFERENCES ads(id),
                          FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Таблица картинок
CREATE TABLE images (
                        id SERIAL PRIMARY KEY,
                        filePath VARCHAR(255),
                        fileSize BIGINT,
                        mediaType VARCHAR(255),
                        preview BYTEA,
                        user_id INT,
                        ads_id INT,
                        FOREIGN KEY (user_id) REFERENCES users (id),
                        FOREIGN KEY (ads_id) REFERENCES ads (id)
);

create table authorities (
                             username VARCHAR(50) not null,
                             authority VARCHAR(50) not null,
                             constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

