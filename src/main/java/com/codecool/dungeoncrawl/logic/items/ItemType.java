package com.codecool.dungeoncrawl.logic.items;

public enum ItemType {
    KEY("key"),
    WEAPON("weapon"),
    ARMOR("armor"),
    FOOD("food"),
    POTION("potion");

    public final String name;

    ItemType(String name) {
        this.name = name;
    }
}
