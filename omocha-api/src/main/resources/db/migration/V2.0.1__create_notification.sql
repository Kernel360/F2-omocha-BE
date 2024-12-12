CREATE TABLE notification (
    notification_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT,
    event_name VARCHAR(255) NOT NULL,
    notification_code VARCHAR(255) NOT NULL,
    data TEXT NOT NULL,
    read BOOLEAN NOT NULL,
    PRIMARY KEY (notification_id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member (member_id)
);