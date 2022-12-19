CREATE TABLE t_notification (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_at datetime NOT NULL,
   created_by VARCHAR(50) NOT NULL,
   updated_at datetime NULL,
   updated_by VARCHAR(50) NULL,
   content TEXT NOT NULL,
   publisher VARCHAR(50) DEFAULT 'SYSTEM' NULL,
   receiver VARCHAR(50) DEFAULT 'EVERYONE' NULL,
   CONSTRAINT pk_t_notification PRIMARY KEY (id)
);

