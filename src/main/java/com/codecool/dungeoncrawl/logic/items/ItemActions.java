package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.util.Map;

public class ItemActions {

    private boolean searchForItem(GameMap map, String nameCheck) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        for (Item item : playerInventory.keySet()) {
            return item.getName().equals(nameCheck);
        }
        return false;
    }

    public void consumeFood(GameMap map, String itemName) {
        int playerHealth = map.getPlayer().getHealth();
        if (searchForItem(map, itemName)) {
            map.getPlayer().setHealth(playerHealth + 5);
        }
        decrementItem(map, itemName);
    }

    public void consumePotion(GameMap map, String itemName) {
        int playerHealth = map.getPlayer().getHealth();
        if (searchForItem(map, itemName)) {
            map.getPlayer().setHealth(playerHealth + 20);
        }
        decrementItem(map, itemName);
    }

    private void decrementItem(GameMap map, String itemName) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        int count = 0;
        Item itemFromInventory = null;
        for (Item item : playerInventory.keySet()) {
            if (item.getName().equals(itemName)) {
                itemFromInventory = item;
                count = playerInventory.get(item);
            }
        }
        handleInventory(map, playerInventory, count, itemFromInventory);
    }

    private void handleInventory(GameMap map, Map<Item, Integer> playerInventory, int count, Item itemFromInventory) {
        if (count > 1) {
            map.getPlayer().addToInventory(itemFromInventory, playerInventory.get(itemFromInventory) - 1);
        } else {
            map.getPlayer().removeFromInventory(itemFromInventory);
        }
    }
}
