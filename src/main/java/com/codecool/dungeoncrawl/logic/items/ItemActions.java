package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.map.Cell;
import com.codecool.dungeoncrawl.logic.map.CellType;
import com.codecool.dungeoncrawl.logic.map.GameMap;
import com.codecool.dungeoncrawl.logic.util.StringFactory;

import java.util.Map;

public class ItemActions {

    public Item searchForItemByType(GameMap map, ItemType itemType) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        Item itemInInventory = null;
        for (Item item : playerInventory.keySet()) {
            if (item.getItemType().equals(itemType)) {
                itemInInventory = item;
            }
        }
        return itemInInventory;
    }

    public void consumeFood(GameMap map, String itemName) {
        int playerHealth = map.getPlayer().getHealth();
        int replenishHealth = 5;
        map.getPlayer().setHealth(playerHealth + replenishHealth);
        decrementItem(map, itemName);
    }

    public void consumePotion(GameMap map, String itemName) {
        int playerHealth = map.getPlayer().getHealth();
        int playerDefense = map.getPlayer().getDefense();
        int playerAttack = map.getPlayer().getAttack();
        if (itemName.equals(StringFactory.HEALING_POTION.message)) {
            map.getPlayer().setHealth(playerHealth + PotionType.HEALING_POTION.effectValue);
        } else if (itemName.equals(StringFactory.STONE_SKIN_POTION.message)) {
            map.getPlayer().setDefense(playerDefense + PotionType.STONE_SKIN_POTION.effectValue);
        } else if (itemName.equals(StringFactory.MIGHT_POTION.message)) {
            map.getPlayer().setAttack(playerAttack + PotionType.MIGHT_POTION.effectValue);
        }
        decrementItem(map, itemName);
    }

    public void consumeAlcohol(GameMap map, String itemName) {
        int defenseModifier = -5;
        int attackModifier = 3;
        map.getPlayer().setDrunk(true);
        if (map.getPlayer().getDefense() > 0) {
            map.getPlayer().setDefense(map.getPlayer().getDefense() + defenseModifier);
        }
        map.getPlayer().setAttack(map.getPlayer().getAttack() + attackModifier);
        decrementItem(map, itemName);
    }

    private void decrementItem(GameMap map, String itemName) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        int count = 0;
        Item itemFromInventory = null;
        for (Item item : playerInventory.keySet()) {
            if (item.getName().equals(itemName)) {
                itemFromInventory = item;
                count = playerInventory.get(item);
            }
        }
        handleInventory(map, playerInventory, count, itemFromInventory);
    }

    private void handleInventory(GameMap map, Map<Item, Integer> playerInventory, int count, Item itemFromInventory) {
        if (count > 1) {
            map.getPlayer().addToInventory(itemFromInventory, playerInventory.get(itemFromInventory) - 1);
        } else {
            map.getPlayer().removeFromInventory(itemFromInventory);
        }
    }

    public void leaveBoat(Player player) {
        Item boat = null;
        for (Item item : player.getInventory().keySet()) {
            if (item.getItemType().equals(ItemType.BOAT)) {
                boat = item;
            }
        }
        player.removeFromInventory(boat);
        Cell playerCell = player.getCell();
        playerCell.setType(CellType.WATER);
        new Boat(StringFactory.BOAT_CAP.message, playerCell, ItemType.BOAT);
    }

    public void equipArmor(GameMap map, String itemName) {
        int playerDefense = map.getPlayer().getDefense();
        if (itemName.equals(StringFactory.SHIELD_CAP.message)) {
            map.getPlayer().setDefense(playerDefense + ArmorType.SHIELD.defenseValue);
        } else if (itemName.equals(StringFactory.HELMET_CAP.message)) {
            map.getPlayer().setDefense(playerDefense + ArmorType.HELMET.defenseValue);
        } else if (itemName.equals(StringFactory.BREASTPLATE_CAP.message)) {
            map.getPlayer().setDefense(playerDefense + ArmorType.BREASTPLATE.defenseValue);
        } else if (itemName.equals(StringFactory.GREAVES_CAP.message)) {
            map.getPlayer().setDefense(playerDefense + ArmorType.GREAVES.defenseValue);
        } else if (itemName.equals(StringFactory.GAUNTLETS_CAP.message)) {
            map.getPlayer().setDefense(playerDefense + ArmorType.GAUNTLETS.defenseValue);
        }
    }

    public void equipWeapon(GameMap map, String itemName) {
        int playerAttack = map.getPlayer().getAttack();
        if (itemName.equals(StringFactory.SWORD_CAP.message)) {
            map.getPlayer().setAttack(playerAttack + WeaponType.SWORD.attackValue);
        } else if (itemName.equals(StringFactory.AXE_CAP.message)) {
            map.getPlayer().setAttack(playerAttack + WeaponType.AXE.attackValue);
        } else if (itemName.equals(StringFactory.PIKE_CAP.message)) {
            map.getPlayer().setAttack(playerAttack + WeaponType.PIKE.attackValue);
        }
    }

}
