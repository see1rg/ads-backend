-- liquibase formatted sql

-- changeSet see1rg:1
-- Таблица пользователей
CREATE TABLE users_profile (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(30) NOT NULL,
                       first_name VARCHAR(30) NOT NULL,
                       last_name VARCHAR(30) NOT NULL,
                       phone VARCHAR(15) NOT NULL,
                       role VARCHAR(10) NOT NULL
);

-- Таблица объявлений
CREATE TABLE ads (
                     id SERIAL PRIMARY KEY,
                     price DECIMAL(10, 2),
                     title VARCHAR(255) NOT NULL,
                     author_id BIGINT NOT NULL,
                     FOREIGN KEY (author_id) REFERENCES users_profile(id)
);

-- Таблица комментариев
CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          ads_id BIGINT NOT NULL,
                          author_id BIGINT NOT NULL,
                          FOREIGN KEY (ads_id) REFERENCES ads(id),
                          FOREIGN KEY (author_id) REFERENCES users_profile(id)
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
                        FOREIGN KEY (user_id) REFERENCES users_profile(id),
                        FOREIGN KEY (ads_id) REFERENCES ads (id)
);

