CREATE TABLE moves (
    id BIGSERIAL PRIMARY KEY ,
    game_player_id bigint NOT NULL REFERENCES game_players (id),
    number smallint NOT NULL ,
    fen_start varchar (100) NOT NULL ,
    move varchar (10) NOT NULL
)