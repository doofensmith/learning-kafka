INSERT
INTO
  t_account_master
  (created_at, created_by, username, password, is_deleted, is_active)
VALUES
  (NOW(), 'FLYWAY', 'admin1', '$2a$12$vpJPWeKEIGPXqTUaNVn0PuNkjYWrgC7Y92.Ppy5mB9x8KM4e6U1UO', FALSE, TRUE),
  (NOW(), 'FLYWAY', 'admin2', '$2a$12$vpJPWeKEIGPXqTUaNVn0PuNkjYWrgC7Y92.Ppy5mB9x8KM4e6U1UO', FALSE, TRUE),
  (NOW(), 'FLYWAY', 'dimas', '$2a$12$vpJPWeKEIGPXqTUaNVn0PuNkjYWrgC7Y92.Ppy5mB9x8KM4e6U1UO', FALSE, TRUE);

INSERT
INTO
  bt_account_roles
  (created_at, created_by, is_deleted, id_account, id_role)
VALUES
  (NOW(), 'FLYWAY', FALSE, 1, 2),
  (NOW(), 'FLYWAY', FALSE, 1, 3),
  (NOW(), 'FLYWAY', FALSE, 2, 3),
  (NOW(), 'FLYWAY', FALSE, 3, 2);