package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.model.PlayersInventory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventorySaveTest {

    public InventorySaveTest() {
    }

    public void saveInventory(GameMap map)
            throws IOException, ClassNotFoundException {
        PlayersInventory inventory = new PlayersInventory(map);

        FileOutputStream fileOutputStream
                = new FileOutputStream("inventory.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(inventory);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public PlayersInventory loadInventory()
            throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream
                = new FileInputStream("inventory.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        PlayersInventory loadedInventory = (PlayersInventory) objectInputStream.readObject();
        objectInputStream.close();
        return loadedInventory;
    }
}
