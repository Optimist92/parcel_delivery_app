INSERT INTO "users"
(id, username, email, password, role) VALUES
(gen_random_uuid(), 'admin', 'admin@ma.il', '$2a$12$yXIFr5G8wbxh8GJN27TxRuPLi2b2Lk.YEdFEza5e3.c4qZHoNmHXK', 'ROLE_ADMIN'),
(gen_random_uuid(), 'courier1', 'parcel1@ma.il', '$2a$12$In8VHKJcscmZF0xP7.niGeR5KUBPloedVPgWOoZFomex.fmjymh6y', 'ROLE_COURIER'),
(gen_random_uuid(), 'courier2', 'parcel2@ma.il', '$2a$12$VK13Cf5yFunsquOpw4MdLO5SpJap8ytiiIhY6cubwLa5QHj/3Ig3G', 'ROLE_COURIER'),
(gen_random_uuid(), 'courier3', 'parcel3@ma.il', '$2a$12$/LBpBCkVVD7F0UfgmjF0FOr3BEOGlOyZnSr1sZ1xf/z4VBGf0SFAa', 'ROLE_COURIER'),
(gen_random_uuid(), 'courier4', 'parcel4@ma.il', '$2a$12$DmV4HDKpSev49yUeYWMrmeJgsO3UVd3kQXDzSOiE1rcXtgj3fbLeq', 'ROLE_COURIER'),
(gen_random_uuid(), 'user1', 'e@ma1.il', '$2a$12$FtSw0JSzgwoRcxnOZ.VVLOHUv61Tq8Sl2wCVbhzO.FvBWYhEozRKG', 'ROLE_CUSTOMER'),
(gen_random_uuid(), 'user2', 'e@ma2.il', '$2a$12$..d53QTbU4z.eOWUfrRNU.SX4.95U195kXXEBVKT18pSPQqBxoIZ6', 'ROLE_CUSTOMER'),
(gen_random_uuid(), 'user3', 'e@ma3.il', '$2a$12$lICShFmdmMgANiyk1oZ1ve4tlU9lqN8p7a4LotUGODtv3XRdko5R6', 'ROLE_CUSTOMER');