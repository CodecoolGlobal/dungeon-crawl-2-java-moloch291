package com.codecool.dungeoncrawl.logic.map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

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
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("armor", new Tile(5, 25));
        tileMap.put("weapon", new Tile(0, 29));
        tileMap.put("food", new Tile(15, 28));
        tileMap.put("potion", new Tile(26, 23));
        tileMap.put("redbrick", new Tile(6,13));
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("house", new Tile(0,20));
        tileMap.put("closed door", new Tile(22, 11));
        tileMap.put("open door", new Tile(21, 11));
        tileMap.put("orc", new Tile(28,6));
        tileMap.put("undead", new Tile(25,2));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
