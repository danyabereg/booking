CREATE EXTENSION pgcrypto;

CREATE TABLE IF NOT EXISTS hotels
(
    id          uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    hotel_name  VARCHAR(64) UNIQUE NOT NULL,
    address     VARCHAR(64) UNIQUE NOT NULL,
    night_price NUMERIC(8, 2)      NOT NULL
);

CREATE TABLE IF NOT EXISTS status_discount
(
    status           VARCHAR(8) PRIMARY KEY,
    discount_percent INT               NOT NULL
);

CREATE TABLE IF NOT EXISTS loyalty
(
    username         VARCHAR(32) PRIMARY KEY,
    booking_quantity INT        NOT NULL,
    status           VARCHAR(8) NOT NULL REFERENCES status_discount (status)
);

CREATE TABLE IF NOT EXISTS reservations
(
    id          uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    username    VARCHAR(32)   NOT NULL REFERENCES loyalty (username),
    hotel_id    uuid          NOT NULL REFERENCES hotels (id),
    status      VARCHAR(8)    NOT NULL,
    date_from   DATE          NOT NULL,
    date_to     DATE          NOT NULL,
    user_status VARCHAR(8)    NOT NULL,
    price       NUMERIC(8, 2) NOT NULL,
    last_update DATE          NOT NULL
);

CREATE TABLE reservations_archive
(
    id          uuid PRIMARY KEY,
    username    VARCHAR(32)   NOT NULL,
    hotel_id    uuid          NOT NULL,
    status      VARCHAR(8)    NOT NULL,
    date_from   DATE          NOT NULL,
    date_to     DATE          NOT NULL,
    user_status VARCHAR(8)    NOT NULL,
    price       NUMERIC(8, 2) NOT NULL,
    last_update DATE          NOT NULL
);