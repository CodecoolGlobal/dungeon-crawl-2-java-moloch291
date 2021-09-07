package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Boat extends Item{

    public Boat(String name, Cell cell) {
        super(name, cell, ItemType.BOAT);
    }

    @Override
    public String getTileName() { return StringFactory.BOAT.message; }
}
