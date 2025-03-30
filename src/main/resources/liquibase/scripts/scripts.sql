-- liquibase formatted sql

-- changeset admin:1

CREATE TABLE notification_task (id SERIAL PRIMARY KEY, notification_text TEXT, notification_date DATE);

-- changeset admin:2

ALTER TABLE notification_task ALTER COLUMN notification_date TYPE TIMESTAMP;

-- changeset admin:3

ALTER TABLE notification_task ALTER COLUMN id TYPE BIGINT;