package com.codecool.dungeoncrawl.logic.items;

public enum WeaponType {
    SWORD("Sword", 4),
    AXE("Axe", 3),
    PIKE("Pike", 5);

    public final String itemName;
    public final int attackValue;

    WeaponType(String itemName, int attackValue) {
        this.itemName = itemName;
        this.attackValue = attackValue;
    }
}
