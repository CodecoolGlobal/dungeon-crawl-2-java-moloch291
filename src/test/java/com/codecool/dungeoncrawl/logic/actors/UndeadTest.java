package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UndeadTest {
    GameMap gameMap;
    Undead undead;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(4, 4, CellType.FLOOR);
        undead = new Undead(gameMap.getCell(1,1));
    }

    @Test
    void undeadTileNameIsCorrect(){
        String correctName = "undead";
        String skeletonName = undead.getTileName();
        assertEquals(correctName,skeletonName);
    }
}