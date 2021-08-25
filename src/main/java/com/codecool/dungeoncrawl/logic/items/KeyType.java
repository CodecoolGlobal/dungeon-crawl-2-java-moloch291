package com.codecool.dungeoncrawl.logic.items;

public enum KeyType {
    DOOR_KEY("Door key"),
    BRIDGE_KEY("Bridge key"),
    LOCK_PICK("Lock pick");

    public final String itemName;

    KeyType(String itemName) {
        this.itemName = itemName;
    }
}
