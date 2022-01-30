DELETE FROM game_offers;
ALTER TABLE game_offers
    ADD COLUMN user_id integer NOT NULL REFERENCES users (id),
    ADD COLUMN joined_color smallint NOT NULL DEFAULT 0,
    ADD COLUMN game_time integer NULL;