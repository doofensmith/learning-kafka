INSERT
INTO
  t_profile
  (created_at, created_by, fullname, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 'Administrator 1', 0),
  (NOW(), 'FLYWAY', 'Administrator 2', 0),
  (NOW(), 'FLYWAY', 'Dimas', 0);

INSERT
INTO
  t_account_master
  (created_at, created_by, username, password, is_active, id_profile, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 'admin1', '$2a$12$Edso/GHPTxuASVz13Fia/upCEOkkGFb3wnNsqE0RaRechZ.Pnq/SG', 1, 1, 0),
  (NOW(), 'FLYWAY', 'admin2', '$2a$12$NuCzyLDaXlRKaHzQpMLWI.vjSMc5QABbNliGWc.O1yfoLKI0vZzia', 1, 2, 0),
  (NOW(), 'FLYWAY', 'dimas', '$2a$12$onlBnTMciqW5bC4SotMkIurUjMY5GkZT1J.BZCRPssXgfJ1pC8QiO', 1, 3, 0);

INSERT
INTO
  bt_account_roles
  (id_account, id_role)
VALUES
  (1, 2),
  (1, 3),
  (2, 3),
  (3, 2);