package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Weapon extends Item {
    private final WeaponType weaponType;

    public Weapon(String name, Cell cell, ItemType itemType, WeaponType weaponType) {
        super(name, cell, itemType);
        this.weaponType = weaponType;
    }

    @Override
    public String getTileName() {
        String actualType = "sword";
        if (weaponType.equals(WeaponType.SWORD)) {
            actualType = "sword";
        } else if (weaponType.equals(WeaponType.AXE)) {
            actualType = "axe";
        } else if (weaponType.equals(WeaponType.PIKE)) {
            actualType = "pike";
        }
        return actualType;
    }
}
