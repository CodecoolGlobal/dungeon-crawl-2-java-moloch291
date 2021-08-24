package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Undead extends Actor{

    public Undead(Cell cell) {
        super(cell);
    }

    @Override
    public void monsterMove(Cell playerCell) {

    }

    @Override
    public String getTileName() {
        return "undead";
    }
}
