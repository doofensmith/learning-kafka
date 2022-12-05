CREATE TABLE t_role (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   `role` VARCHAR(10) NOT NULL,
   CONSTRAINT pk_t_role PRIMARY KEY (id)
);

CREATE TABLE t_profile (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   fullname VARCHAR(100) NOT NULL,
   email VARCHAR(50) NULL,
   phone_number VARCHAR(20) NULL,
   CONSTRAINT pk_t_profile PRIMARY KEY (id)
);

CREATE TABLE t_account_master (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   username VARCHAR(50) NOT NULL,
   password VARCHAR(100) NOT NULL,
   is_active BIT(1) DEFAULT 1 NULL,
   login_attemp SMALLINT DEFAULT 0 NULL,
   id_profile BIGINT NOT NULL,
   CONSTRAINT pk_t_account_master PRIMARY KEY (id)
);

ALTER TABLE t_account_master ADD CONSTRAINT uc_t_account_master_username UNIQUE (username);

ALTER TABLE t_account_master ADD CONSTRAINT FK_T_ACCOUNT_MASTER_ON_ID_PROFILE FOREIGN KEY (id_profile) REFERENCES t_profile (id);

CREATE TABLE bt_account_roles (
  id_account BIGINT NOT NULL,
   id_role BIGINT NOT NULL
);

ALTER TABLE bt_account_roles ADD CONSTRAINT fk_btaccrol_on_account_dao FOREIGN KEY (id_account) REFERENCES t_account_master (id);

ALTER TABLE bt_account_roles ADD CONSTRAINT fk_btaccrol_on_role_dao FOREIGN KEY (id_role) REFERENCES t_role (id);