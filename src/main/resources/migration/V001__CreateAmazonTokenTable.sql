CREATE TABLE amazon_tokens (
    token VARCHAR(200) NOT NULL,
    user_id VARCHAR(40) NOT NULL,
    user_type VARCHAR(40) NOT NULL,
    PRIMARY KEY(token)
) ENGINE=InnoDB charset=UTF8;
