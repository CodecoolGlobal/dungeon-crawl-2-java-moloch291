package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Armor extends Item {
    private final ArmorType armorType;

    public Armor(String name, Cell cell, ArmorType armorType) {
        super(name, cell, ItemType.ARMOR);
        if (armorType != null) this.armorType = armorType;
        else throw new IllegalArgumentException("Armor type must not be null!");
    }


    @Override
    public String getTileName() {
        String actualType = StringFactory.SHIELD.message;
        if (armorType.equals(ArmorType.SHIELD)) {
            actualType = StringFactory.SHIELD.message;
        } else if (armorType.equals(ArmorType.HELMET)) {
            actualType = StringFactory.HELMET.message;
        } else if (armorType.equals(ArmorType.BREASTPLATE)) {
            actualType = StringFactory.BREASTPLATE.message;
        } else if (armorType.equals(ArmorType.GREAVES)) {
            actualType = StringFactory.GREAVES.message;
        } else if (armorType.equals(ArmorType.GAUNTLETS)) {
            actualType = StringFactory.GAUNTLETS.message;
        }
        return actualType;
    }
}
