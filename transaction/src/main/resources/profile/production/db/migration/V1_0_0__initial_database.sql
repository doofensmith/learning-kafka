CREATE TABLE t_transaction (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   issued_at datetime NOT NULL,
   settle_at datetime NULL,
   quantity INT NOT NULL,
   total DOUBLE NOT NULL,
   status VARCHAR(255) NOT NULL,
   id_product BIGINT NOT NULL,
   id_account BIGINT NOT NULL,
   CONSTRAINT pk_t_transaction PRIMARY KEY (id)
);