package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.Drawable;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

import java.io.Serializable;

public abstract class Item implements Drawable, Serializable {
    private final String name;
    private Cell cell;
    private ItemType itemType;



    public Item(String name, Cell cell, ItemType itemType) {
        if (name == null || cell == null || itemType == null)
            throw new IllegalArgumentException(StringFactory.IllegalArgumentError.message);
        this.name = name;
        this.cell = cell;
        this.cell.setItem(this);
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
