INSERT
INTO
  t_role
  (created_at, created_by, role, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 'GUEST', FALSE),
  (NOW(), 'FLYWAY', 'USER', FALSE),
  (NOW(), 'FLYWAY', 'ADMIN', FALSE);