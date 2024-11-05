CREATE OR REPLACE FUNCTION update_annual_holidays(p_year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE)::INT) RETURNS VOID AS $$
DECLARE
    new_years DATE := make_date(p_year, 1, 1);
    presidents_day DATE;
    memorial_day DATE;
    independence_day DATE := make_date(p_year, 7, 4);
    labor_day DATE;
    juneteenth DATE := make_date(p_year, 6, 19);
    veterans_day DATE := make_date(p_year, 11, 11);
    thanksgiving DATE;
    christmas DATE := make_date(p_year, 12, 25);
BEGIN
    presidents_day := date_trunc('month', make_date(p_year, 2, 1)) + 
                      ((1 - EXTRACT(DOW FROM date_trunc('month', make_date(p_year, 2, 1))))::INT % 7 + 7) * interval '1 day' + interval '2 weeks';

    memorial_day := date_trunc('month', make_date(p_year, 6, 1)) - interval '1 day';
    WHILE EXTRACT(DOW FROM memorial_day) <> 1 LOOP
        memorial_day := memorial_day - interval '1 day';
    END LOOP;

    labor_day := date_trunc('month', make_date(p_year, 9, 1));
    IF EXTRACT(DOW FROM labor_day) <> 1 THEN
        labor_day := labor_day + ((1 - EXTRACT(DOW FROM labor_day))::INT + 7) % 7 * interval '1 day';
    END IF;

    thanksgiving := date_trunc('month', make_date(p_year, 11, 1)) 
                    + ((4 - EXTRACT(DOW FROM make_date(p_year, 11, 1))) + 7) % 7 * interval '1 day'
                    + interval '3 weeks';

    INSERT INTO Holiday (holidayName, holdayDate) VALUES
        ('New Year''s Day', new_years),
        ('Presidents'' Day', presidents_day),
        ('Memorial Day', memorial_day),
        ('Independence Day', independence_day),
        ('Labor Day', labor_day),
        ('Juneteenth', juneteenth),
        ('Veterans Day', veterans_day),
        ('Thanksgiving', thanksgiving),
        ('Christmas', christmas)
    ON CONFLICT (holidayName) 
    DO UPDATE SET holdayDate = EXCLUDED.holdayDate;

END;
$$ LANGUAGE plpgsql;

SELECT update_annual_holidays();
