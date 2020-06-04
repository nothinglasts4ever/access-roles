CREATE UNIQUE INDEX location_unique_name ON location (name) WHERE deleted_at IS NULL;
CREATE UNIQUE INDEX access_role_unique_person_location ON access_role (person_id, location_id) WHERE deleted_at IS NULL;

INSERT INTO location (id, name)
VALUES ('5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8', 'Gazorpazorp');
INSERT INTO location (id, name)
VALUES ('584c3365-bb4a-4a78-a2d6-d9b6d256dc19', 'Cronenberg World');
INSERT INTO location (id, name)
VALUES ('5e644a41-50ff-43e5-be85-26d6f9619b6b', 'Earth');
INSERT INTO location (id, name)
VALUES ('7ae8c747-1a2a-460e-88cf-f4874ccd8d95', 'Pluto');

INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('373ea031-93da-46f0-b2d1-ebf6f851ddd7', '2049-03-21 13:30:37', '9f0011f5-72d6-4275-8555-15e350362828', '2019-03-21 13:30:37', '5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('f4cf559f-21bf-4818-82d3-3298fe482dc0', '2029-03-21 13:30:37', '9f0011f5-72d6-4275-8555-15e350362828', '2019-03-21 13:30:37', '584c3365-bb4a-4a78-a2d6-d9b6d256dc19');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('5728c3d1-1ecd-4da9-ac25-e0a5646e507a', '2022-03-21 13:30:37', '9f0011f5-72d6-4275-8555-15e350362828', '2019-03-21 13:30:37', '5e644a41-50ff-43e5-be85-26d6f9619b6b');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('54f50077-7fc7-4c00-874e-d15ba0bedc49', '2026-03-21 13:30:37', '9f0011f5-72d6-4275-8555-15e350362828', '2019-03-21 13:30:37', '7ae8c747-1a2a-460e-88cf-f4874ccd8d95');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('4525b0a4-00ed-4779-aa09-24d920d53494', '2089-03-21 13:30:37', '4ab0e982-a990-4e98-8c25-de6e025681a6', '2019-03-21 13:30:37', '5e644a41-50ff-43e5-be85-26d6f9619b6b');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('d69a6cd2-2cb7-4c5a-ba37-bec87d1b2516', '2029-03-21 13:30:37', '4ab0e982-a990-4e98-8c25-de6e025681a6', '2019-03-21 13:30:37', '5dda0159-ed5c-44d4-b5f7-efb68ffbe8f8');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('2b9362b4-52ba-4e53-b410-96792ba54e82', '2089-03-21 13:30:37', '23e841a0-4be0-4723-bbd5-21f8b6ee82af', '2019-03-21 13:30:37', '5e644a41-50ff-43e5-be85-26d6f9619b6b');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('cf9b6a94-0393-4d7d-897d-ed98f186e04e', '2069-03-21 13:30:37', '34afaaaa-3eca-4245-a8df-185d8af54fce', '2019-03-21 13:30:37', '5e644a41-50ff-43e5-be85-26d6f9619b6b');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('6d4066e8-d471-4fe5-8982-d96e690794d6', '2069-03-21 13:30:37', 'fd9da139-7bc5-4885-982b-d107732f1cc1', '2019-03-21 13:30:37', '5e644a41-50ff-43e5-be85-26d6f9619b6b');
INSERT INTO access_role (id, end_time, person_id, start_time, location_id)
VALUES ('3d6e4fa8-5785-47e5-8ea2-7bd6c4ce80a9', '2119-03-21 13:30:37', 'fd9da139-7bc5-4885-982b-d107732f1cc1', '2019-03-21 13:30:37', '7ae8c747-1a2a-460e-88cf-f4874ccd8d95');