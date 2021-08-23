package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    private final Map<Item, Integer> inventory = new HashMap<>();

    public String getTileName() {
        return "player";
    }

    public void openDoor() {
        File mapTxt = new File("/map.txt");
    }
}
