package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;

public class Actions {

    public void pickUpItem(GameMap map) {
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        if (map.getCell(playerX, playerY).getItem() != null) {
            Item item = map.getCell(playerX, playerY).getItem();
            map.getPlayer().setInventory(item, 1);
            map.getCell(playerX, playerY).setItem(null);
        }
    }

    public Actions() {
    }
}
