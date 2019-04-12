--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE adventuredb;




--
-- Drop roles
--

DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md5245e743177217b5ed0144c94508f39a6';






--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Debian 11.2-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Debian 11.2-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: adventuredb; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE adventuredb WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE adventuredb OWNER TO postgres;

\connect adventuredb

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    category_name character varying(255)
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: event_participants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event_participants (
    event_id integer NOT NULL,
    users_id integer NOT NULL
);


ALTER TABLE public.event_participants OWNER TO postgres;

--
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    id integer NOT NULL,
    description text,
    end_date character varying(255),
    latitude double precision,
    location character varying(255),
    longitude double precision,
    start_date character varying(255),
    status_id integer,
    topic character varying(255),
    category_id integer,
    owner integer
);


ALTER TABLE public.events OWNER TO postgres;

--
-- Name: gallery; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gallery (
    id integer NOT NULL,
    is_deleted boolean,
    events_id integer
);


ALTER TABLE public.gallery OWNER TO postgres;

--
-- Name: gallery_image_urls; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gallery_image_urls (
    gallery_id integer NOT NULL,
    image_urls character varying(255)
);


ALTER TABLE public.gallery_image_urls OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    avatar character varying(255),
    email character varying(255),
    full_name character varying(255),
    location character varying(255),
    password character varying(255),
    phone integer,
    role character varying(255),
    status_id integer,
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, category_name) FROM stdin;
1	Skateboarding
2	Motocross
3	Mountain biking
4	Rock climbing
5	Parkour
6	Surfing
7	Kayaking
8	Bungee jumping
9	Sky diving
10	Snowboarding
11	Other
\.


