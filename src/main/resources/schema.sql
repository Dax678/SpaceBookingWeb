CREATE TABLE IF NOT EXISTS public.user
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(255) UNIQUE NOT NULL,
    password        VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    role            VARCHAR(255),
    user_details_id BIGINT
);

INSERT INTO public.user (username, password, email, role, user_details_id)
VALUES
    ('adminAcc', '$2a$10$O79XpRB7umbTmF4yb4kGC.4QeYxllusjJKidNsdz3bwgutC.CWzlC', 'john.doe@email.com', 'ADMIN', 1),
    ('User1', '$2a$10$O79XpRB7umbTmF4yb4kGC.4QeYxllusjJKidNsdz3bwgutC.CWzlC', 'jane.smith@email.com', 'USER', 2);

CREATE TABLE IF NOT EXISTS public.user_details
(
    user_id       SERIAL PRIMARY KEY,
    name          VARCHAR(10) NOT NULL,
    surname       VARCHAR(12) NOT NULL,
    address       VARCHAR(40) NOT NULL,
    phone_number  VARCHAR(10) NOT NULL,
    profile_image VARCHAR(255)
);

INSERT INTO public.user_details (name, surname, address, phone_number, profile_image)
VALUES
    ('John', 'Doe', '123 Main St', '1234567890', 'profile_image1.jpg'),
    ('Jane', 'Smith', '456 Elm St', '9876543210', 'profile_image2.jpg');

