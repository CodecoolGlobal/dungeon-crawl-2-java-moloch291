package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public void monsterMove(Cell playerCell) {
        setXCoordinate(playerCell);
        setYCoordinate(playerCell);
    }

    public void setXCoordinate(Cell playerCell) {
        int playerX = playerCell.getX();
        if (getX() > playerX) {
            move( -1, 0);
        } else if (getX() < playerX) {
            move( 1, 0);
        }
    }

    public void setYCoordinate(Cell playerCell) {
        int playerY = playerCell.getY();
        if (getY() > playerY) {
            move(0 , -1);
        } else if (getY() < playerY) {
            move(0 , 1);
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
