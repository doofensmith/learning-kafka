INSERT
INTO
  t_role
  (created_at, created_by, `role`, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 'GUEST', 0),
  (NOW(), 'FLYWAY', 'USER', 0),
  (NOW(), 'FLYWAY', 'ADMIN', 0);