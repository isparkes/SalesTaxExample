insert into sales_tax_rate (ID,tax_rate_name,tax_rate_percent)
values
    (1,'default_tax_rate','10'),
    (2,'exempt','0');

insert into product_category (ID,category_name,tax_rate_id)
values
    (1,'default_tax_rate',1),
    (2,'books',2),
    (3,'food',2),
    (4,'medicines',2);

insert into import_duty_rate (ID,category_name,tax_rate_percent)
values
    (1,'imported','5'),
    (2,'domestic','0');

insert into products (ID,product_name,category_id)
values
    (1,'book',2),
    (2,'music CD',1),
    (3,'chocolate bar',3),
    (4,'box of chocolates',3),
    (5,'bottle of perfume',1),
    (6,'packet of headache pills',4);
