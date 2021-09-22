CREATE TABLE game_players (
    id BIGSERIAL PRIMARY KEY ,
    game_id integer NOT NULL REFERENCES games (id),
    user_id integer NOT NULL REFERENCES users (id),
    side smallint NOT NULL ,
    time_remains integer
)