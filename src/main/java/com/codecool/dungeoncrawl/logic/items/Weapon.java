package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Weapon extends Item {
    private final WeaponType weaponType;

    public Weapon(String name, Cell cell, WeaponType weaponType) {
        super(name, cell, ItemType.WEAPON);
        this.weaponType = weaponType;
    }

    @Override
    public String getTileName() {
        String actualType = StringFactory.SWORD.message;
        if (weaponType.equals(WeaponType.SWORD)) {
            actualType = StringFactory.SWORD.message;
        } else if (weaponType.equals(WeaponType.AXE)) {
            actualType = StringFactory.AXE.message;
        } else if (weaponType.equals(WeaponType.PIKE)) {
            actualType = StringFactory.PIKE.message;
        }
        return actualType;
    }
}
