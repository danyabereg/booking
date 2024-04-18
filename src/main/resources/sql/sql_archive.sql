DO $$
    DECLARE
        curr_date DATE = now()::DATE;
    BEGIN
        INSERT INTO booking.public.reservations_archive
            (SELECT *
             FROM booking.public.reservations
             WHERE booking.public.reservations.date_to > curr_date
               AND booking.public.reservations.last_update + 14 < curr_date);

        DELETE FROM booking.public.reservations
        WHERE booking.public.reservations.date_to > curr_date
          AND booking.public.reservations.last_update + 14 < curr_date;
    END $$;