CREATE TABLE IF NOT EXISTS public.floor
(
    id        SERIAL PRIMARY KEY,
    floor_num VARCHAR(255) NOT NULL,
    name      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.space
(
    id                   SERIAL PRIMARY KEY,
    floor_id             BIGINT      NOT NULL,
    name                 VARCHAR(7)  NOT NULL,
    type                 VARCHAR(10) NOT NULL,
    monitor_number       INT         NOT NULL,
    is_height_adjustable BOOLEAN     NOT NULL,
    is_available         BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS public.reservation
(
    id                      SERIAL PRIMARY KEY,
    user_id                 BIGINT  NOT NULL,
    space_id                BIGINT  NOT NULL,
    payment_id              BIGINT,
    reservation_date        DATE    NOT NULL,
    reservation_status      BOOLEAN NOT NULL,
    entity_creation_date    DATE    NOT NULL,
    reservation_update_date DATE    NOT NULL
);

-- Dodaj zależności po utworzeniu tabel, jeśli nie istnieją jeszcze

ALTER TABLE public.user
    ADD CONSTRAINT fk_user_details FOREIGN KEY (user_details_id)
        REFERENCES public.user_details (user_id)
        DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.user_details
    ADD CONSTRAINT fk_user_details_user FOREIGN KEY (user_id)
        REFERENCES public.user (id)
        DEFERRABLE INITIALLY DEFERRED;


ALTER TABLE public.space
    ADD CONSTRAINT fk_space_floor FOREIGN KEY (floor_id)
        REFERENCES public.floor (id)
        DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.reservation
    ADD CONSTRAINT fk_reservation_user FOREIGN KEY (user_id)
        REFERENCES public.user (id)
        DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.reservation
    ADD CONSTRAINT fk_reservation_space FOREIGN KEY (space_id)
        REFERENCES public.space (id)
        DEFERRABLE INITIALLY DEFERRED;

CREATE VIEW reservation_details_view AS
SELECT
    r.id AS reservation_id,
    ud.name,
    ud.surname,
    s.name AS space_name,
    s.type AS space_type,
    s.is_height_adjustable,
    f.floor_num,
    r.reservation_date,
    r.reservation_status
FROM
    public.reservation r
        INNER JOIN
    public."user" u ON u.id = r.user_id
        INNER JOIN
    public.space s ON r.space_id = s.id
        INNER JOIN
    public.floor f ON s.floor_id = f.id
        LEFT JOIN
    public.user_details ud ON u.id = ud.user_id;

CREATE VIEW user_information_view AS
SELECT u.id, ud.name, ud.surname, u.email, ud.address, ud.phone_number, ud.profile_image
FROM public."user" u
         INNER JOIN public.user_details ud
                    ON u.id = ud.user_id;

CREATE VIEW user_reservation_view AS
SELECT r.id, r.user_id, s.name, s.type, s.is_height_adjustable, f.floor_num, r.reservation_date, r.reservation_status
FROM public.reservation r
         INNER JOIN public.space s
                    ON r.space_id = s.id
         INNER JOIN public.floor f
                    ON s.floor_id = f.id;


INSERT INTO public.floor (floor_num, name)
VALUES ('1', 'Reception'),
       ('2', 'Finance'),
       ('3', 'IT'),
       ('4', 'Car_Parking'),
       ('5', 'Car_Parking');

INSERT INTO public.space (floor_id, name, type, monitor_number, is_height_adjustable, is_available)
VALUES (1, 'F1_001', 'Standard', 1, false, true),
       (1, 'F1_002', 'Standard', 1, false, true),
       (1, 'F1_003', 'Standard', 1, false, true),
       (1, 'F1_004', 'Standard', 1, false, true),
       (1, 'F1_005', 'Standard', 1, false, true),
       (1, 'F1_006', 'Standard', 1, false, true),
       (1, 'F1_007', 'Standard', 1, false, true),
       (1, 'F1_008', 'Standard', 1, false, true),
       (1, 'F1_009', 'Standard', 1, false, true),
       (1, 'F1_010', 'Standard', 1, false, true),
       (1, 'F1_011', 'Standard', 1, false, true),
       (1, 'F1_012', 'Standard', 1, false, true),
       (2, 'F2_001', 'Standard', 1, false, true),
       (2, 'F2_002', 'Standard', 1, false, true),
       (2, 'F2_003', 'Standard', 1, false, true),
       (2, 'F2_004', 'Standard', 1, false, true),
       (2, 'F2_005', 'Standard', 1, false, true),
       (2, 'F2_006', 'Standard', 1, false, true),
       (2, 'F2_007', 'Standard', 1, false, true),
       (2, 'F2_008', 'Standard', 1, false, true),
       (2, 'F2_009', 'Standard', 1, false, true),
       (2, 'F2_010', 'Standard', 1, false, true),
       (2, 'F2_011', 'Standard', 1, false, true),
       (2, 'F2_012', 'Standard', 1, false, true),
       (2, 'F2_013', 'Standard', 1, false, true),
       (2, 'F2_014', 'Standard', 1, false, true),
       (2, 'F2_015', 'Standard', 1, false, true),
       (2, 'F2_016', 'Standard', 1, false, true),
       (2, 'F2_017', 'Standard', 1, false, true),
       (2, 'F2_018', 'Standard', 1, false, true),
       (2, 'F2_019', 'Tech', 2, true, true),
       (2, 'F2_020', 'Tech', 2, true, true),
       (2, 'F2_021', 'Tech', 2, true, true),
       (2, 'F2_022', 'Tech', 2, true, true),
       (2, 'F2_023', 'Tech', 2, true, true),
       (2, 'F2_024', 'Tech', 2, true, true),
       (2, 'F2_025', 'Tech', 2, true, true),
       (2, 'F2_026', 'Tech', 2, true, true),
       (2, 'F2_027', 'Tech', 2, true, true),
       (2, 'F2_028', 'Tech', 2, true, true),
       (2, 'F2_029', 'Tech', 2, true, true),
       (2, 'F2_030', 'Tech', 2, true, true),
       (2, 'F2_031', 'Tech', 2, true, true),
       (2, 'F2_032', 'Tech', 2, true, true),
       (2, 'F2_033', 'Tech', 2, true, true),
       (2, 'F2_034', 'Tech', 2, true, true),
       (2, 'F2_035', 'Tech', 2, true, true),
       (2, 'F2_036', 'Tech', 2, true, true),
       (2, 'F2_037', 'Room', 0, false, true),
       (2, 'F2_038', 'Room', 0, false, true),
       (2, 'F2_039', 'Room', 0, false, true),
       (2, 'F2_040', 'Room', 0, false, true),
       (3, 'F3_001', 'Standard', 1, false, true),
       (3, 'F3_002', 'Standard', 1, false, true),
       (3, 'F3_003', 'Standard', 1, false, true),
       (3, 'F3_004', 'Standard', 1, false, true),
       (3, 'F3_005', 'Standard', 1, false, true),
       (3, 'F3_006', 'Standard', 1, false, true),
       (3, 'F3_007', 'Standard', 1, false, true),
       (3, 'F3_008', 'Standard', 1, false, true),
       (3, 'F3_009', 'Standard', 1, false, true),
       (3, 'F3_010', 'Standard', 1, false, true),
       (3, 'F3_011', 'Standard', 1, false, true),
       (3, 'F3_012', 'Standard', 1, false, true),
       (3, 'F3_013', 'Standard', 1, false, true),
       (3, 'F3_014', 'Standard', 1, false, true),
       (3, 'F3_015', 'Standard', 1, false, true),
       (3, 'F3_016', 'Standard', 1, false, true),
       (3, 'F3_017', 'Standard', 1, false, true),
       (3, 'F3_018', 'Standard', 1, false, true),
       (3, 'F3_019', 'Tech', 2, true, true),
       (3, 'F3_020', 'Tech', 2, true, true),
       (3, 'F3_021', 'Tech', 2, true, true),
       (3, 'F3_022', 'Tech', 2, true, true),
       (3, 'F3_023', 'Tech', 2, true, true),
       (3, 'F3_024', 'Tech', 2, true, true),
       (3, 'F3_025', 'Tech', 2, true, true),
       (3, 'F3_026', 'Tech', 2, true, true),
       (3, 'F3_027', 'Tech', 2, true, true),
       (3, 'F3_028', 'Tech', 2, true, true),
       (3, 'F3_029', 'Tech', 2, true, true),
       (3, 'F3_030', 'Tech', 2, true, true),
       (3, 'F3_031', 'Tech', 2, true, true),
       (3, 'F3_032', 'Tech', 2, true, true),
       (3, 'F3_033', 'Tech', 2, true, true),
       (3, 'F3_034', 'Tech', 2, true, true),
       (3, 'F3_035', 'Tech', 2, true, true),
       (3, 'F3_036', 'Tech', 2, true, true),
       (3, 'F3_037', 'Room', 0, false, true),
       (3, 'F3_038', 'Room', 0, false, true),
       (3, 'F3_039', 'Room', 0, false, true),
       (3, 'F3_040', 'Room', 0, false, true),
       (4, 'F4_001', 'Standard', 0, false, true),
       (4, 'F4_002', 'Standard', 0, false, true),
       (4, 'F4_003', 'Standard', 0, false, true),
       (4, 'F4_004', 'Standard', 0, false, true),
       (4, 'F4_005', 'Standard', 0, false, true),
       (4, 'F4_006', 'Standard', 0, false, true),
       (4, 'F4_007', 'Standard', 0, false, true),
       (4, 'F4_008', 'Standard', 0, false, true),
       (4, 'F4_009', 'Standard', 0, false, true),
       (4, 'F4_010', 'Standard', 0, false, true),
       (4, 'F4_011', 'Standard', 0, false, true),
       (4, 'F4_012', 'Standard', 0, false, true),
       (4, 'F4_013', 'Standard', 0, false, true),
       (4, 'F4_014', 'Standard', 0, false, true),
       (4, 'F4_015', 'Standard', 0, false, true),
       (4, 'F4_016', 'Standard', 0, false, true),
       (4, 'F4_017', 'Standard', 0, false, true),
       (4, 'F4_018', 'Standard', 0, false, true),
       (4, 'F4_019', 'Standard', 0, false, true),
       (4, 'F4_020', 'Standard', 0, false, true),
       (4, 'F4_021', 'Motorbikes', 0, false, true),
       (4, 'F4_022', 'Motorbikes', 0, false, true),
       (4, 'F4_023', 'Motorbikes', 0, false, true),
       (4, 'F4_024', 'Motorbikes', 0, false, true),
       (4, 'F4_025', 'Motorbikes', 0, false, true),
       (5, 'F5_001', 'Standard', 0, false, true),
       (5, 'F5_002', 'Standard', 0, false, true),
       (5, 'F5_003', 'Standard', 0, false, true),
       (5, 'F5_004', 'Standard', 0, false, true),
       (5, 'F5_005', 'Standard', 0, false, true),
       (5, 'F5_006', 'Standard', 0, false, true),
       (5, 'F5_007', 'Standard', 0, false, true),
       (5, 'F5_008', 'Standard', 0, false, true),
       (5, 'F5_009', 'Standard', 0, false, true),
       (5, 'F5_010', 'Standard', 0, false, true),
       (5, 'F5_011', 'Standard', 0, false, true),
       (5, 'F5_012', 'Standard', 0, false, true),
       (5, 'F5_013', 'Standard', 0, false, true),
       (5, 'F5_014', 'Standard', 0, false, true),
       (5, 'F5_015', 'Standard', 0, false, true),
       (5, 'F5_016', 'Standard', 0, false, true),
       (5, 'F5_017', 'Standard', 0, false, true),
       (5, 'F5_018', 'Standard', 0, false, true),
       (5, 'F5_019', 'Motorbikes', 0, false, true),
       (5, 'F5_020', 'Motorbikes', 0, false, true),
       (5, 'F5_021', 'Motorbikes', 0, false, true),
       (5, 'F5_022', 'Motorbikes', 0, false, true),
       (5, 'F5_023', 'Motorbikes', 0, false, true),
       (5, 'F5_024', 'Motorbikes', 0, false, true),
       (5, 'F5_025', 'Motorbikes', 0, false, true);

INSERT INTO public.reservation (user_id, space_id, payment_id, reservation_date, reservation_status, entity_creation_date, reservation_update_date)
VALUES
    (2, 1, 1, '2023-10-12', true, '2023-10-12', '2023-10-12');