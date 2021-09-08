package com.codecool.dungeoncrawl.model;

public class PlayerInventory extends BaseModel{
    private int playerId;
    private int itemId;
    private int amount;
    private String itemName;

    public PlayerInventory(int playerId, int itemId, int amount, String itemName) {
        this.playerId = playerId;
        this.itemId = itemId;
        this.amount = amount;
        this.itemName = itemName;
    }

    public int getPlayerId() { return playerId; }

    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getItemId() { return itemId; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getAmount() { return amount; }
}
