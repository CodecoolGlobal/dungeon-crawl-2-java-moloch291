package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Booleans {

    public boolean checkDoorInDirection(int playerX, int playerY, Direction direction, GameMap map) {
        return map.getCell(playerX + direction.getX(), playerY + direction.getY())
                .getType() == CellType.CLOSED_DOOR;
    }

    public boolean checkNextCell(Cell nextCell, int healthOfThis) {
        return (nextCell.getType().equals(CellType.FLOOR) ||
                nextCell.getType().equals(CellType.OPEN_DOOR)) &&
                nextCell.getActor() == null && healthOfThis > 0;
    }
}
