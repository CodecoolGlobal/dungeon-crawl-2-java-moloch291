package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KrakenTest {

    GameMap gameMapWithFloorCells;
    GameMap gameMapWithWaterCells;
    Kraken krakenInTheFloor;
    Kraken krakenIntTheWater;

    @BeforeEach
    void setFields(){
        gameMapWithFloorCells = new GameMap(3, 3, CellType.FLOOR);
        gameMapWithWaterCells = new GameMap(3, 3, CellType.WATER);
        krakenInTheFloor = new Kraken(gameMapWithFloorCells.getCell(1,1),gameMapWithFloorCells);
        krakenIntTheWater = new Kraken(gameMapWithWaterCells.getCell(1,1),gameMapWithWaterCells);
    }

    @Test
    void dontCollectFloor(){
        assertEquals(krakenInTheFloor.getWaterCells().size(),0);
    }

    @Test
    void collectWater(){
        assertNotEquals(krakenIntTheWater.getWaterCells().size(),0);
    }

    @Test
    void krakenTileNameIsCorrect(){
        String correctName = "kraken";
        String playerName = krakenInTheFloor.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void moveThrowsWithBadInput(){
        assertThrows(IndexOutOfBoundsException.class,()->krakenInTheFloor.move(0,99));
    }

    @Test
    void notMovingIntoWater(){
        krakenInTheFloor.move(2,2);
        assertNotEquals(krakenInTheFloor.getCell(),gameMapWithFloorCells.getCell(2,2));
    }

    @Test
    void MovingIntoWater(){
        krakenIntTheWater.move(2,2);
        assertEquals(krakenIntTheWater.getCell(),gameMapWithWaterCells.getCell(2,2));
    }

    @Test
    void MonsterMoveMethodWorks(){
        Cell originalKrakenCell = krakenIntTheWater.getCell();
        krakenIntTheWater.monsterMove(originalKrakenCell);
        assertNotEquals(krakenIntTheWater.getCell(),originalKrakenCell);
    }


}