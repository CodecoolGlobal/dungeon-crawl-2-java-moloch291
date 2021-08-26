package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.util.StringFactory;

public enum FoodType {
    BREAD(StringFactory.BREAD.message, 5),
    WATER(StringFactory.WATER.message, 5),
    CHEESE(StringFactory.CHEESE.message, 5),
    APPLE(StringFactory.APPLE.message, 5),
    FISH(StringFactory.FISH.message, 5);

    public final String itemName;
    public final int replenishHealth;

    FoodType(String itemName, int replenishHealth) {
        this.itemName = itemName;
        this.replenishHealth = replenishHealth;
    }
}
