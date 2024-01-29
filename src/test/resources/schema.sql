create table if not exists product
(
    product_id           INT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name     VARCHAR(128)   NOT NULL ,
    category              VARCHAR(32)     NOT NULL ,
    image_url           VARCHAR(256)   NOT NULL ,
    price                   INT                       NOT NULL,
    stock                   INT                      NOT NULL,
    description         VARCHAR(1024) ,
    create_date                  TIMESTAMP        NOT NULL,
    last_modify_date         TIMESTAMP        NOT NULL
);

create table if not exists user
(
    user_id                 INT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email                  VARCHAR(256)   NOT NULL UNIQUE,
    password            VARCHAR(32)     NOT NULL ,
    create_date                  TIMESTAMP        NOT NULL,
    last_modify_date         TIMESTAMP        NOT NULL
);