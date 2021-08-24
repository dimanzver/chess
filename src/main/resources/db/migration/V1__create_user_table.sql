CREATE TABLE users (
      id       SERIAL PRIMARY KEY ,
      user_name varchar(255) DEFAULT NULL,
      password varchar(255) DEFAULT NULL
);