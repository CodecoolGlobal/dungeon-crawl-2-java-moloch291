package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Boat extends Item{

    public Boat(String name, Cell cell, ItemType itemType) {
        super(name, cell, itemType);
    }

    @Override
    public String getTileName() { return "boat"; }
}
