package com.codecool.dungeoncrawl.logic.util;

public enum StringFactory {

    // Item types:
    KEY("key"),
    WEAPON("weapon"),
    ARMOR("armor"),
    FOOD("food"),
    POTION("potion"),

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
    INVENTORY_EMPTY("Empty"),
    AXE("axe"),
    AXE_CAP("Axe"),
    SWORD("sword"),
    SWORD_CAP("Sword"),
    PIKE("pike"),
    PIKE_CAP("Pike"),
    SHIELD("shield"),
    SHIELD_CAP("Shield"),
    HELMET("helmet"),
    HELMET_CAP("Helmet"),
    BREASTPLATE("breastplate"),
    BREASTPL_CAP("Breastplate"),
    GREAVES("greaves"),
    GREAVES_CAP("Greaves"),
    GAUNTLETS("gauntlets"),
    GAUNTLETS_CAP("Gauntlets"),
    BREAD("bread"),
    WATER("water"),
    CHEESE("cheese"),
    APPLE("apple"),
    FISH("fish"),
    BOAT("boat"),
    BOAT_CAP("Boat"),
    WATER_ITEM("water item"),
    BEER("beer"),
    BEER_CAP("Beer");

    public final String message;

    StringFactory(String customString) {
        this.message = customString;
    }
}
