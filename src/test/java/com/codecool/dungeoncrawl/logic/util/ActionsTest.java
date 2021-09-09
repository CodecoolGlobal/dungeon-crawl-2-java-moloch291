package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionsTest {

    GameMap gameMap;
    Skeleton skeleton;
    Orc orc;
    Orc orc2;
    Undead undead;
    Ghost ghost;
    Kraken kraken;
    Player player;
    Actions actions;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(10, 10, CellType.FLOOR);
        skeleton = new Skeleton(gameMap.getCell(1,1));
        orc = new Orc(gameMap.getCell(2,2));
        orc2 = new Orc(gameMap.getCell(7,7));
        undead = new Undead(gameMap.getCell(3,3));
        ghost = new Ghost(gameMap.getCell(4,4));
        kraken = new Kraken(gameMap.getCell(5,5),gameMap);
        player = new Player(gameMap.getCell(9,9));
        actions = new Actions();
        gameMap.setPlayer(player);
    }



}