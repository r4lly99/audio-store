CREATE TABLE app_user (
    id BIGINT PRIMARY KEY
);

CREATE TABLE phrase (
    id BIGINT PRIMARY KEY
);

CREATE TABLE audio_file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    phrase_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (phrase_id) REFERENCES phrase(id)
);
