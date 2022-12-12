INSERT
INTO
  t_customer
  (created_at, created_by, balance, point, id_account, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 9999999, 9999999, 1, 0),
  (NOW(), 'FLYWAY', 9999999, 9999999, 2, 0),
  (NOW(), 'FLYWAY', 0, 100000, 3, 0);