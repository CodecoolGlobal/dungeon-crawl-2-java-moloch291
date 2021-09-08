package com.codecool.dungeoncrawl.IO;

import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.io.*;

public class GameMapIO {

    public GameMapIO() {
    }

    public void saveGameMap(GameMap map)
            throws IOException, ClassNotFoundException {

        FileOutputStream fileOutputStream
                = new FileOutputStream("savegame.json");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(map);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public GameMap loadGameMap()
            throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream
                = new FileInputStream("savegame.json");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        GameMap loadedMap = (GameMap) objectInputStream.readObject();
        objectInputStream.close();
        return loadedMap;
    }
}

