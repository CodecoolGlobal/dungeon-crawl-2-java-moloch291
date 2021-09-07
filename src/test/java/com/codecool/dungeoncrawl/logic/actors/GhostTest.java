package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTest {
    GameMap gameMap;
    Ghost ghost;
    Player player;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(4, 4, CellType.FLOOR);
        ghost = new Ghost(gameMap.getCell(2,2));
        player = new Player(gameMap.getCell(3,3));
    }

    @Test
    void moveInXWorksCorrectly(){
        ghost.monsterMove(player.getCell());
        assertEquals(ghost.getX(),1);
    }

    @Test
    void moveInYWorksCorrectly(){
        ghost.monsterMove(player.getCell());
        assertEquals(ghost.getY(),1);
    }

    @Test
    void ghostTileNameIsCorrect(){
        String correctName = "ghost";
        String skeletonName = ghost.getTileName();
        assertEquals(correctName,skeletonName);
    }

}