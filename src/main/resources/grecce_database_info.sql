-- SELECT * FROM public.gods
-- ORDER BY id ASC 
	
-- INSERT INTO gods(name, gender_id, sphere_id, type_id) 
--  VALUES ('Zeus', 1, 5, 1),
--  ('Athena', 2, 3, 1),
-- 	('Apollo', 1, 8, 1),
-- ('Hera', 2, 6, 1),
-- ('Artemis', 2, 7, 3),
-- ('Aries', 1, 2, 1),
-- ('Aphrodite', 2, 1, 1);

-- SELECT * FROM public.gods
-- ORDER BY id ASC 

-- INSERT INTO sphereofinfluence(sphere) 
--  VALUES ('flood'),
-- ('death');

-- SELECT * FROM public.sphereofinfluence
-- ORDER BY id ASC 

-- INSERT INTO gods(name, gender_id, sphere_id, type_id) 
--  VALUES ('Poseidon', 1, 9, 2),
--  ('Hades', 1, 10, 4);

-- SELECT * FROM public.gods
-- ORDER BY id ASC 


-- INSERT INTO heroes(name, gender_id, status_id) 
--  VALUES ('Odysseus', 1, 1),
--  ('Perseus', 1, 4),
-- 	('Achilles', 1, 1),
-- ('Hercules', 1, 5),
-- ('Theseus', 1, 5),
-- ('Hector', 1, 1),
-- ('Patroclus', 1, 3);

-- SELECT * FROM public.myths
-- ORDER BY id ASC 


-- INSERT INTO myths(hero_id, monster_id, god_id, synopsis) 
--  VALUES (1, 1, 8, 'In the myth of Odysseus, he encounters the Cyclops Polyphemus who traps him and his men in his cave, but Odysseus cleverly blinds the Cyclops to escape, angering Poseidon who then torments Odysseus on his journey home to Ithaca.')


-- SELECT * FROM public.myths
-- ORDER BY id ASC

-- INSERT INTO myths(hero_id, monster_id, god_id, synopsis) 
--  VALUES (1, 1, 8, 'In the myth of Odysseus, he encoun the Cyclops Polyphemus who traps him and his men in his cave, but Odysseus cleverly blinds the Cyclops to escape, angering Poseidon who then torments Odysseus on his journey home to Ithaca.')

-- INSERT INTO myths(hero_id, monster_id, synopsis) 
--  VALUES (4, 6, 'In the myth of Hercules and the Hydra, Hercules is tasked with killing the nine-headed serpent, the Hydra, as one of his Twelve Labors. However, for every head Hercules cuts off, two more grow in its place until he successfully defeats the Hydra with the help of his nephew Iolaus who cauterizes the necks to prevent regrowth.'),
-- (2, 5, 'In the myth of Perseus and Medusa, Perseus embarks on a quest to defeat the Gorgon Medusa, whose gaze turns people into stone. With the help of Athena and Hermes, Perseus uses a reflective shield to avoid looking directly at Medusa and succeeds in beheading her, using her head as a weapon in subsequent battles.')

-- SELECT * FROM public.myths
-- ORDER BY id ASC

-- INSERT INTO myths(hero_id, monster_id, synopsis) 
--  VALUES (5, 4, 'In the myth of Theseus and the Minotaur, Theseus volunteers to enter the Labyrinth of Crete and slay the half-man, half-bull creature known as the Minotaur. With the help of Princess Ariadne, who gives him a ball of string to help him find his way out of the maze, Theseus successfully defeats the Minotaur and escapes the Labyrinth.'),
-- 	(1, 3, 'In the myth of the Odyssey, Odysseus encounters the Sirens, enchanting creatures whose beautiful songs lure sailors to their deaths. To resist their tempting melodies, Odysseus has his men plug their ears with beeswax and tie himself to the mast of the ship, allowing him to hear the Sirens song without falling prey to its deadly allure.')

SELECT * FROM public.myths
ORDER BY id ASC
