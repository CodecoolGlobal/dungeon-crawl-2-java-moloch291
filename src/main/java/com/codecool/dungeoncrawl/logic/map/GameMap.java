package com.codecool.dungeoncrawl.logic.map;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.util.GameConditions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameMap implements Serializable {

    private final int width;
    private final int height;
    public GameConditions gameConditions;
    private final Cell[][] cells;
    private final List<Monster> monsterList = new ArrayList<>();
    private Player player;
    private CellType exit;
    private MapName mapName;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.gameConditions = new GameConditions();
        this.exit = CellType.CLOSED_DOOR;
        cells = defineCells(width, height, defaultCellType);
    }

    private Cell[][] defineCells(int width, int height, CellType defaultCellType) {
        final Cell[][] cells;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
        return cells;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public MapName getMapName() {
        return mapName;
    }

    public void setMapName(MapName mapName) {
        this.mapName = mapName;
    }

    public CellType getExit() {
        return exit;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addMonster(Monster monster) {
        this.monsterList.add(monster);
    }

    public void openDoor() {
        this.exit = CellType.OPEN_DOOR;
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                if (cell.getType() == CellType.CLOSED_DOOR)
                    cell.setType(CellType.OPEN_DOOR);
            }
        }
    }

    public void monsterInteractions() {
        for (int index = 0; index < monsterList.size(); index++) {
            if (monsterList.get(index).getHealth() > 0) {
                if (!(monsterList.get(index) instanceof Undead))
                    ((MonsterInteractions) monsterList.get(index)).monsterMove(player.getCell());
            }
            else monsterList.remove(monsterList.get(index));
        }
    }
}
