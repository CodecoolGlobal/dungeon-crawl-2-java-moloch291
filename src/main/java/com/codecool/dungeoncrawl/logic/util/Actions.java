package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.Y;

public class Actions {

    Booleans booleans = new Booleans();

    public void pickUpItem(GameMap map) {
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        if (booleans.isItemOnPlayerPosition(playerX, playerY, map)) {
            Item item = map.getCell(playerX, playerY).getItem();
            map.getPlayer().setInventory(item, 1);
            map.getCell(playerX, playerY).setItem(null);
        }
    }



    public Actions() {
    }
}
