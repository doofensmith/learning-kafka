CREATE TABLE t_profile (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   fullname VARCHAR(100) NOT NULL,
   profile_pic VARCHAR(255) DEFAULT '-' NULL,
   email VARCHAR(50) NULL,
   phone_number VARCHAR(20) NULL,
   id_account BIGINT NOT NULL,
   CONSTRAINT pk_t_profile PRIMARY KEY (id)
);

CREATE TABLE t_customer (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   balance DOUBLE NOT NULL,
   point DOUBLE NOT NULL,
   id_account BIGINT NOT NULL,
   CONSTRAINT pk_t_customer PRIMARY KEY (id)
);