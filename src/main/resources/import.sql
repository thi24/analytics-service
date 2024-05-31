DROP SCHEMA IF EXISTS benevolo_analytics_service CASCADE;
CREATE SCHEMA benevolo_analytics_service;

SET schema 'benevolo_analytics_service';

CREATE TABLE event_view
(
    event_id       VARCHAR(256) NOT NULL,
    occurring_date DATE         NOT NULL,
    views          INT          NOT NULL,
    PRIMARY KEY (event_id, occurring_date)
);


CREATE TABLE ticket_validation
(
    event_id        VARCHAR(256) NOT NULL,
    validation_date DATE         NOT NULL,
    validation_time TIME         NOT NULL,
    count           INT,
    PRIMARY KEY (event_id, validation_date, validation_time)

);


INSERT INTO ticket_validation(event_id, validation_date, validation_time, count)
SELECT '383f700f-5449-4e40-b509-bee0b5d139d6'
     , (current_date - interval '1 day' * s1.i)::date
     , ((s2.i)::text || ':00:00')::time
     , floor(random() * 1000 + 100)::int
FROM generate_series(0, 29) s1(i),
     generate_series(14, 23) s2(i)
WHERE NOT EXISTS (SELECT 1
                  FROM ticket_validation
                  WHERE event_id = '383f700f-5449-4e40-b509-bee0b5d139d6'
                    AND validation_date = (current_date - interval '1 day' * s1.i)::date
                    AND validation_time = ((s2.i)::text || ':00:00')::time);



INSERT INTO event_view(event_id, occurring_date, views)
SELECT '383f700f-5449-4e40-b509-bee0b5d139d6'
     , (NOW() - (floor(random() * 120) || ' days')::interval)::date
     , floor(random() * 2900 + 100)::int
FROM generate_series(1, 300) id
ON CONFLICT (event_id, occurring_date) DO NOTHING;