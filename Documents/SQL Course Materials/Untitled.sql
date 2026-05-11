use sql_invoicing;
SELECT * FROM payment_methods;
update payment_methods set name = "POS" where payment_method_id = 5;
delete from payment_methods where payment_method_id = 5;
SELECT * FROM payment_methods;