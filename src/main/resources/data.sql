insert into contact_type (contact_type_id, name)
values (1, 'PICKUP'),
       (2, 'DELIVER');
insert into order_type(order_type_id, name)
values (1, 'ALLO'),
       (2, 'AUTO');
insert into order_status(order_status_id, name)
values (1, 'Open'),
       (2, 'Closed'),
       (3, 'Unknown');
insert into transportation_request_status(transportation_request_status_id, name)
values (1, 'Review Request'),
       (2, 'Place Order'),
       (3, 'Send Container'),
       (4, 'Confirm Container Delivery'),
       (5, 'Confirm Product Pickup'),
       (6, 'Confirm Product Delivery'),
       (7, 'Confirm Container Return'),
       (8, 'Close Request'),
       (9, 'Canceled');
insert into address_type(address_type_id, name)
values (1, 'PICKUP'),
       (2, 'DELIVER');
