package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Weapon extends Item {

    private final int damage;

    public Weapon(String name, Cell cell, ItemType itemType, int damage) {
        super(name, cell, itemType);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getTileName() { return "weapon"; }
}
