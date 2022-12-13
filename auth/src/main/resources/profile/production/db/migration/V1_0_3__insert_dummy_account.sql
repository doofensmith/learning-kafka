INSERT
INTO
  t_account_master
  (created_at, created_by, username, password, is_deleted, is_active)
VALUES
  (NOW(), 'FLYWAY', 'admin1', '$2a$12$Edso/GHPTxuASVz13Fia/upCEOkkGFb3wnNsqE0RaRechZ.Pnq/SG', 0, 1),
  (NOW(), 'FLYWAY', 'admin2', '$2a$12$Edso/GHPTxuASVz13Fia/upCEOkkGFb3wnNsqE0RaRechZ.Pnq/SG', 0, 1),
  (NOW(), 'FLYWAY', 'dimas', '$2a$12$Edso/GHPTxuASVz13Fia/upCEOkkGFb3wnNsqE0RaRechZ.Pnq/SG', 0, 1);

INSERT
INTO
  bt_account_roles
  (id_account, id_role)
VALUES
  (1, 2),
  (1, 3),
  (2, 3),
  (3, 2);