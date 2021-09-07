package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Food extends Item {

    private final FoodType foodType;

    public Food(String name, Cell cell, FoodType foodType) {
        super(name, cell, ItemType.FOOD);
        if (foodType != null) this.foodType = foodType;
        else throw new IllegalArgumentException(StringFactory.IllegalArgumentError.message);
    }

    @Override
    public String getTileName() {
        String actualType = StringFactory.BREAD.message;
        if (foodType.equals(FoodType.BREAD)) {
            actualType = StringFactory.BREAD.message;
        } else if (foodType.equals(FoodType.CHEESE)) {
            actualType = StringFactory.CHEESE.message;
        } else if (foodType.equals(FoodType.WATER)) {
            actualType = StringFactory.WATER_ITEM.message;
        } else if (foodType.equals(FoodType.APPLE)) {
            actualType = StringFactory.APPLE.message;
        } else if (foodType.equals(FoodType.FISH)) {
            actualType = StringFactory.FISH.message;
        }
        return actualType;
    }
}
