package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Armor extends Item{
    private final ArmorType armorType;

    public Armor(String name, Cell cell, ItemType itemType, ArmorType armorType) {
        super(name, cell, itemType);
        this.armorType = armorType;
    }


    @Override
    public String getTileName() {
        String actualType = "shield";
        if (armorType.equals(ArmorType.SHIELD)) {
            actualType = "shield";
        } else if (armorType.equals(ArmorType.HELMET)) {
            actualType = "helmet";
        } else if (armorType.equals(ArmorType.BREASTPLATE)) {
            actualType = "breastplate";
        } else if (armorType.equals(ArmorType.GREAVES)) {
            actualType = "greaves";
        } else if (armorType.equals(ArmorType.GAUNTLETS)) {
            actualType = "gauntlets";
        }
        return actualType;
    }
}
