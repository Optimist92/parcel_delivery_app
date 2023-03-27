INSERT INTO "users"
(id, username, email, password, role) VALUES
(gen_random_uuid(), 'admin', 'admin@ma.il', '$2a$12$yXIFr5G8wbxh8GJN27TxRuPLi2b2Lk.YEdFEza5e3.c4qZHoNmHXK', 'ROLE_ADMIN'),
(gen_random_uuid(), 'parcel', 'parcel@ma.il', '$2a$12$lQZ2AiQfitJNh0gKdXvkkOtL8ojPaGk7agNwOYR8lrJwk3rBRu8O.', 'ROLE_COURIER'),
(gen_random_uuid(), 'user1', 'e@ma1.il', '$2a$12$FtSw0JSzgwoRcxnOZ.VVLOHUv61Tq8Sl2wCVbhzO.FvBWYhEozRKG', 'ROLE_CUSTOMER'),
(gen_random_uuid(), 'user2', 'e@ma2.il', '$2a$12$..d53QTbU4z.eOWUfrRNU.SX4.95U195kXXEBVKT18pSPQqBxoIZ6', 'ROLE_CUSTOMER'),
(gen_random_uuid(), 'user3', 'e@ma3.il', '$2a$12$lICShFmdmMgANiyk1oZ1ve4tlU9lqN8p7a4LotUGODtv3XRdko5R6', 'ROLE_CUSTOMER');