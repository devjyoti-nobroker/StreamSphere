-- Drop user first if they exist
DROP USER if exists 'nobroker'@'localhost' ;

-- Now create user with prop privileges
CREATE USER 'nobroker'@'localhost' IDENTIFIED BY 'Broker@21';

GRANT ALL PRIVILEGES ON * . * TO 'nobroker'@'localhost';