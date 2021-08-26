package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {

    private final Map<Item, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item, Integer quantity) {
        inventory.put(item, quantity);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public boolean hasKey() {
        for (Item item: inventory.keySet()) {
            if (item.getItemType().equals(ItemType.KEY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void monsterMove(Cell playerCell) {
    }
}
