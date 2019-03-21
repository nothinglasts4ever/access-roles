INSERT INTO access_roles.location (id, name)
VALUES (1, 'Gazorpazorp');
INSERT INTO access_roles.location (id, name)
VALUES (2, 'Cronenberg World');
INSERT INTO access_roles.location (id, name)
VALUES (3, 'Earth');
INSERT INTO access_roles.location (id, name)
VALUES (4, 'Pluto');

INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (10, '2049-03-21 13:30:37', 1, '2019-03-21 13:30:37', 1);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (11, '2029-03-21 13:30:37', 1, '2019-03-21 13:30:37', 2);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (12, '2022-03-21 13:30:37', 1, '2019-03-21 13:30:37', 3);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (13, '2026-03-21 13:30:37', 1, '2019-03-21 13:30:37', 4);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (14, '2089-03-21 13:30:37', 2, '2019-03-21 13:30:37', 3);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (15, '2029-03-21 13:30:37', 2, '2019-03-21 13:30:37', 1);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (16, '2089-03-21 13:30:37', 3, '2019-03-21 13:30:37', 3);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (17, '2069-03-21 13:30:37', 4, '2019-03-21 13:30:37', 3);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (18, '2069-03-21 13:30:37', 5, '2019-03-21 13:30:37', 3);
INSERT INTO access_roles.access_role (id, end, person_id, start, location_id)
VALUES (19, '2119-03-21 13:30:37', 5, '2019-03-21 13:30:37', 4);