DROP TABLE users CASCADE ;
CREATE TABLE users
(
  id_user BIGSERIAL NOT NULL,
  user_name VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  first_name VARCHAR,
  last_name VARCHAR,
  created_at TIMESTAMP,
  modified_at TIMESTAMP,
  CONSTRAINT users_pk PRIMARY KEY (id_user),
  CONSTRAINT users_uk1 UNIQUE (user_name),
  CONSTRAINT users_uk2 UNIQUE (email)
);
CREATE INDEX users_idx1 ON users (user_name);
CREATE INDEX users_idx2 ON users (email);
