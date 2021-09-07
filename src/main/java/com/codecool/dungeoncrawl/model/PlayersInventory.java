package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.io.Serializable;
import java.util.Map;

public class PlayersInventory implements Serializable {
    Map<Item, Integer> playersInventory;

    public PlayersInventory(GameMap map) {
        this.playersInventory = map.getPlayer().getInventory();
    }

    public void printInventory() {
        for (Item item: playersInventory.keySet()) {
            System.out.println(item.getName());
        }
    }
}
