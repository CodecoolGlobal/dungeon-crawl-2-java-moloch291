package com.codecool.dungeoncrawl.logic.map;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.util.GameConditions;

import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable {

    private final int width;
    private final int height;
    public GameConditions gameConditions;
    private final Cell[][] cells;
    private final ArrayList<Monster> skeletons = new ArrayList<>();
    private final ArrayList<Monster> orcs = new ArrayList<>();
    private final ArrayList<Monster> undeads = new ArrayList<>();
    private final ArrayList<Monster> krakens = new ArrayList<>();
    private final ArrayList<Monster> ghosts = new ArrayList<>();
    private Player player;
    private CellType exit;
    private char[] mapAsCharArray;
    private MapName mapName;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.gameConditions = new GameConditions();
        this.exit = CellType.CLOSED_DOOR;
        cells = defineCells(width, height, defaultCellType);
    }

    public ArrayList<Monster> getKraken() {
        return krakens;
    }

    public ArrayList<Monster> getGhosts() {
        return ghosts;
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

    public ArrayList<Monster> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Monster> getOrcs() {
        return orcs;
    }

    public ArrayList<Monster> getUndeads() {
        return undeads;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public CellType getExit() {
        return exit;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addKraken(Kraken kraken) {
        this.krakens.add(kraken);
    }

    public void addGhost(Ghost ghost) {
        this.ghosts.add(ghost);
    }

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

    public char[] getMapAsCharArray() {
        return mapAsCharArray;
    }

    public void setMapAsCharArray(char[] mapAsCharArray) {
        this.mapAsCharArray = mapAsCharArray;
    }

    public MapName getMapName() {
        return mapName;
    }

    public void setMapName(MapName mapName) {
        this.mapName = mapName;
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

    public void openDoor() {
        this.exit = CellType.OPEN_DOOR;
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                if (cell.getType() == CellType.CLOSED_DOOR)
                    cell.setType(CellType.OPEN_DOOR);
            }
        }
    }

    /*public char[] gameMapToString(Cell[][] cells) {
        char[]
    }*/
}
