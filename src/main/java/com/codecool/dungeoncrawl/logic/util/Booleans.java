package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Booleans {

    private final GameMap map;

    public Booleans(GameMap map) {
        this.map = map;
    }

    public boolean checkDoorInDirection(int playerX, int playerY, Direction direction) {
        return map.getCell(
                playerX + direction.getX(), playerY + direction.getY()).getType() == CellType.CLOSED_DOOR;
    }
}
