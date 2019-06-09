INSERT INTO public.users
(id, address, email, first_name, last_name, "password", pib, user_type)
VALUES(2, NULL, 'admin', NULL, NULL, '$2a$10$Zw1hlAxaMbZKsKMOxT7d0ujQx1qQSTmQMWOsNcjlZluQFbsngTdMK', NULL, NULL);



INSERT INTO public.accommodation
(id, description, "name", accommodation_category_id, accommodation_type_id, location_id, user_id)
VALUES(6, 'neki opis', 'neko ime', 1, 1, NULL, 2);

INSERT INTO public.accommodation_unit
(id, cancelation_period, capacity, default_price, "name")
VALUES(1, 319013, 2, NULL, 'President Suite');

INSERT INTO public.accommodation_unit_day
(accommodation_unit_id, day_id)
VALUES(1, 1);

INSERT INTO public.accommodation_unit_day
(accommodation_unit_id, day_id)
VALUES(1, 2);

INSERT INTO public."day"
(id, available, "date", price)
VALUES(1, false, 1903190, 100.00);


INSERT INTO public."day"
(id, available, "date", price)
VALUES(2, true, 1903191, 300.00);

