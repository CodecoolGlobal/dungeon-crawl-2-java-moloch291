package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int x;
    private int y;
    private boolean drunk;

    public PlayerModel(String playerName, int hp, int x, int y, boolean drunk) {
        this.playerName = playerName;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.drunk = drunk;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.drunk = player.isDrunk();

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getDrunk() { return drunk; }

    public void setDrunk() { this.drunk = drunk;}

    /*
    @Override
    public String toString() {
        return String.format("Player %d -> Name: %s, HP: %d, Position: %d:%d, Is he drunk: %s",
                             id, playerName, hp, x, y, drunk);
    }
     */

}
