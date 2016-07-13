-- run as root
create user 'sales_tax_ro'@'localhost' identified by 'Zusse877';
grant select on sales_tax.* to 'sales_tax_ro'@'localhost';