package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Armor extends Item{
    int defense;

    public Armor(String name, Cell cell, ItemType itemType, int defense) {
        super(name, cell, itemType);
        this.defense = defense;
    }

    @Override
    public String getTileName() { return "armor"; }
}
