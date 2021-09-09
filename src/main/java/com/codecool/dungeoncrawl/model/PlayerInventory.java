package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.Map;

public class PlayerInventory extends BaseModel{
    private int playerId;
    private int itemId;
    private int amount;
    private String itemName;
    Map<Item, Integer> inventory;

    public PlayerInventory(int playerId, int itemId, int amount, String itemName) {
        this.playerId = playerId;
        this.itemId = itemId;
        this.amount = amount;
        this.itemName = itemName;
    }



    public PlayerInventory(int playerId, Map<Item, Integer> inventory) {
        this.playerId = playerId;
        this.inventory = inventory;
    }

    public int getPlayerId() { return playerId; }

    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getItemId() { return itemId; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getAmount() { return amount; }

    public String getItemName() { return itemName; }

    public Map<Item, Integer> getInventory() { return inventory; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public void setInventory(Map<Item, Integer> inventory) { this.inventory = inventory; }
}
