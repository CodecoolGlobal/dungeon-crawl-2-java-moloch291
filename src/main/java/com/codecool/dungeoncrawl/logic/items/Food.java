package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Food extends Item {
    private final FoodType foodType;

    public Food(String name, Cell cell, ItemType itemType, FoodType foodType) {
        super(name, cell, itemType);
        this.foodType = foodType;
    }

    @Override
    public String getTileName() {
        String actualType = "bread";
        if (foodType.equals(FoodType.BREAD)) {
            actualType = "bread";
        } else if (foodType.equals(FoodType.CHEESE)) {
            actualType = "cheese";
        } else if (foodType.equals(FoodType.WATER)) {
            actualType = "water item";
        } else if (foodType.equals(FoodType.APPLE)) {
            actualType = "apple";
        } else if (foodType.equals(FoodType.FISH)) {
            actualType = "fish";
        }
        return actualType;
    }
}
