CREATE TABLE notification
(
    notification_id   BIGSERIAL PRIMARY KEY,
    member_id         BIGINT,
    event_name        VARCHAR(255) NOT NULL,
    notification_code VARCHAR(255) NOT NULL,
    data              TEXT         NOT NULL,
    read              BOOLEAN      NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member (member_id)
);