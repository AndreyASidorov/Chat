CREATE TABLE "chat".room
(
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(100) not null,
    user_owner INTEGER REFERENCES "chat".app_user(id) not null,
    is_public BOOLEAN
);
INSERT INTO "chat".room (name, user_owner, is_public) values ('YoutubeBot',2,true);
INSERT INTO "chat".room (name, user_owner, is_public) values ('Public',1,true);