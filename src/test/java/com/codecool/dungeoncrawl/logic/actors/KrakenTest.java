package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KrakenTest {

    @Test
    void dontCollectFloor(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        assertEquals(kraken.getWaterCells().size(),0);
    }

    @Test
    void collectWater(){
        GameMap gameMap = new GameMap(3, 3, CellType.WATER);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        assertNotEquals(kraken.getWaterCells().size(),0);
    }

    @Test
    void playerTileNameIsCorrect(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        String correctName = "kraken";
        String playerName = kraken.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void moveThrowsWithBadInput(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        assertThrows(IndexOutOfBoundsException.class,()->kraken.move(0,99));
    }

    @Test
    void notMovingIntoWater(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        kraken.move(2,2);
        assertNotEquals(kraken.getCell(),gameMap.getCell(2,2));
    }

    @Test
    void MovingIntoWater(){
        GameMap gameMap = new GameMap(3, 3, CellType.WATER);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        kraken.move(2,2);
        assertEquals(kraken.getCell(),gameMap.getCell(2,2));
    }

    @Test
    void MonsterMoveMethodWorks(){
        GameMap gameMap = new GameMap(3, 3, CellType.WATER);
        Kraken kraken = new Kraken(gameMap.getCell(1,1),gameMap);
        Cell originalKrakenCell = kraken.getCell();
        kraken.monsterMove(originalKrakenCell);
        assertNotEquals(kraken.getCell(),originalKrakenCell);
    }


}