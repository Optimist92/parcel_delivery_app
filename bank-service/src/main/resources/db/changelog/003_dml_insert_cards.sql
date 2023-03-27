
INSERT INTO "cards"
(id, expiration_date, holder, number, status, account) VALUES

                             (gen_random_uuid(), '2030-03-17 00:00:00', 'admin', 'card_admin', 'STATUS_ACTIVATED', '1e65d3ec-3e97-422a-af66-d30d5d99a539'),
                             (gen_random_uuid(), '2030-03-17 00:00:00', 'user1', 'card_user1', 'STATUS_ACTIVATED', 'bead55a8-b1fc-45a9-b217-264375ebb021'),
                             (gen_random_uuid(), '2030-03-17 00:00:00', 'user2', 'card_user2', 'STATUS_ACTIVATED', 'edf0e8a8-a199-47ea-acc1-8e9c1f755287'),
                             (gen_random_uuid(), '2030-03-17 00:00:00', 'user3', 'card_user3', 'STATUS_ACTIVATED', '9884744a-f0e3-4a03-8c10-91b582edd07e');