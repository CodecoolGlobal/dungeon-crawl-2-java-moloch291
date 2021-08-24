package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.logic.GameMap;

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

    private void decrementItem(GameMap map, String itemName) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        int count;
        Item oldItem;
        for (Item item : playerInventory.keySet()) {
            if (item.getName().equals(itemName)) {
                oldItem = item;
                count = playerInventory.get(item);
                if (count > 1) {
                    map.getPlayer().removeFromInventory(item);
                    map.getPlayer().setInventory(oldItem, count - 1);
                } else {
                    map.getPlayer().removeFromInventory(item);
                }
            }
        }
    }
}
