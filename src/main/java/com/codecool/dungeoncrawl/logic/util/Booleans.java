package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.MapAndParts.Cell;
import com.codecool.dungeoncrawl.logic.MapAndParts.CellType;
import com.codecool.dungeoncrawl.logic.MapAndParts.GameMap;

public class Booleans {

    public boolean checkDoorInDirection(int playerX, int playerY, Direction direction, GameMap map) {
        return map.getCell(playerX + direction.getX(), playerY + direction.getY())
                .getType() == CellType.CLOSED_DOOR;
    }

    public boolean checkNextCell(Cell nextCell) {
        return (nextCell.getType().equals(CellType.FLOOR) ||
                nextCell.getType().equals(CellType.OPEN_DOOR)) &&
                !isCellOccupied(nextCell);
    }

    public boolean isItemOnPlayerPosition(int playerX, int playerY, GameMap map) {
        return map.getCell(playerX, playerY).getItem() != null;
    }

    public boolean isCellOccupied(Cell cell) {
        return cell.getActor() != null;
    }
}
