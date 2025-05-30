CREATE DATABASE  IF NOT EXISTS `stream_sphere`;
USE `stream_sphere`;

-- Disable foreign key checks
-- SET FOREIGN_KEY_CHECKS = 0;

-- Truncate all tables
DROP TABLE watch_history;
DROP TABLE watch_list;
DROP TABLE movie_genre; 
DROP TABLE movies;
DROP TABLE profiles;
DROP TABLE accounts;
DROP TABLE home_config;

-- Re-enable foreign key checks
-- SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE accounts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(100),
    password_hash VARCHAR(255),
    active TINYINT(1),
    created DATETIME,
	last_updated DATETIME
);

CREATE TABLE profiles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    adult BOOLEAN,
    account_id BIGINT NOT NULL,
    created DATETIME,
	last_updated DATETIME,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    released DATE,
    runtime FLOAT,
    description VARCHAR(255),
    rating FLOAT,
    poster VARCHAR(255),
    actors VARCHAR(255)
);

CREATE TABLE movie_genre (
    movie_id BIGINT NOT NULL,
    genre VARCHAR(255) NOT NULL,
    PRIMARY KEY (movie_id, genre),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE watch_list (
    profile_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    time_stamp DATETIME,
    PRIMARY KEY (profile_id, movie_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE watch_history (
    profile_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    time_stamp DATETIME,
    PRIMARY KEY (profile_id, movie_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

CREATE TABLE home_config (
    id INT AUTO_INCREMENT PRIMARY KEY,
    value INT NOT NULL UNIQUE
);