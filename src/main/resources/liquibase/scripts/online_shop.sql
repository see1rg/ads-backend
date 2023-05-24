-- liquibase formatted sql

-- changeSet see1rg:1
-- Таблица пользователей
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(30) NOT NULL,
                       first_name VARCHAR(30) NOT NULL,
                       last_name VARCHAR(30) NOT NULL,
                       phone VARCHAR(15) NOT NULL,
                       image VARCHAR(15) NOT NULL,
                       role VARCHAR(10) NOT NULL
);

-- Таблица объявлений
CREATE TABLE ads (
                     id BIGSERIAL PRIMARY KEY,
                     image VARCHAR(255) NOT NULL,
                     price DECIMAL(10, 2),
                     title VARCHAR(255) NOT NULL,
                     author_id BIGINT NOT NULL,
                     FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Таблица комментариев
CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          ads_id BIGINT NOT NULL,
                          author_id BIGINT NOT NULL,
                          FOREIGN KEY (ads_id) REFERENCES ads(id),
                          FOREIGN KEY (author_id) REFERENCES users(id)
);
