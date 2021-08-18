CREATE TABLE "chat".add_user_schedule
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES "chat".app_user(id) not null,
    room_id INTEGER REFERENCES "chat".room(id) not null,
    time timestamp
);