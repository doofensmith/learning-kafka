CREATE TABLE t_product (
  id BIGINT AUTO_INCREMENT NOT NULL,
   deleted_at datetime NULL,
   is_deleted BIT(1) NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   name VARCHAR(50) NOT NULL,
   `description` TEXT NOT NULL,
   price DOUBLE NOT NULL,
   stock INT NOT NULL,
   CONSTRAINT pk_t_product PRIMARY KEY (id)
);