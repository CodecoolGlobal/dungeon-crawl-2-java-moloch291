package com.codecool.dungeoncrawl.logic.items;

public enum FoodType {
    BREAD("Bread", 5),
    WATER("Water", 5),
    CHEESE("Cheese", 5),
    APPLE("Apple", 5),
    FISH("Fish", 5);

    public final String itemName;
    public final int replenishHealth;

    FoodType(String itemName, int replenishHealth) {
        this.itemName = itemName;
        this.replenishHealth = replenishHealth;
    }
}