--
-- Data for Name: event_participants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event_participants (event_id, users_id) FROM stdin;
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.events (id, description, end_date, latitude, location, longitude, start_date, status_id, topic, category_id, owner) FROM stdin;
23	Tour for everyone. It does not matter who you are and what kind of cyclist you are, we invite everyone who is interested in cycling. Throughout the road, you will be able to test your strengths and capabilities. We leave Lviv and move to Zaporozhye WITHOUT STOPPING!	2019-04-30T21:23:26.000Z	49.8419519999999991	Львів, Галицький район, Львівська міська рада, Львівська область, Україна	24.031592100000001	2019-04-12T21:23:26.000Z	1	Bicycle tour through Ukraine	3	17
24	There are other forms of sport parachuting that do not utilize aircraft as a launching platform. One such sport is paragliding, in which a pilot seated in a harness connected to a parachute canopy launches from a high place and glides, using air currents. In parasailing, a parachute is linked by a long line to a boat or land vehicle, and the forward motion of the vehicle tows the parachute and its wearer skyward. Finally, in BASE (an acronym for building, antenna, span, earth) jumping, the parachutist leaps from a very high point, such as a building, bridge, or cliff, rather than an airplane. It should be noted, however, that—owing to the relatively low altitudes from which the jump takes place—BASE jumping has a much higher risk level than other sport uses of a parachute; because of this and the possibility of injuring bystanders below, BASE jumping is usually illegal.	2019-05-21T21:35:59.000Z	49.144362049999998	Regierungsbezirk Stuttgart, Баден-Вюртемберг, Німеччина	9.54384325166325986	2019-05-20T21:35:59.000Z	1	Try yourself in the sky !!!	9	17
25	Skateboarding is an action sport which involves riding and performing tricks using a skateboard, as well as a recreational activity, an art form, an entertainment industry job, and a method of transportation.[1] Skateboarding has been shaped and influenced by many skateboarders throughout the years. A 2009 report found that the skateboarding market is worth an estimated $4.8 billion in annual revenue with 11.08 million active skateboarders in the world.[2] In 2016, it was announced that skateboarding will be represented at the 2020 Summer Olympics in Tokyo.	2020-05-01T21:43:43.000Z	35.6769880035	Aoyama dori, 6-chome, Мінато, Токіо, Регіон Канто, 107-8371, Японія	139.734561785497988	2020-04-01T05:30:00.000Z	1	$^&@# Go with us! $^&@#	1	18
26	Nestled in a quiet corner of Shropshire, this former Grand Prix circuit rose to global fame during the 70s and 80s as the annual host of the British Motocross Grand Prix.\nIn 1984, Hawkstone Park was immortalised after Nick Haskell captured THAT photo of Georges Jobe leaping over the head of Andre Malherbe. Quickly becoming the most iconic single image in the sport’s history, the resulting poster of the ‘Hawkstone bomb hole’ adorned bedroom walls across the world.	2019-04-26T21:53:18.000Z	51.4814957999999976	Windsor, Windsor and Maidenhead, South East, Англія, SL4 1TA, Велика Британія	-0.613318299999999983	2019-04-07T21:53:18.851Z	1	Travel to England on Moto.	2	18
21	The Dark Lord, Sauron, redeemed the One Ring to gain control over other rings in the hands of the leaders of the people, elves and dwarves. He will defeat the Battle of 3441 of the Second Age, and Isildur, the son of Elendil, will cut Ring with his finger and call it a family relic. Later, Isildura is killed by Orcs, and the Ring is lost in the Andujin River. Two thousand years later, the Ring falls into the hands of the Hobbit Deagol, and from him - to Smeagol, who kills his friend and takes away the Ring. Bilbo Begins kidnaps a ring and in old age passes it to his relative Frodo.\n\nFrodo, learning the history of the ring decides to destroy it where it was created, namely in Mordor.\n\nGo along with Frodo to Mordor and help destroy the ring of omnipotence in the volcano's volcano.\n \n	2019-05-03T09:31:03.000Z	47.9752753985797682	Sobetsu, Usu, Округ Ібурі, Префектура Хоккайдо, Хоккайдо, Японія	37.8753662109375	2019-04-05T09:31:03.654Z	1	Travel to Mordor	11	15
22	When the rider of the dragon Galbatorix wanted absolute power, he destroyed all the dragon riders along with the dragons.\n\nThe last riders managed to save one dragon egg by donating themselves. He teleported to the usual boy Eragon, whose life has radically changed after this discovery. After hatching the dragon Eragon gives him the name of Sapphire. The young rider begins training for becoming a rider with the mission of liberating the world from the tyrant Galbatorix and uniting all races with each other.\n\nJoin the exciting journey in which there will be dragons, elves, orcs, gnomes, dwarves and many interesting adventures. Going on a journey with Eragon you will not have time for boredom.	2020-01-02T17:04:43.000Z	10.2116702000000004	Ефіо́пія	38.6521203	2019-04-05T16:04:43.172Z	1	Save Alaghesia	9	16
27	Gandalf with Pippin goes to Gondor to warn the inhabitants of Minas Tirith about the imminent offensive of the troops of Mordor. He finds Denethor, the governor of Gondor, in mourning for Boromir. Theoden leads the Rohan army to aid Gondor, Merry is secretly sent with the army, violating the king’s ban. Aragorn, along with Legolas and Gimli, passes along a secret path and calls for help the Army of the Dead — the ghosts of people who once broke the oath to Aragorn's ancestor Isildur and who have not known peace since. The orcs from Mordor captured Osgiliath and proceeded to storm Minas Tirith.	2019-05-30T22:20:14.000Z	-18.0342450999999997	Municipio El Torno, Provincia Andrés Ibáñez, SCZ, Болі́вія	-63.4500611015868969	2019-05-23T22:20:14.000Z	1	Warn residents of Minas Tirith about of the troops of Mordor	7	15
28	In the future, a fierce struggle against Sauron will take place. But long before this was the first preparations for the war, which were associated with the rings of omnipotence. Find Bilbo Balboa, with whom you can find this ring.	2019-05-30T22:27:31.000Z	39.3763806999999986	Туркменістан	59.3924609000000032	2019-04-30T22:27:31.000Z	1	Find Bilbo Balboa	11	15
\.


--
-- Data for Name: gallery; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gallery (id, is_deleted, events_id) FROM stdin;
1	\N	21
2	\N	22
3	\N	23
4	\N	24
5	\N	25
6	\N	26
7	\N	27
8	\N	28
\.


