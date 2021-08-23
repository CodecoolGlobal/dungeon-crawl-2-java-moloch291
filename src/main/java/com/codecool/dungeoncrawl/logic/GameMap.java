package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private CellType exit;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.exit = CellType.CLOSED_DOOR;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public CellType getExit() {
        return exit;
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

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
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
}
