CREATE TABLE messages (
    id VARCHAR(40) NOT NULL,
    number INTEGER AUTO_INCREMENT NOT NULL,
    content TEXT NOT NULL,
    is_palindrome BOOLEAN NOT NULL,
    created_time BIGINT NOT NULL,
    UNIQUE(number),
    PRIMARY KEY(number)
) ENGINE=InnoDB charset=UTF8;

CREATE UNIQUE INDEX compound_num_ispal_indx
ON messages (number, is_palindrome);


