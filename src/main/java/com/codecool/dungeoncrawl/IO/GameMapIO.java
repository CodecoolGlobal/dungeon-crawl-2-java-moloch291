package com.codecool.dungeoncrawl.IO;

import com.codecool.dungeoncrawl.logic.map.GameMap;

import java.io.*;

public class GameMapIO {

    public GameMapIO() {
    }

    public void saveGameMap(GameMap map, String saveName)
            throws IOException, ClassNotFoundException {

        FileOutputStream fileOutputStream
                = new FileOutputStream(saveName);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(map);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public GameMap loadGameMap(String saveName)
            throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream
                = new FileInputStream(saveName);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        GameMap loadedMap = (GameMap) objectInputStream.readObject();
        objectInputStream.close();
        return loadedMap;
    }
}

