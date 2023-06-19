CREATE TABLE certificates (
    id SERIAL PRIMARY KEY,
    password TEXT,
    path TEXT NOT NULL,
    user_id BIGINT UNIQUE
);

ALTER TABLE certificates
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES users (id);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);
CREATE TABLE users_roles (
    user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);



