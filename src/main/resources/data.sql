insert into privilege
(code)
values
('user:read_summary'),
('user:read_contact'),
('user:read_name'),
('user:read_full');


insert into role
(code)
values
('buyer'),
('seller');

insert into role_privilege
(role_code, privilege_code)
values
('seller','user:read_contact'),
('buyer','user:read_name');

insert into auction_user
(id, username, password, first_name, middle_name, last_name, email, contact_no, zipcode, active, created_at)
values
('cb6a9cef-b454-46c6-9aa9-f391e1212091', 'seller1','{noop}password','Jake','Joseph','Doe','jake.doe@gmail.com','1234567890','12345','1',CURRENT_TIMESTAMP()),
('f7027dcc-9e74-4696-b8da-877b58fd7cec', 'buyer1','{noop}password','John','Joseph','Doe','john.doe@gmail.com','1234567890','12345','1',CURRENT_TIMESTAMP());


insert into user_role
(user_id, role_code)
values
('cb6a9cef-b454-46c6-9aa9-f391e1212091', 'seller'),
('f7027dcc-9e74-4696-b8da-877b58fd7cec','buyer');



