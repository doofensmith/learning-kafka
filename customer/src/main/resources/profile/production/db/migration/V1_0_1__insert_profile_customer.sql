INSERT
INTO
  t_profile
  (created_at, created_by, fullname, id_account, is_deleted, profile_pic)
VALUES
  (NOW(), 'FLYWAY', 'Administrator 1', 1, 0, '-'),
  (NOW(), 'FLYWAY', 'Administrator 2', 2, 0, '-'),
  (NOW(), 'FLYWAY', 'Dimas Danang', 3, 0, '-');

INSERT
INTO
  t_customer
  (created_at, created_by, balance, point, id_account, is_deleted)
VALUES
  (NOW(), 'FLYWAY', 9999999, 9999999, 1, 0),
  (NOW(), 'FLYWAY', 1111111, 1111111, 2, 0),
  (NOW(), 'FLYWAY', 0, 50000, 3, 0);