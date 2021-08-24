package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Undead;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MapLoader {

    public static int[] getPlayerPosition() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);

        scanner.nextLine(); // empty line
        int[] result = new int[3];
        int y = -1;
        while (true) {
            y++;
            String line = "";
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') {
                    result[0] = y;
                    result[1] = x;
                }
            }
        }
        result[2] = y;
        return result;
    }

    public static GameMap loadMap(int height) {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);

        String line = scanner.nextLine(); // empty line
        int width = line.length();

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        int y = -1;
        while (true) {
            y++;
            line = "";
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            for (int x = 0; x < line.length(); x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addSkeleton(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key("Key", cell, ItemType.KEY);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Armor("Shield", cell, ItemType.ARMOR, 10);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon("Sword", cell, ItemType.WEAPON, 10);
                            break;
                        case 'f':
                            cell.setType(CellType.FLOOR);
                            new Food("Bread", cell, ItemType.FOOD);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion("Potion", cell, ItemType.POTION);
                            break;
                        case 'd':
                            cell.setType(map.getExit());
                            break;
                        case 'o':
                            cell.setType(CellType.FLOOR);
                            map.addOrc(new Orc(cell));
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            map.addUndead(new Undead(cell));
                            break;
                        case '/':
                            cell.setType(CellType.BRICK_WALL);
                            break;
                        case '~':
                            cell.setType(CellType.WATER);
                            break;
                        case '^':
                            cell.setType(CellType.HOUSE);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
