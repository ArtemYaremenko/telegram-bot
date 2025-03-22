-- liquibase formatted sql

-- changeset admin:1

CREATE TABLE notification_task (id SERIAL PRIMARY KEY, notification_text TEXT, notification_date DATE);