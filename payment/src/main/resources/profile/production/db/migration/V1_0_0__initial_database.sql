CREATE TABLE t_payment (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   req_amount DOUBLE NOT NULL,
   settle_amount DOUBLE NOT NULL,
   status VARCHAR(10) NOT NULL,
   paid_by VARCHAR(50) NOT NULL,
   id_transaction BIGINT NOT NULL,
   CONSTRAINT pk_t_payment PRIMARY KEY (id)
);