package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Util;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    private final Map<Item, Integer> inventory = new HashMap<>();

    public String getTileName() {
        return "player";
    }

    public void checkNearbyMonsters() {
        Cell cell = this.getCell();
        Cell nearbyCell = cell.getNeighbor(-1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell);
        }
        nearbyCell = cell.getNeighbor(1, 0);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell);
        }
        nearbyCell = cell.getNeighbor(0, -1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell);
        }
        nearbyCell = cell.getNeighbor(0, 1);
        if (nearbyCell.getActor() != null) {
            fight(nearbyCell);
        }
    }

    private void fight(Cell nearbyCell) {
        int playerAttack = this.getAttack();
        int playerDefense = this.getDefense();
        int playerHealth = this.getHealth();
        Actor enemy = nearbyCell.getActor();
        int enemyAttack = enemy.getAttack();
        int enemyDefense = enemy.getDefense();
        int enemyHealth = enemy.getHealth();
        while (true) {
            int playerHit = Util.getRandomNumber(playerAttack + 2, playerAttack - 1) - (enemyDefense / 2);
            enemyHealth -= playerHit;
            System.out.println("You hit the enemy for " + playerHit);
            Util.waitOneSec();
            if (enemyHealth <= 0) {
                nearbyCell.setActor(null);
                System.out.println("You killed the enemy!");
                this.setHealth(playerHealth);
                break;
            }
            int enemyHit = Util.getRandomNumber(enemyAttack + 2, enemyAttack - 1) - (playerDefense / 2);
            playerHealth -= enemyHit;
            System.out.println("The enemy hit you for " + enemyHit);
            Util.waitOneSec();
            if (playerHealth <= 0) {
                this.getCell().setActor(null);
                System.out.println("You died!");
                enemy.setHealth(enemyHealth);
                System.exit(0);
                break;
            }
        }
    }
}
