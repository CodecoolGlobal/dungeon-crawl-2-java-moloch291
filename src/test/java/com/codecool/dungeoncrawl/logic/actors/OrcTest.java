package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrcTest {

    GameMap gameMap;
    Orc orc;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        orc = new Orc(gameMap.getCell(1,1));
    }

    @Test
    void moveThrowsWithBadInput(){
        assertThrows(IndexOutOfBoundsException.class,()->orc.move(0,99));
    }

    @Test
    void orcTileNameIsCorrect(){
        String correctName = "orc";
        String playerName = orc.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void moveToWest(){
        Direction originalDirection = orc.getDirection();
        orc.monsterMove(orc.getCell());
        assertNotEquals(originalDirection,orc.getDirection());
    }
    @Test
    void moveToSouth(){
        orc.setDirection(Direction.WEST);
        Direction originalDirection = orc.getDirection();
        orc.monsterMove(orc.getCell());
        assertNotEquals(originalDirection,orc.getDirection());
    }



}