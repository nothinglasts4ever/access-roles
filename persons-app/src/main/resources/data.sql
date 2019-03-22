INSERT INTO persons.person (id, birthday, first_name, gender, last_name)
VALUES (1, '1949-03-21', 'Rick', 0, 'Sanchez');
INSERT INTO persons.person (id, birthday, first_name, gender, last_name)
VALUES (2, '2005-03-21', 'Morty', 0, 'Smith');
INSERT INTO persons.person (id, birthday, first_name, gender, last_name)
VALUES (3, '2002-03-21', 'Summer', 1, 'Smith');
INSERT INTO persons.person (id, birthday, first_name, gender, last_name)
VALUES (4, '1985-03-21', 'Beth', 1, 'Smith');
INSERT INTO persons.person (id, birthday, first_name, gender, last_name)
VALUES (5, '1985-03-21', 'Jerry', 0, 'Smith');

INSERT INTO persons.address (id, city, street, building, apartment)
VALUES (6, 'Black', '41st Street', 234, 2342);
INSERT INTO persons.address (id, city, street, building, apartment)
VALUES (7, 'Yellow', '32nd Street', 123, 9876);
INSERT INTO persons.address (id, city, street, building, apartment)
VALUES (8, 'Red', '23rd Street', 456, 1);

INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (1, 6);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (1, 7);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (2, 7);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (3, 7);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (4, 7);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (5, 7);
INSERT INTO persons.person_addresses (person_id, address_id)
VALUES (5, 8);