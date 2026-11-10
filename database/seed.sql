-- =====================================================================
--  SwiftWheels - demo data (safe to run more than once: only fills an empty database)
--
--  Demo accounts
--    admin@swiftwheels.com / Admin123!   (admin)
--    demo@swiftwheels.com  / Demo123!    (customer, has one active rental)
-- =====================================================================

DO
$$
BEGIN
    IF EXISTS (SELECT 1 FROM vehicles) OR EXISTS (SELECT 1 FROM kullanici) THEN
        RAISE NOTICE 'Database already has data - seed skipped.';
        RETURN;
    END IF;

    -- Passwords are PBKDF2 hashes (see PasswordHasher.java)
    INSERT INTO kullanici (id, fname, lname, phone, mail, password, is_admin)
    VALUES (1, 'Admin', 'SwiftWheels', '05437457478', 'admin@swiftwheels.com',
            'pbkdf2_sha256$210000$Lyk+mZ0ah9KMRSlmBCM4XQ==$EUSgXmoUPkaCB2SZpodzwW254h8zWqfHMryQ+UYirsQ=', TRUE),
           (2, 'Demo', 'Kullanıcı', '05318361627', 'demo@swiftwheels.com',
            'pbkdf2_sha256$210000$cCWDuEifJzxIOv9Twf/JKw==$74SkKdtAXEZ1YotJ1Ix8CK0JaTnYE2BkECXTwMSe8+8=', FALSE);

    -- ids 1, 2 and 8 have bundled photos (src/main/resources/images/sample-vehicle-photos.properties)
    INSERT INTO vehicles (id, make, model, year, km, color, engine_power, engine_capacity, price, stock)
    VALUES (1, 'Mercedes-Benz', 'C 200', 2022, 18500, 'Gümüş', '204 HP', '1.5', 95, 3),
           (2, 'Ford', 'Focus', 2021, 42000, 'Kırmızı', '125 HP', '1.0', 55, 4),
           (3, 'Fiat', 'Egea', 2023, 12000, 'Beyaz', '95 HP', '1.4', 40, 6),
           (4, 'Renault', 'Clio', 2022, 30500, 'Mavi', '90 HP', '1.0', 38, 5),
           (5, 'Jeep', 'Compass', 2022, 25000, 'Siyah', '130 HP', '1.3', 85, 2),
           (6, 'Toyota', 'RAV4', 2023, 9000, 'Beyaz', '218 HP', '2.5', 110, 2),
           (7, 'Dacia', 'Duster', 2021, 51000, 'Turuncu', '115 HP', '1.5', 50, 4),
           (8, 'Yamaha', 'YZF-R1', 2022, 6500, 'Siyah', '200 HP', '998 cc', 70, 2),
           (9, 'Honda', 'CB650R', 2021, 14000, 'Gri', '95 HP', '649 cc', 45, 3),
           (10, 'Vespa', 'Primavera 125', 2023, 3000, 'Yeşil', '11 HP', '125 cc', 20, 5);

    INSERT INTO car_details (vehicle_id, seri, fuel)
    VALUES (1, 'C-Serisi', 'Benzin'),
           (2, 'Hatchback', 'Benzin'),
           (3, 'Sedan', 'Dizel'),
           (4, 'Hatchback', 'Benzin');

    INSERT INTO jeep_details (vehicle_id, seri, fuel, traction)
    VALUES (5, 'Limited', 'Benzin', '4x2'),
           (6, 'Hybrid', 'Hibrit', '4x4'),
           (7, 'Prestige', 'Dizel', '4x4');

    INSERT INTO motor_details (vehicle_id, type, cooling, cylinder)
    VALUES (8, 'Sport', 'Sıvı', '4'),
           (9, 'Naked', 'Sıvı', '4'),
           (10, 'Scooter', 'Hava', '1');

    PERFORM setval('kullanici_id_seq', (SELECT MAX(id) FROM kullanici));
    PERFORM setval('vehicles_id_seq', (SELECT MAX(id) FROM vehicles));

    -- One finished rental (counts towards revenue) and one active rental for the demo user.
    -- The triggers keep the stock in sync.
    INSERT INTO rented_vehicles (user_id, address, purchase_date, return_date, vehicle_id, fee)
    VALUES (2, 'Emniyet, Milas Sk. No:30, Yenimahalle/Ankara', CURRENT_DATE - 10, CURRENT_DATE - 7, 2, 165);
    UPDATE rented_vehicles SET returned_at = CURRENT_DATE - 7 WHERE vehicle_id = 2 AND user_id = 2;

    CALL vehicle_rent(2, 'Kızılay, Atatürk Blv. No:1, Çankaya/Ankara', 3, 3, 140);

    RAISE NOTICE 'Demo data inserted.';
END
$$;
