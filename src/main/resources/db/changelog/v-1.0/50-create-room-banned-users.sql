CREATE TABLE "chat".room_banned_users
(
    user_id INTEGER REFERENCES "chat".app_user(id) not null,
    room_id INTEGER REFERENCES "chat".room(id) not null,
    PRIMARY KEY (user_id, room_id)
);