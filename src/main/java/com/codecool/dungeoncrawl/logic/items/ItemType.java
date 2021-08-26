package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.util.StringFactory;

public enum ItemType {
    KEY(StringFactory.KEY.message),
    WEAPON(StringFactory.WEAPON.message),
    ARMOR(StringFactory.ARMOR.message),
    FOOD(StringFactory.FOOD.message),
    POTION(StringFactory.POTION.message),
    ALCOHOL(StringFactory.BEER.message),
    BOAT(StringFactory.BOAT.message);

    public final String name;

    ItemType(String name) {
        this.name = name;
    }
}
