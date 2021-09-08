package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkeletonTest {
    GameMap gameMap;
    Skeleton skeleton;
    Player player;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(4, 4, CellType.FLOOR);
        skeleton = new Skeleton(gameMap.getCell(1,1));
        player = new Player(gameMap.getCell(3,3));
    }

    @Test
    void skeletonTileNameIsCorrect(){
        String correctName = "skeleton";
        String skeletonName = skeleton.getTileName();
        assertEquals(correctName,skeletonName);
    }

    @Test
    void moveInXWorksCorrectly(){
        skeleton.monsterMove(player.getCell());
        assertEquals(skeleton.getX(),2);
    }

    @Test
    void moveInYWorksCorrectly(){
        skeleton.monsterMove(player.getCell());
        assertEquals(skeleton.getY(),2);
    }

}