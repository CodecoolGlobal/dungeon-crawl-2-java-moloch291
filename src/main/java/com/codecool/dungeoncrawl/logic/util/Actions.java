package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Orc;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.util.Locale;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.Y;

public class Actions {

    public void pickUpItem(GameMap map) {
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        if (map.getCell(playerX, playerY).getItem() != null) {
            Item item = map.getCell(playerX, playerY).getItem();
            map.getPlayer().setInventory(item, 1);
            map.getCell(playerX, playerY).setItem(null);
        }
    }


    public void quitGame(KeyEvent keyEvent, Label quitLabel) {
        quitLabel.setText("Are you sure you want to quit? Y/N");
        if (keyEvent.getCode() == Y) {
            System.exit(0);
        } else if (keyEvent.getCode() == N) {
            quitLabel.setText("");
        }
    }

    public void movement(int moveInRow, int moveInColumn, GameMap map) {
        map.getPlayer().move(moveInRow, moveInColumn);
        lookForDoor(map);
    }

    public void moveMonsters(GameMap map){
        for (Skeleton skeleton : map.getSkeletons()){
            skeleton.monsterMove(map.getPlayer().getCell());
        }
        for (Orc orc : map.getOrcs()){
            orc.monsterMove(map.getPlayer().getCell());
        }
    }

    public void lookForDoor(GameMap map) {
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        if (doorNextToPlayer(playerX, playerY, map) && map.getPlayer().hasKey())
            map.openDoor();
    }

    public boolean doorNextToPlayer(int playerX, int playerY, GameMap map) {
        boolean doorToTheLeft = map.getCell(playerX, playerY - 1).getType() == CellType.CLOSED_DOOR;
        boolean doorToTheRight = map.getCell(playerX, playerY + 1).getType() == CellType.CLOSED_DOOR;
        boolean doorBelow = map.getCell(playerX + 1, playerY).getType() == CellType.CLOSED_DOOR;
        boolean doorAbove = map.getCell(playerX - 1, playerY).getType() == CellType.CLOSED_DOOR;
        return doorToTheLeft || doorToTheRight || doorBelow || doorAbove;
    }

    public void checkNearbyMonsters(Actor player, Label actionLabel) {
        Cell cell = player.getCell();
        Cell nearbyCell = cell.getNeighbor(-1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player, actionLabel);
        }
        nearbyCell = cell.getNeighbor(1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player, actionLabel);
        }
        nearbyCell = cell.getNeighbor(0, -1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player, actionLabel);
        }
        nearbyCell = cell.getNeighbor(0, 1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell, player, actionLabel);
        }
    }

    public void fight(Cell nearbyCell, Actor player, Label actionLabel) {
        actionLabel.setText("");
        int playerAttack = player.getAttack();
        int playerDefense = player.getDefense();
        int playerHealth = 100;
        Actor enemy = nearbyCell.getActor();
        int enemyAttack = enemy.getAttack();
        int enemyDefense = enemy.getDefense();
        int enemyHealth = enemy.getHealth();
        while (true) {
            int playerHit = Util.getRandomNumber(playerAttack + 2, playerAttack - 1) - (enemyDefense / 2);
            enemyHealth -= playerHit;
            actionLabel.setText(actionLabel.getText() + "\nYou hit the enemy for " + playerHit);
            if (enemyHealth <= 0) {
                nearbyCell.setActor(null);
                actionLabel.setText(actionLabel.getText() + "\nYou killed the enemy!");
                player.setHealth(playerHealth);
                enemy.setHealth(enemyHealth);
                break;
            }
            int enemyHit = Util.getRandomNumber(enemyAttack + 2, enemyAttack - 1) - (playerDefense / 2);
            playerHealth -= enemyHit;
            actionLabel.setText(actionLabel.getText() + "\nThe enemy hit you for " + enemyHit);
            if (playerHealth <= 0) {
                player.getCell().setActor(null);
                actionLabel.setText(actionLabel.getText() + "\nYou died!");
                enemy.setHealth(enemyHealth);
                System.exit(0);
                break;
            }
        }
    }


    public Actions() {
    }
}
