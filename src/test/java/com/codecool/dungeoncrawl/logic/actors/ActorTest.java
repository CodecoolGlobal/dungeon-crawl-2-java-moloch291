package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap;
    Player player;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1, 1));
    }

    @Test
    void moveUpdatesCells() { ;
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void healthStartsAt10(){
        int health = 10;
        int playerHealth = player.getHealth();
        assertEquals(health,playerHealth);
    }

    @Test
    void attackStartsAt3(){
        int attack = 3;
        int playerAttack = player.getAttack();
        assertEquals(attack,playerAttack);
    }

    @Test
    void defenseStartAt0(){
        int defense = 0;
        int playerDefense = player.getDefense();
        assertEquals(defense,playerDefense);
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }



    @Test
    void cannotMoveIntoAnotherActor() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }
}