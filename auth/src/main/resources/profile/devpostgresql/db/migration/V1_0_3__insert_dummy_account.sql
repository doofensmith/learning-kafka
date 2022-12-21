INSERT
INTO
  t_account_master
  (created_at, created_by, username, password, is_deleted, is_active)
VALUES
  (NOW(), 'FLYWAY', 'admin1', '$2a$12$VZr/HXiouQvoR3Pjx50bpO2BK9Dt3Uc9JTg33/6fm1VM4QCLOtRMu', FALSE, TRUE),
  (NOW(), 'FLYWAY', 'admin2', '$2a$12$VZr/HXiouQvoR3Pjx50bpO2BK9Dt3Uc9JTg33/6fm1VM4QCLOtRMu', FALSE, TRUE),
  (NOW(), 'FLYWAY', 'dimas', '$2a$12$VZr/HXiouQvoR3Pjx50bpO2BK9Dt3Uc9JTg33/6fm1VM4QCLOtRMu', FALSE, TRUE);

INSERT
INTO
  bt_account_roles
  (id_account, id_role)
VALUES
  (1, 2),
  (1, 3),
  (2, 3),
  (3, 2);