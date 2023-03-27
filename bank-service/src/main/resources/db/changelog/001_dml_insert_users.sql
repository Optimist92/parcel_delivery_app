
INSERT INTO "users"
(id, username, email, password, role) VALUES
                             (gen_random_uuid(), 'admin', 'admin@ma.il', '$2a$12$SvpxATE.JJribtc6VVQALulxpT5GdFzeWUT2d7EWyc9t3J6oGdeRC', 'ROLE_ADMIN'),
                             (gen_random_uuid(), 'parcel', 'parcel@ma.il', '$2a$12$Co9gypGfbJLQwikdcMdTy.g5TBrAQqjrI3z6X8osScHhZFaDhasSi', 'ROLE_CONTRACTOR'),
                             (gen_random_uuid(), 'user1', 'e@ma1.il', '$2a$12$LyybAX7EpjuS6QW3SrKpGO8GJK7.YsR9Mug/.cq2IaMvCdmtGobv2', 'ROLE_CUSTOMER'),
                             (gen_random_uuid(), 'user2', 'e@ma2.il', '$2a$12$LyybAX7EpjuS6QW3SrKpGO8GJK7.YsR9Mug/.cq2IaMvCdmtGobv2', 'ROLE_CUSTOMER'),
                             (gen_random_uuid(), 'user3', 'e@ma3.il', '$2a$12$LyybAX7EpjuS6QW3SrKpGO8GJK7.YsR9Mug/.cq2IaMvCdmtGobv2', 'ROLE_CUSTOMER');