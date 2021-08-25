package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Armor extends Item{
    private final int defense;

    public Armor(String name, Cell cell, ItemType itemType, int defense) {
        super(name, cell, itemType);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public String getTileName() { return "armor"; }
}
