CREATE DATABASE  IF NOT EXISTS `stream_sphere`;
USE `stream_sphere`;

-- Disable foreign key checks
-- SET FOREIGN_KEY_CHECKS = 0;

-- Truncate all tables
-- DROP TABLE watch_history;
-- DROP TABLE watch_list;
-- DROP TABLE movie_actors;
-- DROP TABLE movie_genre; 
-- DROP TABLE user_profile;
-- DROP TABLE movies;
-- DROP TABLE actors;
-- DROP TABLE genre;
-- DROP TABLE profiles;
-- DROP TABLE users;

-- Re-enable foreign key checks
-- SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(100),
    password_hash VARCHAR(255)
);

CREATE TABLE profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    adult BOOLEAN,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    released DATE,
    runtime FLOAT,
    description VARCHAR(255),
    rating FLOAT,
    poster VARCHAR(255),
    genre VARCHAR(255),
    actors VARCHAR(255)
);

CREATE TABLE watch_list (
    profile_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    time_stamp DATE,
    PRIMARY KEY (profile_id, movie_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE watch_history (
    profile_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    time_stamp DATE,
    PRIMARY KEY (profile_id, movie_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE home_config (
    id INT AUTO_INCREMENT PRIMARY KEY,
    value INT NOT NULL UNIQUE
);