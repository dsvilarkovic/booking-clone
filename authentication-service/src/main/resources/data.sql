INSERT INTO users
("id", "address", "email", "first_name", "last_name", "password", "pib", "user_type")
VALUES(2, NULL, 'admin', NULL, NULL, '$2a$10$Zw1hlAxaMbZKsKMOxT7d0ujQx1qQSTmQMWOsNcjlZluQFbsngTdMK', NULL, NULL);

INSERT INTO role
("id", "role")
VALUES(1, "ADMIN");

INSERT INTO users_roles
("users_id", "roles_id")
VALUES(2, 1);
