package com.codecool.dungeoncrawl.logic.util;

public enum StringFactory {

    // In Actions class:
    HIT_ENEMY("\nYou hit the enemy for "),
    KILL_ENEMY("\nYou killed the enemy!"),
    ENEMY_HIT("\nEnemy hit you for "),
    DAMAGE(" damage!"),

    // IN Game class:
    TITLE("Dungeon Crawl"),
    PICK_UP_ITEMS("Pick up items by pressing Enter while standing on the item."),
    WANT_TO_QUIT("Are you sure you want to quit? Y/N"),
    // Item names:
    BREAD("Bread"),
    HEALING_POTION("Healing potion"),
    STONE_SKIN_POTION("Stone skin potion"),
    MIGHT_POTION("Potion of might"),
    // Labels:
    HEALTH_LABEL("Health: "),
    ATTACK_LABEL("Attack: "),
    DEFENSE_LABEL("Defense: "),
    ACTION_LABEL("Action: "),
    INVENTORY_LABEL("Inventory: "),
    // Inventory content
    INVENTORY_EMPTY("Empty");

    public final String message;

    StringFactory(String customString) {
        this.message = customString;
    }
}
