package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;

public class Undead extends Actor{

    public Undead(Cell cell) {
        super(cell);
        super.setAttack(5);
        super.setDefense(3);
        super.setHealth(8);
    }

    @Override
    public void monsterMove(Cell playerCell) {

    }

    @Override
    public String getTileName() {
        return "undead";
    }
}
