package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    @Override
    public void monsterMove(Cell playerCell) {

    }


    private final Map<Item, Integer> inventory = new HashMap<>();

    public boolean hasKey() {
        return true;
    }

    public String getTileName() {
        return "player";
    }

}
