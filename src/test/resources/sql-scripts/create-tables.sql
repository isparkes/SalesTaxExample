create table sales_tax_rate (
  `tax_rate_name` varchar(24) NOT NULL,
  `tax_rate_percent` double NOT NULL,
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
);
create index idx_str_trn on sales_tax_rate(tax_rate_name);

create table product_category  (
  `category_name` varchar(24) NOT NULL,
  `tax_rate_id` int unsigned NOT NULL ,
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
);
create index idx_pc_cn on product_category(category_name);

create table import_duty_rate (
  `category_name` varchar(24) NOT NULL,
  `tax_rate_percent` double NOT NULL,
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
);
create index idx_du_cn on import_duty_rate(category_name);

create table products (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(256) not null default '',
  `category_id` int unsigned NOT NULL,
  PRIMARY KEY (`ID`)
);
create index idx_products_pn on products(product_name);
