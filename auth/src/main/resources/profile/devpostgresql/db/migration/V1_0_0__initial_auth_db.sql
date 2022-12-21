CREATE TABLE t_role (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   deleted_at TIMESTAMP WITHOUT TIME ZONE,
   is_deleted BOOLEAN,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(50),
   role VARCHAR(10) NOT NULL,
   CONSTRAINT pk_t_role PRIMARY KEY (id)
);

CREATE TABLE t_account_master (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   deleted_at TIMESTAMP WITHOUT TIME ZONE,
   is_deleted BOOLEAN,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(50),
   username VARCHAR(50) NOT NULL,
   password VARCHAR(100) NOT NULL,
   is_active BOOLEAN DEFAULT TRUE,
   login_attemp SMALLINT DEFAULT 0,
   CONSTRAINT pk_t_account_master PRIMARY KEY (id)
);

ALTER TABLE t_account_master ADD CONSTRAINT uc_t_account_master_username UNIQUE (username);

CREATE TABLE bt_account_roles (
  id_account BIGINT NOT NULL,
   id_role BIGINT NOT NULL
);

ALTER TABLE bt_account_roles ADD CONSTRAINT fk_btaccrol_on_account_dao FOREIGN KEY (id_account) REFERENCES t_account_master (id);

ALTER TABLE bt_account_roles ADD CONSTRAINT fk_btaccrol_on_role_dao FOREIGN KEY (id_role) REFERENCES t_role (id);