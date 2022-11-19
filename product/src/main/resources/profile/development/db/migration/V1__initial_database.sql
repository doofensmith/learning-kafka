CREATE TABLE t_product (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at TIMESTAMP,
   is_deleted BOOLEAN,
   created_at TIMESTAMP NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at TIMESTAMP,
   updated_by VARCHAR(50),
   name VARCHAR(50) NOT NULL,
   description CLOB NOT NULL,
   price DOUBLE NOT NULL,
   stock INT NOT NULL,
   CONSTRAINT pk_t_product PRIMARY KEY (id)
);
