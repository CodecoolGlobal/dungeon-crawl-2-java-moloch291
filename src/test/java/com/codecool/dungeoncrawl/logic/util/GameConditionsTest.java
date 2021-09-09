package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConditionsTest {

    GameMap gameMap;
    Player player;
    GameConditions gameConditions;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1,1));
        gameConditions = new GameConditions();
    }

    @Test
    void fakeDoorCheckerWorksCorrectly(){
        boolean noFakeDoor =gameConditions.checkFakeDoor(player.getX(), player.getY(),gameMap);
        assertFalse(noFakeDoor);
    }

    @Test
    void doorInDirectionCheckerWorksCorrectly(){
        boolean doorInDirection = gameConditions.checkDoorInDirection(player.getX(), player.getY(),
                Direction.NORTH,gameMap);
        assertFalse(doorInDirection);
    }

    @Test
    void openDoorCheckerWorksCorrectly(){
        boolean openDoor = gameConditions.checkOpenDoor(player.getX(),player.getY(),gameMap);
        assertFalse(openDoor);
    }

    @Test
    void nextCellCheckerWorksCorrectly(){
        boolean nextCellIsUsable = gameConditions.checkNextCell(gameMap.getCell(2,2));
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerThrowsException(){
        assertThrows(ArrayIndexOutOfBoundsException.class,()->gameConditions.checkNextCell(gameMap.getCell(70,2)));
    }

    @Test
    void nextCellCheckerForKrakenWorksCorrectly(){
        boolean nextCellIsUsable = gameConditions.checkNextCellKraken(gameMap.getCell(2,2));
        assertFalse(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForGhostWorksCorrectly(){
        boolean nextCellIsUsable = gameConditions.checkNextCellGhost(gameMap.getCell(2,2));
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithoutBoat(){
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMap.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithBoat(){
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMap.getCell(2,2),true);
        assertFalse(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithFloor2(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.FLOOR2);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
        void nextCellCheckerForPlayerWorksWithRampStart(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.RAMP_START);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithRampMiddle(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.RAMP_MIDDLE);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithRampEnd(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.RAMP_END);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithLadder(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.LADDER);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithLadderUpper(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.LADDER_UPPER);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithOpenDoor(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.OPEN_DOOR);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertTrue(nextCellIsUsable);
    }

    @Test
    void nextCellCheckerForPlayerWorksWithClosedDoor(){
        GameMap gameMapToCheck = new GameMap(3, 3, CellType.CLOSED_DOOR);
        boolean nextCellIsUsable = gameConditions.checkNextCellPlayer(gameMapToCheck.getCell(2,2),false);
        assertFalse(nextCellIsUsable);
    }

    @Test
    void itemOnPlayerWorksCorrectly(){
        boolean itemOnPlayer = gameConditions.isItemOnPlayerPosition(player.getX(),player.getY(),gameMap);
        assertFalse(itemOnPlayer);
    }

    @Test
    void doorNextToPlayerCheckWorksCorrectly(){
        boolean doorNextToPlayer = gameConditions.doorNextToPlayer(player.getX(),player.getY(),gameMap);
        assertFalse(doorNextToPlayer);
    }

    @Test
    void deadCheckWorksCorrectly(){
        boolean playerIsDead = gameConditions.isDead(player.getHealth());
        assertFalse(playerIsDead);
    }

    @Test
    void checkArmorWorksCorrectly(){
        Item armor = new Armor("Shield",gameMap.getCell(2,2), ArmorType.SHIELD);
        boolean isItArmor = gameConditions.checkIfArmor(armor);
        assertTrue(isItArmor);
    }

    @Test
    void checkWeaponWorksCorrectly(){
        Item weapon = new Weapon("Weapon",gameMap.getCell(2,2), WeaponType.AXE);
        boolean isItWeapon = gameConditions.checkIfWeapon(weapon);
        assertTrue(isItWeapon);
    }



}