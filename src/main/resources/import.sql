DROP SCHEMA IF EXISTS benevolo_analytics_service CASCADE;
CREATE SCHEMA benevolo_analytics_service;

SET schema 'benevolo_analytics_service';

CREATE TABLE event_view (
    id VARCHAR(256) PRIMARY KEY,
    event_id VARCHAR(256) NOT NULL,
    occurring_date DATE NOT NULL,
    views INT NOT NULL
);

INSERT INTO event_view(id, event_id, occurring_date, views)
SELECT gen_random_uuid(), '383f700f-5449-4e40-b509-bee0b5d139d6' , (NOW() - (floor(random() * 120) || ' days')::interval)::date
     , floor(random() * 2900 + 100)::int
FROM generate_series(1,300) id;