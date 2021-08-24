package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.MapAndParts.Cell;

public class Undead extends Actor{

    public Undead(Cell cell) {
        super(cell);
        super.setAttack(3);
        super.setDefense(3);
        super.setHealth(4);
    }

    @Override
    public void monsterMove(Cell playerCell) {

    }

    @Override
    public String getTileName() {
        return "undead";
    }
}
