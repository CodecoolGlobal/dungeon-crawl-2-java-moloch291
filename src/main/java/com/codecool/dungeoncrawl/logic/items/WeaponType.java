package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.util.StringFactory;

public enum WeaponType {
    SWORD(StringFactory.SWORD_CAP.message, 4),
    AXE(StringFactory.AXE_CAP.message, 3),
    PIKE(StringFactory.PIKE_CAP.message, 5);

    public final String itemName;
    public final int attackValue;

    WeaponType(String itemName, int attackValue) {
        this.itemName = itemName;
        this.attackValue = attackValue;
    }
}
