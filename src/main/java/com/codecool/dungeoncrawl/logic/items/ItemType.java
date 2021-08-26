package com.codecool.dungeoncrawl.logic.items;

public enum ItemType {
    KEY("key"),
    WEAPON("weapon"),
    ARMOR("armor"),
    FOOD("food"),
    POTION("potion"),
    BOAT("boat"),
    ALCOHOL("beer");

    public final String name;

    ItemType(String name) {
        this.name = name;
    }
}
