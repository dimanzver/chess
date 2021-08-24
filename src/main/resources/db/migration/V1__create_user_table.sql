CREATE TABLE users (
      id       SERIAL PRIMARY KEY ,
      name varchar(255) NOT NULL DEFAULT '',
      email varchar(255) NOT NULL DEFAULT '',
      password varchar(255) NOT NULL DEFAULT ''
);