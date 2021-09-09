package com.codecool.dungeoncrawl.logic.map;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MapLoader {

    public static int[] getPlayerPosition(String map) {
        InputStream is = MapLoader.class.getResourceAsStream(map);
        Scanner scanner = new Scanner(is);

        scanner.nextLine(); // empty line
        int[] result = new int[3];
        int y = -1;
        while (true) {
            y++;
            String line;
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

    public static GameMap loadMap(int height, String mapToLoad, GameMap previousMap) {
        InputStream is = MapLoader.class.getResourceAsStream(mapToLoad);
        Scanner scanner = new Scanner(is);

        String line = scanner.nextLine(); // empty line
        int width = line.length();

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        int y = -1;
        while (true) {
            y++;
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
                        case '-':
                            cell.setType(CellType.FLOOR2);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            setPlayer(previousMap, map, cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(StringFactory.KEY_CAP.message, cell, KeyType.DOOR_KEY);
                            break;
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            new Key("Bridge key", cell, KeyType.BRIDGE_KEY);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            new Key("Lock pick", cell, KeyType.LOCK_PICK);
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            new Armor(StringFactory.SHIELD_CAP.message, cell, ArmorType.SHIELD);
                            break;
                        case 'P':
                            cell.setType(CellType.FLOOR);
                            new Armor(StringFactory.HELMET_CAP.message, cell, ArmorType.HELMET);
                            break;
                        case 'C':
                            cell.setType(CellType.FLOOR);
                            new Armor(StringFactory.BREASTPLATE_CAP.message, cell, ArmorType.BREASTPLATE);
                            break;
                        case 'E':
                            cell.setType(CellType.FLOOR);
                            new Armor(StringFactory.GREAVES_CAP.message, cell, ArmorType.GREAVES);
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            new Armor(StringFactory.GAUNTLETS_CAP.message, cell, ArmorType.GAUNTLETS);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon(StringFactory.SWORD_CAP.message, cell, WeaponType.SWORD);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Weapon(StringFactory.AXE_CAP.message, cell, WeaponType.AXE);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Weapon(StringFactory.PIKE_CAP.message, cell, WeaponType.PIKE);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Food(StringFactory.BREAD_CAP.message, cell, FoodType.BREAD);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Food(StringFactory.CHEESE_CAP.message, cell, FoodType.CHEESE);
                            break;
                        case 'v':
                            cell.setType(CellType.FLOOR);
                            new Food(StringFactory.WATER_BOTTLE.message, cell, FoodType.WATER);
                            break;
                        case 'n':
                            cell.setType(CellType.FLOOR);
                            new Food(StringFactory.APPLE_CAP.message, cell, FoodType.APPLE);
                            break;
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            new Food(StringFactory.FISH_CAP.message, cell, FoodType.FISH);
                            break;    
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Potion(StringFactory.HEALING_POTION.message, cell, PotionType.HEALING_POTION);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Potion(StringFactory.STONE_SKIN_POTION.message, cell, PotionType.STONE_SKIN_POTION);
                            break;
                        case 'j':
                            cell.setType(CellType.FLOOR);
                            new Potion(StringFactory.MIGHT_POTION.message, cell, PotionType.MIGHT_POTION);
                            break;
                        case 'B':
                            cell.setType(CellType.WATER);
                            new Boat(StringFactory.BOAT_CAP.message, cell);
                            break;
                        case 'd':
                            cell.setType(map.getExit());
                            break;
                        case 'o':
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Orc(cell));
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Undead(cell));
                            break;
                        case '/':
                            cell.setType(CellType.BRICK_WALL);
                            break;
                        case '~':
                            cell.setType(CellType.WATER);
                            break;
                        case '_':
                            cell.setType(CellType.RIVER);
                            break;
                        case '^':
                            cell.setType(CellType.HOUSE);
                            break;
                        case '«':
                            cell.setType(CellType.RAMP_START);
                            break;
                        case '|':
                            cell.setType(CellType.RAMP_MIDDLE);
                            break;
                        case '»':
                            cell.setType(CellType.RAMP_END);
                            break;
                        case '!':
                            cell.setType(CellType.TORCH);
                            break;
                        case 'L':
                            cell.setType(CellType.LADDER);
                            break;
                        case 'l':
                            cell.setType(CellType.LADDER_UPPER);
                            break;
                        case 'H':
                            cell.setType(CellType.LAKE_HOUSE);
                            break;
                        case 'F':
                            cell.setType(CellType.FAKE_DOOR);
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Alcohol(cell);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'W':
                            cell.setType(CellType.WEED);
                            break;
                        case 'M':
                            cell.setType(CellType.MOSS);
                            break;
                        case 'R':
                            cell.setType(CellType.ROAD_SIGN);
                            break;
                        case 'f':
                            cell.setType(CellType.CAMPFIRE);
                            break;
                        case 'ü':
                            cell.setType(CellType.CAULDRON);
                            break;
                        case 'r':
                            cell.setType(CellType.ROCKS);
                            break;
                        case 'T':
                            cell.setType(CellType.TENT);
                            break;
                        case 'Q':
                            cell.setType(CellType.WATER);
                            map.addMonster(new Kraken(cell,map));
                            break;
                        case 'Ű':
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Ghost(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    private static void setPlayer(GameMap previousMap, GameMap map, Cell cell) {
        if (previousMap != null) {
            setExistingPlayer(previousMap, map, cell);
        } else {
            map.setPlayer(new Player(cell));
        }
    }

    private static void setExistingPlayer(GameMap previousMap, GameMap map, Cell cell) {
        Player previousPlayer = previousMap.getPlayer();
        cell.setActor(previousPlayer);
        previousPlayer.setCell(cell);
        map.setPlayer(previousPlayer);
    }
}
