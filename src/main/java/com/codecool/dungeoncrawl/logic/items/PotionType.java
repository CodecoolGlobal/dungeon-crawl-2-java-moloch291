package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.util.StringFactory;

public enum PotionType {
    HEALING_POTION(StringFactory.HEALING_POTION.message, "health", 20),
    STONE_SKIN_POTION(StringFactory.STONE_SKIN_POTION.message, "defense", 5),
    MIGHT_POTION(StringFactory.MIGHT_POTION.message, "attack", 5);

    public final String itemName;
    public final String affectedStat;
    public final int effectValue;

    PotionType(String itemName, String affectedStat, int effectValue) {
        this.itemName = itemName;
        this.affectedStat = affectedStat;
        this.effectValue = effectValue;
    }
}
