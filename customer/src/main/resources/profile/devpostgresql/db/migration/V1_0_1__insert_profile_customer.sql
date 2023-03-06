INSERT
INTO
  t_profile
  (created_at, created_by, fullname, id_account, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 'Administrator 1', 1, FALSE),
  (NOW(), 'FLYWAY', 'Administrator 2', 2, FALSE),
  (NOW(), 'FLYWAY', 'Dimas Danang', 3, FALSE);

INSERT
INTO
  t_customer
  (created_at, created_by, balance, point, id_account, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 9999999, 9999999, 1, FALSE),
  (NOW(), 'FLYWAY', 1111111, 1111111, 2, FALSE),
  (NOW(), 'FLYWAY', 0, 50000, 3, FALSE);