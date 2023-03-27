
INSERT INTO "accounts"
(id, status, number, available_amount, reserved_amount, owner) VALUES

                             (gen_random_uuid(), 'STATUS_ACTIVE', 'parcel_acc', 0.0, 0.0, 'deb21ecd-3446-4436-a055-4b0a6638221c'),
                             (gen_random_uuid(), 'STATUS_ACTIVE', 'user1_acc', 100.0, 0.0, '68b22f57-c705-469d-a6f2-e78d7b6d28f8'),
                             (gen_random_uuid(), 'STATUS_ACTIVE', 'user2_acc', 200.0, 0.0, 'bf0f8407-4ad2-4601-81b4-a7bcb736e6e7'),
                             (gen_random_uuid(), 'STATUS_ACTIVE', 'user3_acc', 300.0, 0.0, 'bf11452d-6cdb-467f-9afe-f8cb971b38c5');