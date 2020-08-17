DROP TABLE IF EXISTS USER_REDDIT_CHANNEL;
DROP TABLE IF EXISTS USER_PROFILE;
DROP TABLE IF EXISTS SUB_REDDIT_CHANNEL;
DROP TABLE IF EXISTS USER_REDDIT_CHANNEL_MAPPING;
DROP TABLE IF EXISTS NOTIFICATION;

CREATE TABLE USER_PROFILE
(
    user_id                 VARCHAR(50) NOT NULL,
    full_name               VARCHAR(255) NOT NULL,
    email_id                VARCHAR(255),
    country                 VARCHAR (100) NOT NULL,
    notification_status     BOOLEAN NOT NULL DEFAULT FALSE,
    notification_ts_utc     TIMESTAMPTZ  NOT NULL,
    last_email_sent_ts_utc  TIMESTAMPTZ  NOT NULL,
    created_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT user_profile_pkey PRIMARY KEY (user_id),
    CONSTRAINT user_profile_unique UNIQUE (email_id)
);

CREATE TABLE SUBSCRIBED_REDDIT_CHANNEL
(
    channel_id              VARCHAR(50) NOT NULL,
    reddit_channel          VARCHAR(255) NOT NULL,
    created_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT sub_reddit_pk PRIMARY KEY (channel_id),
    CONSTRAINT sub_reddit_unique UNIQUE (reddit_channel)
);

CREATE TABLE USER_REDDIT_CHANNEL_MAPPING
(
    user_id             VARCHAR(50) REFERENCES USER_PROFILE (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    channel_id          VARCHAR(50) REFERENCES SUBSCRIBED_REDDIT_CHANNEL (channel_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_reddit_pkey PRIMARY KEY (user_id, channel_id)  -- explicit pk
);
