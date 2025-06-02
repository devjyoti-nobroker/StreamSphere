CREATE DATABASE  IF NOT EXISTS `stream_sphere`;
USE `stream_sphere`;

-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Truncate all tables
DROP TABLE IF EXISTS watch_history;
DROP TABLE IF EXISTS watch_list;
DROP TABLE IF EXISTS movie_genre; 
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS home_config;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

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
    image VARCHAR(255),
    image_type VARCHAR(255),
    image_data MEDIUMBLOB,
    actors VARCHAR(255),
    created_at DATE,
    updated_at DATE,
	  updated_by BIGINT,
    FOREIGN KEY (updated_by) REFERENCES accounts(id)
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

INSERT INTO accounts (email, name, password_hash, active, created, last_updated)
VALUES
('john.doe@example.com', 'John Doe', 'hashed_pw_1', 1, NOW(), NOW()),
('jane.smith@example.com', 'Jane Smith', 'hashed_pw_2', 1, NOW(), NOW()),
('alice.wong@example.com', 'Alice Wong', 'hashed_pw_3', 0, NOW(), NOW());

INSERT INTO movies (
    title, released, runtime, description, rating,
    image, image_type, image_data,
    actors, created_at, updated_at, updated_by
)
VALUES
('Inception', '2010-07-16', 148, 'A thief who steals corporate secrets through dream-sharing technology is given an inverse task.', 8.8,
 'inception.jpg', 'image/jpeg', NULL,
 'Leonardo DiCaprio, Joseph Gordon-Levitt', CURDATE(), CURDATE(), 1),

('The Matrix', '1999-03-31', 136, 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.', 8.7,
 'matrix.jpg', 'image/jpeg', NULL,
 'Keanu Reeves, Laurence Fishburne', CURDATE(), CURDATE(), 2),

('Interstellar', '2014-11-07', 169, 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity’s survival.', 8.6,
 'interstellar.jpg', 'image/jpeg', NULL,
 'Matthew McConaughey, Anne Hathaway', CURDATE(), CURDATE(), 1),

('Parasite', '2019-05-30', 132, 'A poor family schemes to become employed by a wealthy family and infiltrate their household.', 8.6,
 'parasite.jpg', 'image/jpeg', NULL,
 'Kang-ho Song, Sun-kyun Lee', CURDATE(), CURDATE(), 3),

('The Dark Knight', '2008-07-18', 152, 'Batman faces the Joker, a criminal mastermind who plunges Gotham into anarchy.', 9.0,
 'dark_knight.jpg', 'image/jpeg', NULL,
 'Christian Bale, Heath Ledger', CURDATE(), CURDATE(), 2);


-- Inception
INSERT INTO movie_genre (movie_id, genre) VALUES
(1, 'Sci-Fi'),
(1, 'Action'),
(1, 'Thriller');

-- The Matrix
INSERT INTO movie_genre (movie_id, genre) VALUES
(2, 'Sci-Fi'),
(2, 'Action');

-- Interstellar
INSERT INTO movie_genre (movie_id, genre) VALUES
(3, 'Sci-Fi'),
(3, 'Drama'),
(3, 'Adventure');

-- Parasite
INSERT INTO movie_genre (movie_id, genre) VALUES
(4, 'Drama'),
(4, 'Thriller');

-- The Dark Knight
INSERT INTO movie_genre (movie_id, genre) VALUES
(5, 'Action'),
(5, 'Crime'),
(5, 'Drama'),
(5, 'Thriller');



SELECT * FROM movies;

SELECT * FROM accounts;

SELECT * FROM movie_genre;