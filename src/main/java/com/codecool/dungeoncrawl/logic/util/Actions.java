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
    Util util = new Util();

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
        removeBodies(map.getSkeletons(), map);
        removeBodies(map.getOrcs(), map);
        removeBodies(map.getUndeads(), map);
    }

    private void removeBodies(ArrayList<Actor> monsters, GameMap map) {
        for (int index = 0; index < monsters.size(); index++) {
            if (gameConditions.isDead(monsters.get(index).getHealth()))
                removeBody(monsters.get(index), map, index);
        }
    }

    private void removeBody(Actor monster, GameMap map, int index) {
        if (monster instanceof Skeleton) map.removeSkeleton(index);
        else if (monster instanceof Orc) map.removeOrc(index);
        else if (monster instanceof Undead) map.removeUndead(index);
    }

    private void moveMonsters(ArrayList<Actor> monstersToMove, Cell playerCell) {
        for (Actor monster : monstersToMove) monster.monsterMove(playerCell);
    }


    public void movement(int moveInRow, int moveInColumn, GameMap map, Label actionLabel) {
        map.getPlayer().move(moveInRow, moveInColumn);
        lookForDoor(map);
        checkNearbyMonsters(map.getPlayer(), actionLabel);
    }

    private void lookForDoor(GameMap map) {
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        if (gameConditions.doorNextToPlayer(playerX, playerY, map) && map.getPlayer().hasKey())
            map.openDoor();
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
        if (gameConditions.isCellOccupied(nearbyCell))
            fight(nearbyCell, player, actionLabel);
    }

    private void fight(Cell nearbyCell, Actor player, Label actionLabel) {
        actionLabel.setText("");
        fightLoop(nearbyCell, player, actionLabel, nearbyCell.getActor());
    }

    private void fightLoop(Cell nearbyCell, Actor player, Label actionLabel, Actor enemy) {
        while (true) {
            int playerHealth = 100;
            int enemyHealth;
            enemyHealth = hit(actionLabel, player, enemy, StringFactory.HIT_ENEMY.message);
            if (gameConditions.isDead(enemyHealth)) {
                killEnemy(nearbyCell, player, actionLabel, playerHealth, enemy, enemy.getHealth());
                break;
            }
            playerHealth = hit(actionLabel, enemy, player, StringFactory.ENEMY_HIT.message);
            if (gameConditions.isDead(playerHealth))
                die();
        }
    }

    private int hit(Label actionLabel, Actor attacker, Actor defender, String message) {
        int attackerHit = util.getAttackerHit(attacker, defender);
        defender.setHealth(defender.getHealth() - attackerHit);
        actionLabel.setText(actionLabel.getText() + message + attackerHit + StringFactory.DAMAGE.message);
        return defender.getHealth();
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
        actionLabel.setText(actionLabel.getText() + StringFactory.KILL_ENEMY.message);
        player.setHealth(playerHealth);
        enemy.setHealth(enemyHealth);
    }


    public Actions() {
    }
}
