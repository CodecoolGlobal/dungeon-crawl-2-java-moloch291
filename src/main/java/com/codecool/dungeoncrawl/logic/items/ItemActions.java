package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.map.GameMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Map;

public class ItemActions {

    private boolean searchForItemByName(GameMap map, String nameCheck) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        for (Item item : playerInventory.keySet()) {
            return item.getName().equals(nameCheck);
        }
        return false;
    }
    
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
        if (itemName.equals("Healing potion")) {
            map.getPlayer().setHealth(playerHealth + PotionType.HEALING_POTION.effectValue);
        } else if (itemName.equals("Stone skin potion")) {
            map.getPlayer().setDefense(playerDefense + PotionType.STONE_SKIN_POTION.effectValue);
        } else if (itemName.equals("Potion of might")) {
            map.getPlayer().setAttack(playerAttack + PotionType.MIGHT_POTION.effectValue);
        }
        decrementItem(map, itemName);
    }

    private void decrementItem(GameMap map, String itemName) {
        Map<Item, Integer> playerInventory = map.getPlayer().getInventory();
        int count = 0;
        Item inInventory = null;
        for (Item item : playerInventory.keySet()) {
            if (item.getName().equals(itemName)) {
                inInventory = item;
                count = playerInventory.get(item);
            }
        }
        if (count > 1) {
            map.getPlayer().addToInventory(inInventory, playerInventory.get(inInventory) - 1);
        } else {
            map.getPlayer().removeFromInventory(inInventory);
        }
    }

    public void equipArmor(GameMap map, String itemName) {
        int playerDefense = map.getPlayer().getDefense();
        if (itemName.equals("Shield")) {
            map.getPlayer().setDefense(playerDefense + ArmorType.SHIELD.defenseValue);
        } else if (itemName.equals("Helmet")) {
            map.getPlayer().setDefense(playerDefense + ArmorType.HELMET.defenseValue);
        } else if (itemName.equals("Breastplate")) {
            map.getPlayer().setDefense(playerDefense + ArmorType.BREASTPLATE.defenseValue);
        } else if (itemName.equals("Greaves")) {
            map.getPlayer().setDefense(playerDefense + ArmorType.GREAVES.defenseValue);
        } else if (itemName.equals("Gauntlets")) {
            map.getPlayer().setDefense(playerDefense + ArmorType.GAUNTLETS.defenseValue);
        }
    }

    public void equipWeapon(GameMap map, String itemName) {
        int playerAttack = map.getPlayer().getAttack();
        if (itemName.equals("Sword")) {
            map.getPlayer().setAttack(playerAttack + WeaponType.SWORD.attackValue);
        } else if (itemName.equals("Axe")) {
            map.getPlayer().setAttack(playerAttack + WeaponType.AXE.attackValue);
        } else if (itemName.equals("Pike")) {
            map.getPlayer().setAttack(playerAttack + WeaponType.PIKE.attackValue);
        }
    }

}
