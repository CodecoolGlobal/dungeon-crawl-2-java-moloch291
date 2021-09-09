DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    save_name text NOT NULL,
    current_map text NOT NULL,
    saved_at text NOT NULL,
    player_id integer NOT NULL
);


DROP TABLE IF EXISTS public.player CASCADE ;
CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    hp integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    drunk boolean NOT NULL
);


DROP TABLE IF EXISTS public.inventory_handler CASCADE ;
CREATE TABLE public.inventory_handler (
    id serial NOT NULL PRIMARY KEY,
    item_name text NOT NULL
);


ALTER TABLE IF EXISTS ONLY public.players_inventory DROP CONSTRAINT IF EXISTS fk_player_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.players_inventory DROP CONSTRAINT IF EXISTS fk_item_id CASCADE;
DROP TABLE IF EXISTS public.players_inventory CASCADE;
CREATE TABLE public.players_inventory (
     id serial NOT NULL PRIMARY KEY,
     player_id integer NOT NULL,
     item_id integer NOT NULL,
     amount integer NOT NULL
);


ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.players_inventory
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.players_inventory
    ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES public.inventory_handler(id);


INSERT INTO inventory_handler VALUES (1, 'Beer');
INSERT INTO inventory_handler VALUES (2, 'Shield');
INSERT INTO inventory_handler VALUES (3, 'Helmet');
INSERT INTO inventory_handler VALUES (4, 'Breastplate');
INSERT INTO inventory_handler VALUES (5, 'Greaves');
INSERT INTO inventory_handler VALUES (6, 'Gauntlets');
INSERT INTO inventory_handler VALUES (7, 'Boat');
INSERT INTO inventory_handler VALUES (8, 'Bread');
INSERT INTO inventory_handler VALUES (9, 'Water');
INSERT INTO inventory_handler VALUES (10, 'Cheese');
INSERT INTO inventory_handler VALUES (11, 'Apple');
INSERT INTO inventory_handler VALUES (12, 'Fish');
INSERT INTO inventory_handler VALUES (13, 'Healing potion');
INSERT INTO inventory_handler VALUES (14, 'Stone skin potion');
INSERT INTO inventory_handler VALUES (15, 'Might potion');
INSERT INTO inventory_handler VALUES (16, 'Sword');
INSERT INTO inventory_handler VALUES (17, 'Axe');
INSERT INTO inventory_handler VALUES (18, 'Pike');
INSERT INTO inventory_handler VALUES (19, 'Key');
INSERT INTO inventory_handler VALUES (20, 'Bridge key');
INSERT INTO inventory_handler VALUES (21, 'Lock pick');