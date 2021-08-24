package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    private final Map<Item, Integer> inventory = new HashMap<>();

    public void setInventory(Item item, Integer quantity) {
        inventory.put(item, quantity);
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

    public String getTileName() {
        return "player";
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }
}
