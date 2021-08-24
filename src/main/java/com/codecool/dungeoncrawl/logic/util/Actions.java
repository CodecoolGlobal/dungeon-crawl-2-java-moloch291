package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Undead;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;

public class Actions {

    gameConditions gameConditions = new gameConditions();

    public void pickUpItem(GameMap map) {
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        if (gameConditions.isItemOnPlayerPosition(playerX, playerY, map)) {
            Item item = map.getCell(playerX, playerY).getItem();
            Item itemInInventory;
            String itemName = item.getName();
            int count = 0;
            Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
            for (Item itemInLoop: playerInventory.keySet()) {
                if (itemInLoop.getName().equals(item.getName())) {
                    itemInInventory = itemInLoop;
                    count = playerInventory.get(itemInLoop);
                    map.getPlayer().removeFromInventory(itemInLoop);
                }
            }
            //map.getPlayer().setInventory(item, 1);
            //int count = map.getPlayer().getInventory().getOrDefault(item, 0);
            map.getPlayer().setInventory(item, count + 1);
            map.getCell(playerX, playerY).setItem(null);
        }
    }

   /* private void checkInventoryContents(Map<Item, Integer> inventory, String name) {
        for (Item item: inventory.keySet()) {

        }
    }*/

    public void monsterInteractions(GameMap map) {
        removeDeadMonsters(map);
        moveMonsters(map.getSkeletons(), map.getPlayer().getCell());
        moveMonsters(map.getOrcs(), map.getPlayer().getCell());
    }


    private void removeDeadMonsters(GameMap map) {
        removeSkeletons(map);
        removeOrcs(map);
        removeUndeads(map);
    }

    private void removeUndeads(GameMap map) {
        ArrayList<Actor> undeads = map.getUndeads();
        for (int i = 0; i < undeads.size(); i++) {
            if (undeads.get(i).getHealth() <= 0) {
                map.removeUndead(i);
            }
        }
    }

    private void removeOrcs(GameMap map) {
        ArrayList<Actor> orcs = map.getOrcs();
        for (int i = 0; i < orcs.size(); i++) {
            if (orcs.get(i).getHealth() <= 0) {
                map.removeOrc(i);
            }
        }
    }

    private void removeSkeletons(GameMap map) {
        ArrayList<Actor> skeletons = map.getSkeletons();
        for (int i = 0; i < skeletons.size(); i++) {
            if (skeletons.get(i).getHealth() <= 0) {
                map.removeSkeleton(i);
            }
        }
    }

    private void moveMonsters(ArrayList<Actor> monstersToMove, Cell playerCell) {
        for (Actor monster : monstersToMove)
            monster.monsterMove(playerCell);
    }


    public void movement(int moveInRow, int moveInColumn, GameMap map, Label actionLabel) {
        map.getPlayer().move(moveInRow, moveInColumn);
        lookForDoor(map);
        checkNearbyMonsters(map.getPlayer(), actionLabel);
    }

    private void lookForDoor(GameMap map) {
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        if (doorNextToPlayer(playerX, playerY, map) && map.getPlayer().hasKey())
            map.openDoor();
    }

    private boolean doorNextToPlayer(int playerX, int playerY, GameMap map) {
        boolean doorToTheLeft = gameConditions.checkDoorInDirection(playerX, playerY, Direction.NORTH, map);
        boolean doorToTheRight = gameConditions.checkDoorInDirection(playerX, playerY, Direction.SOUTH, map);
        boolean doorBelow = gameConditions.checkDoorInDirection(playerX, playerY, Direction.EAST, map);
        boolean doorAbove = gameConditions.checkDoorInDirection(playerX, playerY, Direction.WEST, map);
        return doorToTheLeft || doorToTheRight || doorBelow || doorAbove;
    }

    private void checkNearbyMonsters(Actor player, Label actionLabel) {
        Cell cell = player.getCell();
        checkForEnemies(player, cell, Direction.WEST, actionLabel);
        checkForEnemies(player, cell, Direction.EAST, actionLabel);
        checkForEnemies(player, cell, Direction.NORTH, actionLabel);
        checkForEnemies(player, cell, Direction.SOUTH, actionLabel);
    }

    private void checkForEnemies(Actor player, Cell playerCell, Direction currentDirection, Label actionLabel) {
        Cell nearbyCell = playerCell.getNeighbor(currentDirection.getX(), currentDirection.getY());
        if (gameConditions.isCellOccupied(nearbyCell)) {
            fight(nearbyCell, player, actionLabel);
        }
    }

    private void fight(Cell nearbyCell, Actor player, Label actionLabel) {
        actionLabel.setText("");
        fightLoop(nearbyCell, player, actionLabel, nearbyCell.getActor());
    }

    private void fightLoop(Cell nearbyCell, Actor player, Label actionLabel, Actor enemy) {
        while (true) {
            int playerHealth = 100;
            int enemyHealth;
            enemyHealth = hit(actionLabel, player, enemy, "\nYou hit the enemy for ");
            if (enemyHealth <= 0) {
                killEnemy(nearbyCell, player, actionLabel, playerHealth, enemy, enemy.getHealth());
                break;
            }
            playerHealth = hit(actionLabel, enemy, player, "\nEnemy hit you for ");
            if (playerHealth <= 0) {
                die();
            }
        }
    }

    private int hit(Label actionLabel, Actor attacker, Actor defender, String message) {
        int attackerHit = getAttackerHit(attacker, defender);
        defender.setHealth(defender.getHealth() - attackerHit);
        actionLabel.setText(actionLabel.getText() + message + attackerHit + " damage!");
        return defender.getHealth();
    }

    private int getAttackerHit(Actor attacker, Actor defender) {
        return Util.getRandomNumber(
                attacker.getAttack() + NumberParameters.ATTACK_BONUS.getValue(),
                attacker.getAttack() - NumberParameters.ATTACK_NERF.getValue())
                - (defender.getDefense() / NumberParameters.DEFENSE_DIVISOR.getValue()
        );
    }

    private void die() {
        System.exit(0);
    }

    private void killEnemy(
            Cell nearbyCell,
            Actor player,
            Label actionLabel,
            int playerHealth,
            Actor enemy,
            int enemyHealth
    ) {
        nearbyCell.setActor(null);
        actionLabel.setText(actionLabel.getText() + "\nYou killed the enemy!");
        player.setHealth(playerHealth);
        enemy.setHealth(enemyHealth);
    }


    public Actions() {
    }
}
