CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS account_settings;
DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    account_id UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    full_name  VARCHAR(100)                               NOT NULL,
    email      VARCHAR(255)                               NOT NULL,
    password   VARCHAR(255)     DEFAULT 'Mudar@123',
    birth_date DATE,
    created_at TIMESTAMP        DEFAULT NOW()
);

CREATE TABLE account_settings
(
    account_settings_id UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    initial_form_done   BOOLEAN NOT NULL DEFAULT FALSE,
    is_first_time       BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE chat
(
    chat_id    UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    account_id UUID REFERENCES account (account_id)       NOT NULL,
    title      VARCHAR(255)                               NOT NULL,
    created_at TIMESTAMP        DEFAULT NOW()
);

CREATE TABLE message
(
    message_id        UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    chat_id           UUID REFERENCES chat (chat_id)             NOT NULL,
    text              TEXT                                       NOT NULL,
    negative          FLOAT            DEFAULT 0.0,
    neutral           FLOAT            DEFAULT 0.0,
    positive          FLOAT            DEFAULT 0.0,
    compound          FLOAT            DEFAULT 0.0,
    prompt_tokens     INT              DEFAULT 0,
    completion_tokens INT              DEFAULT 0,
    total_tokens      INT              DEFAULT 0,
    created_at        TIMESTAMP        DEFAULT NOW()
);
