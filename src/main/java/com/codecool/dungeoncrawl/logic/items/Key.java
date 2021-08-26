package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Key extends Item {
    private final KeyType keyType;

    public Key(String name, Cell cell, ItemType itemType, KeyType keyType) {
        super(name, cell, itemType);
        this.keyType = keyType;
    }

    @Override
    public String getTileName() {
        String actualType = "key";
        if (keyType.equals(KeyType.BRIDGE_KEY)) {
            actualType = "bridge key";
        } else if (keyType.equals(KeyType.LOCK_PICK)) {
            actualType = "lock pick";
        }
        return actualType;
    }
}
