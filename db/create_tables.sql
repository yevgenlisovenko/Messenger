CREATE TABLE IF NOT EXISTS messages (
    id bigserial PRIMARY KEY,
    date_time timestamp NOT NULL,
    sender_id bigint NOT NULL,
    recipient_id bigint NOT NULL,
    message varchar(1024) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_messages_datetime ON messages (date_time);
CREATE INDEX IF NOT EXISTS idx_messages_recipient ON messages (recipient_id);
CREATE INDEX IF NOT EXISTS idx_messages_recipient_sender ON messages (recipient_id, sender_id);