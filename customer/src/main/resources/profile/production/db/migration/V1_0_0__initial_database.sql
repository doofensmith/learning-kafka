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