package com.codecool.dungeoncrawl.logic.map;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.util.gameConditions;

import java.util.ArrayList;

public class GameMap {

    private final int width;
    private final int height;
    public gameConditions gameConditions;
    private final Cell[][] cells;
    private final ArrayList<Actor> skeletons = new ArrayList<>();
    private final ArrayList<Actor> orcs = new ArrayList<>();
    private final ArrayList<Actor> undeads = new ArrayList<>();

    private Player player;
    private CellType exit;

    public void addSkeleton(Skeleton skeleton) {
        this.skeletons.add(skeleton);
    }

    public void addOrc(Orc orc) {
        this.orcs.add(orc);
    }

    public void addUndead(Undead undead) {
        this.undeads.add(undead);
    }

    public void removeSkeleton(int index) {
        this.skeletons.remove(index);
    }

    public void removeOrc(int index) {
        this.orcs.remove(index);
    }

    public void removeUndead(int index) {
        this.undeads.remove(index);
    }

    public ArrayList<Actor> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Actor> getOrcs() {
        return orcs;
    }

    public ArrayList<Actor> getUndeads() {
        return undeads;
    }


    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.gameConditions = new gameConditions();
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
