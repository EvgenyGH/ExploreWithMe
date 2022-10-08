INSERT INTO users (user_id, email, name)
VALUES (1, 'email1@bk.ru', 'name1'),
       (2, 'email2@bl.ru', 'name2');

INSERT INTO categories (category_id, name)
VALUES (1, 'театр'),
       (2, 'кино'),
       (3, 'цирк');

INSERT INTO locations (location_id, latitude, longitude)
VALUES (1, 10, 10),
       (2, 10.1, 10.1),
       (3, 40, 40);

INSERT INTO set_locations (loc_id, name, longitude, latitude, description, radius)
VALUES (1, 'set town', 10, 10, 'test', 1),
       (2, 'set town2', 30, 30, 'test2', 1);


INSERT INTO events (event_id, annotation, created, description, event_date, paid,
                           participant_limit, published, request_moderation, state, title,
                           category_id, initiator_id, location_id)
VALUES (1, 'annotation1 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description1', '2023-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title1', 1, 1, 1),
       (2, 'annotation2 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description2', '2023-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PENDING', 'title2', 2, 1, 1),
       (3, 'annotation3 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description3', '2023-10-01 19:18:26.000000',
        false, 0, '2022-10-01 19:18:38.000000', false, 'PUBLISHED', 'title3', 3, 1, 1),
       (4, 'annotation4 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description4', '2021-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title4', 2, 1, 1),
       (5, 'annotation5', '2022-10-01 19:18:19.000000', 'description5', '2023-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title5', 3, 1, 1),
       (6, 'annotation6 LooKing FoR IT', '022-10-01 19:18:19.000000', 'description6', '4023-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title6', 3, 1, 1),
       (7, 'annotation7 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description7', '2023-10-01 19:18:26.000000',
        true, 1, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title7', 3, 1, 1),
       (8, 'annotation8 LooKing FoR IT', '2022-10-01 19:18:19.000000', 'description7', '2023-10-01 19:18:26.000000',
        true, 0, '2022-10-01 19:18:38.000000', true, 'PUBLISHED', 'title8', 3, 1, 1);

INSERT INTO participation_requests (request_id, created, status, event_id, requester_id)
VALUES (1, '2022-10-01 19:19:47.000000', 'CONFIRMED', 7, 2),
       (2, '2022-10-01 19:19:47.000000', 'CONFIRMED', 2, 2),
       (3, '2022-10-01 19:19:47.000000', 'CONFIRMED', 3, 2),
       (4, '2022-10-01 19:19:47.000000', 'CONFIRMED', 4, 2),
       (5, '2022-10-01 19:19:47.000000', 'CONFIRMED', 5, 2);