package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    private Map<Item, Integer> inventory = new HashMap<>();

    public void setInventory(Item item, Integer quantity) {
        inventory.put(item, quantity);
    }

    public boolean isKeyPickedUp() {
        return false;
    }

    public String getTileName() {
        return "player";
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }
}
