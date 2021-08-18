CREATE TABLE "chat".message_list
(
    id SERIAL PRIMARY KEY,
    message text not null,
    user_id INTEGER REFERENCES "chat".app_user(id) not null,
    message_date timestamp DEFAULT now(),
    room_id INTEGER REFERENCES "chat".room(id) not null
);
INSERT INTO "chat".message_list (message, user_id, room_id) values ('Список команд бота:
                <br/>yBot help
                <br/>yBot find {имя канала}{Название ролика}
                <br/>yBot channelInfo {имя канала}
                <br/>yBot videoCommentRandom {имя канала}{Название ролика}', 2, 1);
INSERT INTO "chat".message_list (message, user_id, room_id) values ('Список команд чата:
                <br/>Комнаты:
                <br/>//room create {Название комнаты} - создает комнаты
                <br/>-c закрытая комната
                <br/>//room remove {Название комнаты} - удаляет комнату (владелец и админ)
                <br/>//room rename {Название комнаты} - переименование комнаты (владелец и админ)
                <br/>//room connect {Название комнаты} - войти в комнату
                <br/>-l {login пользователя} - добавить пользователя в комнату
                <br/>//room disconnect - выйти из текущей комнаты
                <br/>//room disconnect {Название комнаты} - выйти из заданной комнаты
                <br/>-l {login пользователя} - выгоняет пользователя из комнаты (для владельца и админа)
                <br/>-m {Количество минут} - время на которое пользователь не сможет войти (для владельца и админа)
                <br/>Пользователи:
                <br/> //user rename {login пользователя} (изменить свое имя)
                <br/>//user ban;
                <br/>-l {login пользователя} - выгоняет пользователя из всех комнат (для админа)
                <br/>-m {Количество минут} - время на которое пользователь не сможет войти
                <br/>//user moderator {login пользователя} - действия над модераторами (для админа)
                <br/>-n - назначить пользователя модератором
                <br/>-d - “разжаловать” пользователя', 1, 2);