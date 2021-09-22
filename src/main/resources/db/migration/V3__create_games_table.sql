CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    date_time_start timestamptz NOT NULL,
    date_time_end timestamptz NULL ,
    status smallint NOT NULL DEFAULT 0,
    result smallint NULL
)