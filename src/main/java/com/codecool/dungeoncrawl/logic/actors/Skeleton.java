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
        int skeletonX = getCell().getX();
        int skeletonY = getCell().getY();
        int playerX = playerCell.getX();
        if (skeletonX > playerX) {
            move(skeletonX-2, skeletonY);
        } else if (skeletonX < playerX) {
            move(skeletonX + 2, skeletonY);
        }
    }

    public void setYCoordinate(Cell playerCell) {
        int skeletonX = getCell().getX();
        int skeletonY = getCell().getY();
        int playerY = playerCell.getY();
        if (skeletonY > playerY) {
            move(skeletonX, skeletonY-1);
        } else if (skeletonY < playerY) {
            move(skeletonX, skeletonY+1);
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
