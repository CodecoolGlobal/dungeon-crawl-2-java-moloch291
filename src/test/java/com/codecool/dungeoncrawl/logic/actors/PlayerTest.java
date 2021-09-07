package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.items.Alcohol;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    @Test
    void playerTileNameIsCorrect(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        String correctName = "player";
        String playerName = player.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void playerInventoryIsHashMap(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        Map<Item,Integer> hashMap = new HashMap<>();
        Map<Item,Integer> playerHashMap = player.getInventory();
        assertEquals(hashMap,playerHashMap);
    }

    @Test
    void playerIsNotDrunkONStart(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        assertFalse(player.isDrunk());
    }

    @Test
    void setPlayerToDrunk(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        player.setDrunk(true);
        assertTrue(player.isDrunk());
    }

    @Test
    void  addItemToInventory(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Item alcohol = new Alcohol(gameMap.getCell(1,1));
        Player player = new Player(gameMap.getCell(1,1));
        player.addToInventory(alcohol,1);
        assertTrue(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void removeFromInventory(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Item alcohol = new Alcohol(gameMap.getCell(1,1));
        Player player = new Player(gameMap.getCell(1,1));
        player.addToInventory(alcohol,1);
        player.removeFromInventory(alcohol);
        assertFalse(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void moveToNextCell(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        player.move(0,1);
        assertEquals(player.getY(),2);
    }

    @Test
    void moveThrowsWithBadInput(){
        GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(1,1));
        assertThrows(IndexOutOfBoundsException.class,()->player.move(0,99));
    }

}