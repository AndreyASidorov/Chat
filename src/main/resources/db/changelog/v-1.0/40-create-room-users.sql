CREATE TABLE "chat".room_users
(
    user_id INTEGER REFERENCES "chat".app_user(id) not null,
    room_id INTEGER REFERENCES "chat".room(id) not null,
    PRIMARY KEY (user_id, room_id)
);
INSERT INTO "chat".room_users (user_id, room_id) values (1,1),
                                                        (3,1),
                                                        (4,1);
INSERT INTO "chat".room_users (user_id, room_id) values (3,2),
                                                        (4,2);
