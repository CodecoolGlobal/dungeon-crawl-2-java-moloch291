package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Potion extends Item {
    private final PotionType potionType;

    public Potion(String name, Cell cell, PotionType potionType) {
        super(name, cell, ItemType.POTION);
        if (potionType != null) this.potionType = potionType;
        else throw new IllegalArgumentException("Potion type must not be null!");
    }

    @Override
    public String getTileName() {
        String actualType = "healing potion";
        if (potionType.equals(PotionType.HEALING_POTION)) {
            actualType = "healing potion";
        } else if (potionType.equals(PotionType.STONE_SKIN_POTION)) {
            actualType = "stone skin potion";
        } else if (potionType.equals(PotionType.MIGHT_POTION)) {
            actualType = "might potion";
        }
        return actualType;
    }

    public PotionType getPotionType() {
        return this.potionType;
    }
}
