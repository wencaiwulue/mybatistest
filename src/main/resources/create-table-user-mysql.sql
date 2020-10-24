DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id     int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name   varchar(100) DEFAULT NULL,
    gender TINYINT      DEFAULT 0,
    type   varchar(10)  DEFAULT NULL
);

INSERT INTO user
VALUES (1, 'a', '1', NULL);
INSERT INTO user
VALUES (2, 'b', '0', NULL);
INSERT INTO user
VALUES (3, 'c', '1', '好人');