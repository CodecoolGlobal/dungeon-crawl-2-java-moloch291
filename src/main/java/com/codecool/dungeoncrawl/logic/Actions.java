package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.Y;

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


    public void quitGame(KeyEvent keyEvent, Label quitLabel) {
        quitLabel.setText("Are you sure you want to quit? Y/N");
        if (keyEvent.getCode() == Y) {
            System.exit(0);
        } else if (keyEvent.getCode() == N) {
            quitLabel.setText("");
        }
    }


    public Actions() {
    }
}
