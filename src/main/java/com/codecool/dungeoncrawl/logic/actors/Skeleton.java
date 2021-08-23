package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
    }

    public void skeletonMove(Cell playerCell) {
        setXCoordinate(playerCell);
        setYCoordinate(playerCell);
    }

    public void setXCoordinate(Cell playerCell) {
        int playerX = playerCell.getX();
        if (getX() > playerX) {
            move(getX() -2, getY());
        } else if (getX() < playerX) {
            move(getX()  + 2, getY());
        }
    }

    public void setYCoordinate(Cell playerCell) {
        int playerY = playerCell.getY();
        if (getY() > playerY) {
            move(getX() , getY()-1);
        } else if (getY() < playerY) {
            move(getX() , getY()+1);
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
