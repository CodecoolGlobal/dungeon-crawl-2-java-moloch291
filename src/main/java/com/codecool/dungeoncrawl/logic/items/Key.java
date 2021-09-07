package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Key extends Item {
    private final KeyType keyType;

    public Key(String name, Cell cell, KeyType keyType) {
        super(name, cell, ItemType.KEY);
        if (keyType != null) this.keyType = keyType;
        else throw new IllegalArgumentException("Key type must not be null!");
    }

    @Override
    public String getTileName() {
        String actualType = StringFactory.KEY.message;
        if (keyType.equals(KeyType.BRIDGE_KEY)) {
            actualType = "bridge key";
        } else if (keyType.equals(KeyType.LOCK_PICK)) {
            actualType = "lock pick";
        }
        return actualType;
    }
}
