-- CREATE TABLE sphereofinfluence(
--  id SERIAL PRIMARY KEY NOT NULL,
--  sphere VARCHAR(32) NOT NULL
-- );

-- CREATE TABLE monsters(
--  id SERIAL PRIMARY KEY NOT NULL,
--  monster VARCHAR(32) NOT NULL
-- );

-- CREATE TABLE status(
--  id SERIAL PRIMARY KEY NOT NULL,
--  status VARCHAR(16) NOT NULL
-- );

-- CREATE TABLE gender(
--  id SERIAL PRIMARY KEY NOT NULL,
--  gender VARCHAR(16) NOT NULL
-- );


-- CREATE TABLE typeofgod (
--     id SERIAL PRIMARY KEY,
--     type VARCHAR(50) NOT NULL
-- );

-- CREATE TABLE gods (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(50) NOT NULL,
-- 	gender_id INTEGER REFERENCES gender(id),
-- 	sphere_id INTEGER REFERENCES sphereofinfluence(id),
--     type_id INTEGER REFERENCES typeofgod(id)
-- );


-- -- класс первый запросик 
-- SELECT gods.id, gods.name, typeofgod.type, gender.gender, sphereofinfluence.sphere
-- FROM gods
-- JOIN typeofgod ON gods.type_id = typeofgod.id
-- JOIN gender ON gods.gender_id = gender.id
-- JOIN sphereofinfluence ON gods.sphere_id = sphereofinfluence.id


-- CREATE TABLE heroes (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(50) NOT NULL,
-- 	gender_id INTEGER REFERENCES gender(id),
-- 	status_id INTEGER REFERENCES status(id)
-- );

-- -- -- класс первый запросик 
-- SELECT heroes.id, heroes.name,  gender.gender, status.status
-- FROM heroes
-- JOIN gender ON heroes.gender_id = gender.id
-- JOIN status ON heroes.status_id = status.id;


-- CREATE TABLE myths (
--     id SERIAL PRIMARY KEY,
-- 	hero_id INTEGER REFERENCES gender(id),
-- 	monster_id INTEGER REFERENCES status(id),
-- 	god_id INTEGER REFERENCES status(id),
-- 	synopsis VARCHAR(50) NOT NULL
-- );

-- -- -- класс первый запросик 
-- SELECT myths.id, heroes.name, monsters.monster, gods.name
-- FROM myths
-- JOIN heroes ON myths.hero_id = heroes.id
-- JOIN monsters ON myths.monster_id = monsters.id
-- JOIN gods ON myths.god_id = gods.id;


-- ALTER TABLE heroes
-- ALTER COLUMN name TYPE VARCHAR(16);


-- ALTER TABLE gods
-- ALTER COLUMN name TYPE VARCHAR(16);



-- CREATE TABLE myths (
--     id SERIAL PRIMARY KEY,
-- 	hero_id INTEGER REFERENCES heroes(id),
-- 	monster_id INTEGER REFERENCES monsters(id),
-- 	god_id INTEGER REFERENCES gods(id),
-- 	synopsis VARCHAR(500) NOT NULL
-- );


-- ALTER TABLE myths
-- ALTER COLUMN synopsis TYPE TEXT;

-- SELECT * FROM public.myths
-- ORDER BY id ASC;

-- лучший запрос в мире
-- SELECT myths.id, heroes.name, monsters.monster, 
-- CASE 
--    WHEN myths.god_id IS NULL THEN 'none'
--    ELSE gods.name
-- END AS god_name, myths.synopsis
-- FROM myths
-- LEFT JOIN heroes ON myths.hero_id = heroes.id
-- LEFT JOIN monsters ON myths.monster_id = monsters.id
-- LEFT JOIN gods ON myths.god_id = gods.id;

-- -- класс первый запросик 
-- SELECT heroes.id, heroes.name,  gender.gender, status.status
-- FROM heroes
-- JOIN gender ON heroes.gender_id = gender.id
-- JOIN status ON heroes.status_id = status.id;

-- SELECT myths.id, heroes.name, monsters.monster, 
-- CASE 
--    WHEN myths.god_id IS NULL THEN 'none'
--    ELSE gods.name
-- END AS god_name, myths.synopsis
-- FROM myths
-- LEFT JOIN heroes ON myths.hero_id = heroes.id
-- LEFT JOIN monsters ON myths.monster_id = monsters.id
-- LEFT JOIN gods ON myths.god_id = gods.id
-- WHERE heroes.name LIKE 'Odysseus';

-- SELECT gods.id, gods.name, typeofgod.type, gender.gender, sphereofinfluence.sphere
-- FROM gods
-- JOIN typeofgod ON gods.type_id = typeofgod.id
-- JOIN gender ON gods.gender_id = gender.id
-- JOIN sphereofinfluence ON gods.sphere_id = sphereofinfluence.id;

-- лучший запрос в мире

SELECT myths.id, 
CASE 
	WHEN myths.hero_id IS NULL THEN '-'
ELSE heroes.name
END AS hero_name,
	CASE 
WHEN myths.monster_id IS NULL THEN '-'
ELSE monsters.monster
END AS monster_name,
CASE 
WHEN myths.god_id IS NULL THEN '-'
ELSE gods.name
END AS god_name, myths.synopsis
FROM myths
LEFT JOIN heroes ON myths.hero_id = heroes.id
LEFT JOIN monsters ON myths.monster_id = monsters.id
LEFT JOIN gods ON myths.god_id = gods.id;
