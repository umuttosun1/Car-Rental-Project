-- =====================================================================
--  SwiftWheels - PostgreSQL schema
--  Run with:  psql -U <user> -d swiftwheels -v ON_ERROR_STOP=1 -f database/schema.sql
--  (scripts/setup-database.ps1 does this for you)
-- =====================================================================

SET client_min_messages = warning;  -- hide "already exists, skipping" notices on re-runs

-- ---------------------------------------------------------------------
--  TABLES
-- ---------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS kullanici
(
    id       SERIAL PRIMARY KEY,
    fname    VARCHAR(40)  NOT NULL,
    lname    VARCHAR(40)  NOT NULL,
    phone    VARCHAR(11)  NOT NULL,
    mail     VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,              -- PBKDF2 hash, never plain text
    is_admin BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS vehicles
(
    id              SERIAL PRIMARY KEY,
    make            VARCHAR(25) NOT NULL,
    model           VARCHAR(30) NOT NULL,
    year            INTEGER     NOT NULL,
    km              INTEGER     NOT NULL CHECK (km >= 0),
    color           VARCHAR(25) NOT NULL,
    engine_power    VARCHAR(20) NOT NULL,
    engine_capacity VARCHAR(20) NOT NULL,
    price           INTEGER     NOT NULL CHECK (price >= 0),   -- daily rental price
    stock           INTEGER     NOT NULL DEFAULT 5 CHECK (stock >= 0)
);

CREATE TABLE IF NOT EXISTS car_details
(
    vehicle_id INTEGER PRIMARY KEY REFERENCES vehicles (id) ON DELETE CASCADE,
    seri       VARCHAR(20) NOT NULL,
    fuel       VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS jeep_details
(
    vehicle_id INTEGER PRIMARY KEY REFERENCES vehicles (id) ON DELETE CASCADE,
    seri       VARCHAR(25) NOT NULL,
    fuel       VARCHAR(15) NOT NULL,
    traction   VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS motor_details
(
    vehicle_id INTEGER PRIMARY KEY REFERENCES vehicles (id) ON DELETE CASCADE,
    type       VARCHAR(20) NOT NULL,
    cooling    VARCHAR(10) NOT NULL,
    cylinder   VARCHAR(5)  NOT NULL
);

-- Rental history is kept forever so revenue reports stay correct.
-- A rental is "active" while returned_at IS NULL.
CREATE TABLE IF NOT EXISTS rented_vehicles
(
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER REFERENCES kullanici (id) ON DELETE SET NULL,
    address       VARCHAR NOT NULL,
    purchase_date DATE    NOT NULL,
    return_date   DATE    NOT NULL,
    vehicle_id    INTEGER REFERENCES vehicles (id) ON DELETE SET NULL,
    fee           INTEGER NOT NULL CHECK (fee >= 0),
    returned_at   DATE
);

CREATE TABLE IF NOT EXISTS user_logs
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL REFERENCES kullanici (id) ON DELETE CASCADE,
    login_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_action VARCHAR
);

-- ---------------------------------------------------------------------
--  FUNCTIONS
-- ---------------------------------------------------------------------

CREATE OR REPLACE FUNCTION account_read(p_user_id INTEGER)
    RETURNS TABLE (fname VARCHAR, lname VARCHAR, phone VARCHAR, mail VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    RETURN QUERY
        SELECT k.fname, k.lname, k.phone, k.mail
        FROM kullanici k
        WHERE k.id = p_user_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_all_rented_vehicles()
    RETURNS TABLE (user_id INTEGER, address VARCHAR, purchase_date DATE, return_date DATE,
                   vehicle_id INTEGER, fee INTEGER, returned_at DATE)
    LANGUAGE plpgsql AS
$$
BEGIN
    RETURN QUERY
        SELECT rv.user_id, rv.address, rv.purchase_date, rv.return_date,
               rv.vehicle_id, rv.fee, rv.returned_at
        FROM rented_vehicles rv
        ORDER BY rv.id DESC;
END;
$$;

-- Only the rentals the user still has to return
CREATE OR REPLACE FUNCTION get_rented_vehicles_by_user(p_user_id INTEGER)
    RETURNS TABLE (vehicle_id INTEGER, make VARCHAR, model VARCHAR, fee INTEGER,
                   purchase_date DATE, return_date DATE)
    LANGUAGE plpgsql AS
$$
BEGIN
    RETURN QUERY
        SELECT v.id, v.make, v.model, rv.fee, rv.purchase_date, rv.return_date
        FROM rented_vehicles rv
                 JOIN vehicles v ON rv.vehicle_id = v.id
        WHERE rv.user_id = p_user_id
          AND rv.returned_at IS NULL
        ORDER BY rv.purchase_date;
END;
$$;

CREATE OR REPLACE FUNCTION get_vehicle_details_by_type(p_vehicle_id INTEGER, p_vehicle_type TEXT)
    RETURNS TABLE (id INTEGER, make VARCHAR, model VARCHAR, year INTEGER, km INTEGER, color VARCHAR,
                   engine_power VARCHAR, engine_capacity VARCHAR, price INTEGER, stock INTEGER,
                   detail1 VARCHAR, detail2 VARCHAR, detail3 VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    RETURN QUERY
        SELECT g.* FROM get_vehicles(p_vehicle_type) g WHERE g.id = p_vehicle_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_vehicles(p_vehicle_type TEXT)
    RETURNS TABLE (id INTEGER, make VARCHAR, model VARCHAR, year INTEGER, km INTEGER, color VARCHAR,
                   engine_power VARCHAR, engine_capacity VARCHAR, price INTEGER, stock INTEGER,
                   detail1 VARCHAR, detail2 VARCHAR, detail3 VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    IF p_vehicle_type = 'car' THEN
        RETURN QUERY
            SELECT v.id, v.make, v.model, v.year, v.km, v.color, v.engine_power, v.engine_capacity,
                   v.price, v.stock, cd.seri, cd.fuel, NULL::VARCHAR
            FROM vehicles v
                     JOIN car_details cd ON v.id = cd.vehicle_id
            ORDER BY v.id;
    ELSIF p_vehicle_type = 'jeep' THEN
        RETURN QUERY
            SELECT v.id, v.make, v.model, v.year, v.km, v.color, v.engine_power, v.engine_capacity,
                   v.price, v.stock, jd.seri, jd.fuel, jd.traction
            FROM vehicles v
                     JOIN jeep_details jd ON v.id = jd.vehicle_id
            ORDER BY v.id;
    ELSIF p_vehicle_type = 'motor' THEN
        RETURN QUERY
            SELECT v.id, v.make, v.model, v.year, v.km, v.color, v.engine_power, v.engine_capacity,
                   v.price, v.stock, md.type, md.cooling, md.cylinder
            FROM vehicles v
                     JOIN motor_details md ON v.id = md.vehicle_id
            ORDER BY v.id;
    ELSE
        RAISE EXCEPTION 'Geçersiz araç türü: %', p_vehicle_type;
    END IF;
END;
$$;

-- Inserts (p_vehicle_id IS NULL) or updates a vehicle and returns its id.
CREATE OR REPLACE FUNCTION save_vehicle(
    p_type VARCHAR, p_vehicle_id INTEGER,
    p_make VARCHAR, p_model VARCHAR, p_year INTEGER, p_km INTEGER, p_color VARCHAR,
    p_engine_power VARCHAR, p_engine_capacity VARCHAR, p_price INTEGER, p_stock INTEGER,
    p_seri VARCHAR, p_fuel VARCHAR, p_traction VARCHAR,
    p_motor_type VARCHAR, p_cooling VARCHAR, p_cylinder VARCHAR)
    RETURNS INTEGER
    LANGUAGE plpgsql AS
$$
DECLARE
    v_id INTEGER := p_vehicle_id;
BEGIN
    IF p_type NOT IN ('car', 'jeep', 'motor') THEN
        RAISE EXCEPTION 'Geçersiz araç türü: %', p_type;
    END IF;

    IF v_id IS NULL THEN
        INSERT INTO vehicles (make, model, year, km, color, engine_power, engine_capacity, price, stock)
        VALUES (p_make, p_model, p_year, p_km, p_color, p_engine_power, p_engine_capacity, p_price, p_stock)
        RETURNING id INTO v_id;

        IF p_type = 'car' THEN
            INSERT INTO car_details (vehicle_id, seri, fuel) VALUES (v_id, p_seri, p_fuel);
        ELSIF p_type = 'jeep' THEN
            INSERT INTO jeep_details (vehicle_id, seri, fuel, traction) VALUES (v_id, p_seri, p_fuel, p_traction);
        ELSE
            INSERT INTO motor_details (vehicle_id, type, cooling, cylinder) VALUES (v_id, p_motor_type, p_cooling, p_cylinder);
        END IF;
    ELSE
        UPDATE vehicles
        SET make = p_make, model = p_model, year = p_year, km = p_km, color = p_color,
            engine_power = p_engine_power, engine_capacity = p_engine_capacity,
            price = p_price, stock = p_stock
        WHERE id = v_id;

        IF NOT FOUND THEN
            RAISE EXCEPTION 'Araç bulunamadı: %', v_id;
        END IF;

        IF p_type = 'car' THEN
            UPDATE car_details SET seri = p_seri, fuel = p_fuel WHERE vehicle_id = v_id;
        ELSIF p_type = 'jeep' THEN
            UPDATE jeep_details SET seri = p_seri, fuel = p_fuel, traction = p_traction WHERE vehicle_id = v_id;
        ELSE
            UPDATE motor_details SET type = p_motor_type, cooling = p_cooling, cylinder = p_cylinder WHERE vehicle_id = v_id;
        END IF;
    END IF;

    RETURN v_id;
END;
$$;

-- ---------------------------------------------------------------------
--  PROCEDURES
-- ---------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE account_insert(p_name VARCHAR, p_surname VARCHAR, p_phone VARCHAR,
                                           p_mail VARCHAR, p_password_hash VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO kullanici (fname, lname, phone, mail, password)
    VALUES (p_name, p_surname, p_phone, p_mail, p_password_hash);
END;
$$;

-- p_password_hash = NULL keeps the current password
CREATE OR REPLACE PROCEDURE account_update(p_user_id INTEGER, p_name VARCHAR, p_surname VARCHAR,
                                           p_phone VARCHAR, p_password_hash VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE kullanici
    SET fname    = p_name,
        lname    = p_surname,
        phone    = p_phone,
        password = COALESCE(p_password_hash, password)
    WHERE id = p_user_id;
END;
$$;

CREATE OR REPLACE PROCEDURE account_delete(p_user_id INTEGER)
    LANGUAGE plpgsql AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM rented_vehicles WHERE user_id = p_user_id AND returned_at IS NULL) THEN
        RAISE EXCEPTION 'Kiralanmış aracınız var, hesabınızı silemezsiniz';
    END IF;
    DELETE FROM kullanici WHERE id = p_user_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_vehicle(p_type VARCHAR, p_vehicle_id INTEGER)
    LANGUAGE plpgsql AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM rented_vehicles WHERE vehicle_id = p_vehicle_id AND returned_at IS NULL) THEN
        RAISE EXCEPTION 'Seçtiğiniz araç kiralı durumda, silme işlemi yapılamaz.';
    END IF;

    IF p_type NOT IN ('car', 'jeep', 'motor') THEN
        RAISE EXCEPTION 'Geçersiz araç türü: %', p_type;
    END IF;

    -- details rows are removed by ON DELETE CASCADE
    DELETE FROM vehicles WHERE id = p_vehicle_id;
END;
$$;

CREATE OR REPLACE PROCEDURE vehicle_rent(p_user_id INTEGER, p_address VARCHAR, p_vehicle_id INTEGER,
                                         p_days_between INTEGER, p_fee INTEGER)
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM kullanici WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Kullanıcı bulunamadı: %', p_user_id;
    END IF;

    IF p_days_between IS NULL OR p_days_between < 1 THEN
        RAISE EXCEPTION 'Kiralama süresi en az 1 gün olmalıdır';
    END IF;

    -- stock is decreased (and validated) by trg_decrease_stock
    INSERT INTO rented_vehicles (user_id, address, purchase_date, return_date, vehicle_id, fee)
    VALUES (p_user_id, p_address, CURRENT_DATE, CURRENT_DATE + p_days_between, p_vehicle_id, p_fee);
END;
$$;

CREATE OR REPLACE PROCEDURE return_vehicle(p_user_id INTEGER, p_vehicle_id INTEGER)
    LANGUAGE plpgsql AS
$$
BEGIN
    -- stock is increased by trg_increase_stock
    UPDATE rented_vehicles
    SET returned_at = CURRENT_DATE
    WHERE id = (SELECT id
                FROM rented_vehicles
                WHERE user_id = p_user_id
                  AND vehicle_id = p_vehicle_id
                  AND returned_at IS NULL
                ORDER BY id
                LIMIT 1);

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Bu kullanıcı için iade edilecek bir araç kaydı bulunamadı.';
    END IF;
END;
$$;

-- ---------------------------------------------------------------------
--  TRIGGERS
-- ---------------------------------------------------------------------

CREATE OR REPLACE FUNCTION decrease_vehicle_stock()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE vehicles SET stock = stock - 1 WHERE id = NEW.vehicle_id AND stock > 0;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Araç stoğu tükenmiş veya geçersiz araç ID: %', NEW.vehicle_id;
    END IF;
    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_decrease_stock
    AFTER INSERT ON rented_vehicles
    FOR EACH ROW
EXECUTE FUNCTION decrease_vehicle_stock();

CREATE OR REPLACE FUNCTION increase_vehicle_stock()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE vehicles SET stock = stock + 1 WHERE id = NEW.vehicle_id;
    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_increase_stock
    AFTER UPDATE OF returned_at ON rented_vehicles
    FOR EACH ROW
    WHEN (OLD.returned_at IS NULL AND NEW.returned_at IS NOT NULL)
EXECUTE FUNCTION increase_vehicle_stock();

CREATE OR REPLACE FUNCTION user_logs_trigger_function()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO user_logs (user_id, login_time, user_action)
    VALUES (NEW.id, CURRENT_TIMESTAMP, TG_OP);
    RETURN NULL;
END;
$$;

CREATE OR REPLACE TRIGGER log_user_insert
    AFTER INSERT ON kullanici
    FOR EACH ROW
EXECUTE FUNCTION user_logs_trigger_function();

CREATE OR REPLACE TRIGGER log_user_update
    AFTER UPDATE ON kullanici
    FOR EACH ROW
EXECUTE FUNCTION user_logs_trigger_function();

-- ---------------------------------------------------------------------
--  VIEW (admin dashboard)
-- ---------------------------------------------------------------------

CREATE OR REPLACE VIEW admin AS
SELECT (SELECT COUNT(*) FROM vehicles)                                    AS vehicle_type_number,
       (SELECT COUNT(*) FROM kullanici)                                   AS user_number,
       (SELECT COUNT(*) FROM rented_vehicles WHERE returned_at IS NULL)   AS rented_vehicle_number,
       (SELECT COALESCE(SUM(fee), 0) FROM rented_vehicles)                AS total_turnover,
       (SELECT COALESCE(SUM(stock), 0) FROM vehicles)                     AS vehicle_stock;
