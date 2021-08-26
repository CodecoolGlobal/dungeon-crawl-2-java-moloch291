package com.codecool.dungeoncrawl.logic.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public final static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(1, 3));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("floor second", new Tile(19, 1));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("shield", new Tile(5, 25));
        tileMap.put("sword", new Tile(0, 29));
        tileMap.put("bread", new Tile(15, 28));
        tileMap.put("healing potion", new Tile(16, 25));
        tileMap.put("redbrick", new Tile(6,13));
        tileMap.put("redbrick alter", new Tile(7,15));
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("river", new Tile(12, 5));
        tileMap.put("house", new Tile(0,20));
        tileMap.put("house alter", new Tile(0,19));
        tileMap.put("house alter 2", new Tile(1,19));
        tileMap.put("house alter 3", new Tile(1,20));
        tileMap.put("closed door", new Tile(22, 11));
        tileMap.put("open door", new Tile(21, 11));
        tileMap.put("orc", new Tile(28,6));
        tileMap.put("undead", new Tile(25,2));
        tileMap.put("ramp start", new Tile(10, 15));
        tileMap.put("ramp middle", new Tile(11, 15));
        tileMap.put("ramp end", new Tile(12, 15));
        tileMap.put("boat", new Tile(11, 19));
        tileMap.put("torch", new Tile(3, 15));
        tileMap.put("ladder", new Tile(21, 0));
        tileMap.put("ladder upper", new Tile(21, 1));
        tileMap.put("lake house", new Tile(8, 20));
        tileMap.put("lake house alter", new Tile(7, 20));
        tileMap.put("fake door",new Tile(21, 11));
        tileMap.put("water item", new Tile(16, 30));
        tileMap.put("cheese", new Tile(18, 28));
        tileMap.put("apple", new Tile(15, 29));
        tileMap.put("fish", new Tile(17, 29));
        tileMap.put("beer", new Tile(15, 31));
        tileMap.put("helmet", new Tile(1, 22));
        tileMap.put("breastplate", new Tile(0, 23));
        tileMap.put("greaves", new Tile(8, 23));
        tileMap.put("gauntlets", new Tile(9, 23));
        tileMap.put("bridge key", new Tile(17, 23));
        tileMap.put("lock pick", new Tile(18, 23));
        tileMap.put("stone skin potion", new Tile(17, 25));
        tileMap.put("might potion", new Tile(18, 25));
        tileMap.put("axe", new Tile(10, 30));
        tileMap.put("pike", new Tile(4, 25));
        tileMap.put("tree", new Tile(4, 2));
        tileMap.put("weed", new Tile(0, 2));
        tileMap.put("moss", new Tile(5, 0));
        tileMap.put("road sign", new Tile(0, 7));
        tileMap.put("campfire", new Tile(14, 10));
        tileMap.put("cauldron", new Tile(5, 14));
        tileMap.put("rocks", new Tile(5, 2));
        tileMap.put("tent", new Tile(6, 20));
        tileMap.put("kraken", new Tile(25,8));
        tileMap.put("ghost", new Tile(27,6));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
