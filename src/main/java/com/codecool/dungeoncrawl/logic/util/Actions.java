package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.ItemActions;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;

public class Actions {

    GameConditions gameConditions = new GameConditions();

    public void fight(Cell nearbyCell, Actor player, Label actionLabel) {
        actionLabel.setText("");
        Actor enemy = nearbyCell.getActor();
        while (true) {
            int playerHealth = 100;
            // Player's turn:
            if (playerTurn(nearbyCell, player, actionLabel, enemy, playerHealth)) break;
            // Enemy turn:
            enemyTurn(player, actionLabel, enemy);
        }
    }

    private void enemyTurn(Actor player, Label actionLabel, Actor enemy) {
        int playerHealth;
        playerHealth = hit(actionLabel, enemy, player, StringFactory.ENEMY_HIT.message);
        if (gameConditions.isDead(playerHealth))
            die();
    }

    private boolean playerTurn(Cell nearbyCell, Actor player, Label actionLabel, Actor enemy, int playerHealth) {
        int enemyHealth = hit(actionLabel, player, enemy, StringFactory.HIT_ENEMY.message);
        if (gameConditions.isDead(enemyHealth)) {
            killEnemy(nearbyCell, player, actionLabel, playerHealth, enemy);
            return true;
        }
        return false;
    }

    // Decrease and the defender's health according to the attack value of the attacking Actor:
    private int hit(Label actionLabel, Actor attacker, Actor defender, String message) {
        int attackerHit = Util.getAttackerHit(attacker, defender);
        defender.setHealth(defender.getHealth() - attackerHit);
        actionLabel.setText(actionLabel.getText() + message + attackerHit + StringFactory.DAMAGE.message);
        return defender.getHealth();
    }

    // If player's health 0 or less:
    private void die() {
        Util.exitGame();
    }

    // If enemy's health 0 or less:
    private void killEnemy(Cell nearbyCell, Actor player, Label actionLabel, int playerHealth, Actor enemy) {
        if (enemy instanceof Ghost){
            Util.exitGame();
        }
        nearbyCell.setActor(null);
        actionLabel.setText(actionLabel.getText() + StringFactory.KILL_ENEMY.message);
        player.setHealth(playerHealth);
        enemy.setHealth(enemy.getHealth());
    }
}
