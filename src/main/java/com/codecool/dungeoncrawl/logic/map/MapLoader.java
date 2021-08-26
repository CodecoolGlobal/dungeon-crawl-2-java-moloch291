package com.codecool.dungeoncrawl.logic.map;

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
                            new Key("Key", cell, ItemType.KEY, KeyType.DOOR_KEY);
                            break;
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            new Key("Bridge key", cell, ItemType.KEY, KeyType.BRIDGE_KEY);
                            break;
                        case 'l':
                            cell.setType(CellType.FLOOR);
                            new Key("Lock pick", cell, ItemType.KEY, KeyType.LOCK_PICK);
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            new Armor("Shield", cell, ItemType.ARMOR, ArmorType.SHIELD);
                            break;
                        case 'H':
                            cell.setType(CellType.FLOOR);
                            new Armor("Helmet", cell, ItemType.ARMOR, ArmorType.HELMET);
                            break;
                        case 'C':
                            cell.setType(CellType.FLOOR);
                            new Armor("Breastplate", cell, ItemType.ARMOR, ArmorType.BREASTPLATE);
                            break;
                        case 'L':
                            cell.setType(CellType.FLOOR);
                            new Armor("Greaves", cell, ItemType.ARMOR, ArmorType.GREAVES);
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            new Armor("Gauntlets", cell, ItemType.ARMOR, ArmorType.GAUNTLETS);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon("Sword", cell, ItemType.WEAPON, WeaponType.SWORD);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Weapon("Axe", cell, ItemType.WEAPON, WeaponType.AXE);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Weapon("Pike", cell, ItemType.WEAPON, WeaponType.PIKE);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Food("Bread", cell, ItemType.FOOD, FoodType.BREAD);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Food("Cheese", cell, ItemType.FOOD, FoodType.CHEESE);
                            break;
                        case 'v':
                            cell.setType(CellType.FLOOR);
                            new Food("Water bottle", cell, ItemType.FOOD, FoodType.WATER);
                            break;
                        case 'n':
                            cell.setType(CellType.FLOOR);
                            new Food("Apple", cell, ItemType.FOOD, FoodType.APPLE);
                            break;
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            new Food("Fish", cell, ItemType.FOOD, FoodType.FISH);
                            break;    
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Potion("Healing potion", cell, ItemType.POTION, PotionType.HEALING_POTION);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Potion("Stone skin potion", cell, ItemType.POTION, PotionType.STONE_SKIN_POTION);
                            break;
                        case 'j':
                            cell.setType(CellType.FLOOR);
                            new Potion("Potion of might", cell, ItemType.POTION, PotionType.MIGHT_POTION);
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
