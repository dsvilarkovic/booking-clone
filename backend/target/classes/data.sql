	INSERT INTO additional_service ("id", "name") VALUES 
(1,'Pegla');
INSERT INTO additional_service ("id", "name") VALUES 
(2,'Wi fi');
INSERT INTO public.additional_service ("id", "name") VALUES 
(3,'AC');
INSERT INTO public.additional_service ("id", "name") VALUES 
(4,'Frizider');



INSERT INTO accommodation_category ("id", "name") VALUES 
(1,'hotel');
INSERT INTO accommodation_category ("id", "name") VALUES 
(2,'motel');
INSERT INTO accommodation_category ("id", "name") VALUES 
(3,'apartmani');


INSERT INTO accommodation_type ("id", "name") VALUES 
(1,'1. tip');
INSERT INTO accommodation_type ("id", "name") VALUES 
(2,'drugi_tip');
INSERT INTO accommodation_type ("id", "name") VALUES 
(3,'treci tip');
INSERT INTO accommodation_type ("id", "name") VALUES 
(4,'Cetvrti tip');


INSERT INTO public.users (address,email,first_name,last_name,"password",pib,user_type) VALUES 
(NULL,'admin@admin.rs',NULL,NULL,'123',NULL,NULL);


INSERT INTO public.location(
	id, address, city, country, latitude, longitude)
	VALUES (12, 'Bogu iza nogu 4', 'Borkovac na vodi', 'Srbija', 45.036508, 19.820200);

INSERT INTO public.accommodation(
	id, description, name, accommodation_category_id, accommodation_type_id, location_id, user_id)
	VALUES (1, 'Neki opis', 'Neko ime', 1, 1, 12, 1);

INSERT INTO public.accommodation_unit(
	id, cancelation_period, capacity, default_price, name)
	VALUES (12, 7, 4, 120, 'Apartman na vodi');