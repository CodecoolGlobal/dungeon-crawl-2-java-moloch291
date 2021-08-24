package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    int damage;

    public Weapon(String name, Cell cell, ItemType itemType, int damage) {
        super(name, cell, itemType);
        this.damage = damage;
    }

    @Override
    public String getTileName() { return "weapon"; }
}
