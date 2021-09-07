package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

public class Alcohol extends Item{

    public Alcohol(Cell cell) {
        super(StringFactory.BEER.message, cell, ItemType.ALCOHOL);
    }

    @Override
    public String getTileName() {
        return StringFactory.BEER.message;
    }
}
