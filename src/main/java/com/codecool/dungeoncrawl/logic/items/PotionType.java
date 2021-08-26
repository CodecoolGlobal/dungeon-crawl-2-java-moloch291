package com.codecool.dungeoncrawl.logic.items;

public enum PotionType {
    HEALING_POTION("Healing potion", "health", 20),
    STONE_SKIN_POTION("Stone skin potion", "defense", 5),
    MIGHT_POTION("Potion of might", "attack", 5);

    public final String itemName;
    public final String affectedStat;
    public final int effectValue;

    PotionType(String itemName, String affectedStat, int effectValue) {
        this.itemName = itemName;
        this.affectedStat = affectedStat;
        this.effectValue = effectValue;
    }
}
