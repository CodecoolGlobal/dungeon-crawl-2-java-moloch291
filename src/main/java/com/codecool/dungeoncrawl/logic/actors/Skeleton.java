package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.util.Direction;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        super.setAttack(3);
        super.setDefense(1);
        super.setHealth(6);
    }

    @Override
    public void monsterMove(Cell playerCell) {
        setXCoordinate(playerCell);
        setYCoordinate(playerCell);
    }

    public void setXCoordinate(Cell playerCell) {
        int playerX = playerCell.getX();
        if (getX() > playerX) {
            move( Direction.WEST.getX(), Direction.WEST.getY());
        } else if (getX() < playerX) {
            move( Direction.EAST.getX(), Direction.EAST.getY());
        }
    }

    public void setYCoordinate(Cell playerCell) {
        int playerY = playerCell.getY();
        if (getY() > playerY) {
            move(Direction.NORTH.getX(), Direction.NORTH.getY());
        } else if (getY() < playerY) {
            move(Direction.SOUTH.getX(), Direction.SOUTH.getY());
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
