package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.MapAndParts.Cell;

public class Potion extends Item {

    public Potion(String name, Cell cell, ItemType itemType) {
        super(name, cell, itemType);
    }

    @Override
    public String getTileName() { return "potion"; }
}
