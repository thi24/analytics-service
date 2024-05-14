CREATE TABLE event_view (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL,
    occurring_date DATE NOT NULL,
    views INT NOT NULL
);

INSERT INTO event_view(id, event_id, occurring_date, views)
SELECT gen_random_uuid(), '383f700f-5449-4e40-b509-bee0b5d139d6' , (NOW() - (floor(random() * 120) || ' days')::interval)::date
     , floor(random() * 2900 + 100)::int
FROM generate_series(1,300) id;