--
-- Data for Name: gallery_image_urls; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gallery_image_urls (gallery_id, image_urls) FROM stdin;
1	https://c.wallhere.com/photos/9d/52/1920x1080_px_fantasy_Art_Middle_earth_Shadow_Of_Mordor_mordor_Shadow_Of_Mordor_The_Lord_Of_The_Rings_video_games-713821.jpg!d
2	http://vignette4.wikia.nocookie.net/inheritance/images/f/f0/Eragon-alagaesia-map-2.jpg/revision/latest?cb=20120115112240&path-prefix=ru
3	https://www.reidcycles.com.au/media/product/e83/women-s-osprey-road-bike-e5f.jpg
4	https://i.pinimg.com/originals/4c/9a/11/4c9a11a15a887703884a8cf82c41ed45.jpg
5	https://images.vexels.com/media/users/3/138238/isolated/preview/a74066f99227b4769ef934ca8831c396-skateboarding-jump-rolling-by-vexels.png
6	https://previews.123rf.com/images/alexeyko/alexeyko1612/alexeyko161200004/69008211-motocross-enduro-background-silhouette-of-a-man-who-rides-on-a-motorbike-vector-illustration-.jpg
7	https://www.nzfilm.co.nz/sites/default/files/styles/ratio_16_x_9__small_/public/2017-10/locations_gallery/media/2394/the-lord-of-the-ring-the-return-of-the-kings-3.jpg?itok=b0D1tuKi
8	https://www.digitaltrends.com/wp-content/uploads/2012/08/thehobbit.jpg
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, avatar, email, full_name, location, password, phone, role, status_id, username) FROM stdin;
17	https://ji-network.org/wp-content/uploads/2013/09/NONAKA_logo_1.jpg	nonaka@scrum.com	Ikujiro Nonaka	\N	$2a$10$mGtUz4OdCKKIAaL2vS70pen9CzAFv4qvvabBHvzXmRXIlqOz6hdtC	0	\N	2	nonaka
18	https://userscontent2.emaze.com/images/39582c1b-7436-4ebe-abdd-e0992c82354d/4de77f622b7481a9a1429f7c41ac294b.jpg	hirotaka@scrum.com	Hirotaka Takeuchi	\N	$2a$10$5O2ZL8dA8LlDEEj54Vu/zOkhDqEnxOJPFLOU4SbRkput/.6a2W09q	0	\N	2	hirotaka
16	https://images-na.ssl-images-amazon.com/images/I/814ea-1zl1L._SL1500_.jpg	eragon@gmail.com	Christopher Paolini	\N	$2a$10$0NlooAaWSB04ivSmJq7tc.joiuFurQEuV65G5p66ezA8psfo4ywm2	0	\N	2	eragon
15	https://target.scene7.com/is/image/Target/GUEST_907f740a-7888-4cb0-9081-80ecada58e3a?wid=488&hei=488&fmt=pjpeg	lord.of.the.rings@gmail.com	John R.R. Tolkien	\N	$2a$10$XQNJhD0E1Tm2dNd/bfTZOOczf3GldjfOCQPjONJ2SLzyM5aGvtCMm	0	\N	1	lord.of.the.rings
12	https://upload.wikimedia.org/wikipedia/commons/4/4e/Gmail_Icon.png	email@gmail.com	Ioann	\N	$2a$10$/At5sB8sDJsXUMZ/Te.oYeYN7pWOBFPUTh7.i24hc3AYy98.hYB9u	0	\N	2	\N
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 28, true);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: event_participants event_participants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT event_participants_pkey PRIMARY KEY (event_id, users_id);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: gallery gallery_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gallery
    ADD CONSTRAINT gallery_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: event_participants fk2x391urx4up03f4jp2y9mdt5x; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT fk2x391urx4up03f4jp2y9mdt5x FOREIGN KEY (event_id) REFERENCES public.events(id);


--
-- Name: events fk3m9uf5usf8mhej2bku5l63cjh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk3m9uf5usf8mhej2bku5l63cjh FOREIGN KEY (owner) REFERENCES public.users(id);


--
-- Name: gallery fk85v1m7ywvut2smrwlxsel2ev9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gallery
    ADD CONSTRAINT fk85v1m7ywvut2smrwlxsel2ev9 FOREIGN KEY (events_id) REFERENCES public.events(id);


--
-- Name: event_participants fk9ybj6yy5tk6k56b3h6pwbmju; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event_participants
    ADD CONSTRAINT fk9ybj6yy5tk6k56b3h6pwbmju FOREIGN KEY (users_id) REFERENCES public.users(id);


--
-- Name: gallery_image_urls fklupi7iva4i1lh2yofx1mt1u6r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gallery_image_urls
    ADD CONSTRAINT fklupi7iva4i1lh2yofx1mt1u6r FOREIGN KEY (gallery_id) REFERENCES public.gallery(id);


--
-- Name: events fkmr4w99o4qicmmrmxnwojkxthm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkmr4w99o4qicmmrmxnwojkxthm FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Debian 11.2-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

