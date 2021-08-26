package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Alcohol extends Item{

    public Alcohol(String name, Cell cell, ItemType itemType) {
        super(name, cell, itemType);
    }

    @Override
    public String getTileName() { return StringFactory.BEER.message;}
}
