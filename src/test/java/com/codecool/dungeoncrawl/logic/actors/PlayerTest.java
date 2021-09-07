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

    GameMap gameMap;
    Player player;
    Item alcohol;

    @BeforeEach
    void setFields(){
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1,1));
        alcohol = new Alcohol(gameMap.getCell(1,1));
    }


    @Test
    void playerTileNameIsCorrect(){
        String correctName = "player";
        String playerName = player.getTileName();
        assertEquals(correctName,playerName);
    }

    @Test
    void playerInventoryIsHashMap(){
        Map<Item,Integer> hashMap = new HashMap<>();
        Map<Item,Integer> playerHashMap = player.getInventory();
        assertEquals(hashMap,playerHashMap);
    }

    @Test
    void playerIsNotDrunkONStart(){
        assertFalse(player.isDrunk());
    }

    @Test
    void setPlayerToDrunk(){
        player.setDrunk(true);
        assertTrue(player.isDrunk());
    }

    @Test
    void  addItemToInventory(){
        player.addToInventory(alcohol,1);
        assertTrue(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void removeFromInventory(){
        player.addToInventory(alcohol,1);
        player.removeFromInventory(alcohol);
        assertFalse(player.hasItem(ItemType.ALCOHOL));
    }

    @Test
    void moveToNextCell(){
        player.move(0,1);
        assertEquals(player.getY(),2);
    }

    @Test
    void moveThrowsWithBadInput(){
        assertThrows(IndexOutOfBoundsException.class,()->player.move(0,99));
    }

}