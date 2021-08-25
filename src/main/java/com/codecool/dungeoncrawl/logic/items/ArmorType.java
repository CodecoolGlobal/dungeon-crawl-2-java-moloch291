package com.codecool.dungeoncrawl.logic.items;

public enum ArmorType {
    SHIELD("Shield", 5),
    HELMET("Helmet", 3),
    BREASTPLATE("Breastplate", 5),
    GREAVES("Greaves", 2),
    GAUNTLETS("Gauntlets", 2);

    public final String itemName;
    public final int defenseValue;

    ArmorType(String itemName, int defenseValue) {
        this.itemName = itemName;
        this.defenseValue = defenseValue;
    }
}
