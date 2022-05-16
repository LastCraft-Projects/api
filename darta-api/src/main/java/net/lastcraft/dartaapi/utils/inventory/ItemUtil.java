package net.lastcraft.dartaapi.utils.inventory;

import net.lastcraft.api.util.Head;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.utils.DItemStack;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Deprecated
public class ItemUtil {
    public static boolean isHelment(Material material) {
        return material == Material.LEATHER_HELMET || material == Material.IRON_HELMET || material == Material.GOLD_HELMET || material == Material.CHAINMAIL_HELMET || material == Material.DIAMOND_HELMET || material == Material.SKULL_ITEM;
    }

    public static boolean isSword(Material material) {
        return material == Material.WOOD_SWORD || material == Material.STONE_SWORD || material == Material.IRON_SWORD || material == Material.DIAMOND_SWORD;
    }

    public static boolean isChestplate(Material material) {
        return material == Material.LEATHER_CHESTPLATE || material == Material.IRON_CHESTPLATE || material == Material.GOLD_CHESTPLATE || material == Material.CHAINMAIL_CHESTPLATE || material == Material.DIAMOND_CHESTPLATE;
    }

    public static boolean isLeggings(Material material) {
        return material == Material.LEATHER_LEGGINGS || material == Material.IRON_LEGGINGS || material == Material.GOLD_LEGGINGS || material == Material.CHAINMAIL_LEGGINGS || material == Material.DIAMOND_LEGGINGS;
    }

    public static boolean isBoots(Material material) {
        return material == Material.LEATHER_BOOTS || material == Material.IRON_BOOTS || material == Material.GOLD_BOOTS || material == Material.CHAINMAIL_BOOTS || material == Material.DIAMOND_BOOTS;
    }

    public static boolean isLeatherArmor(Material material){
        return material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS;
    }

    public static ItemStack removeFlags(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static short getPotionType(PotionType pt, int level, boolean extended_duration, boolean splash) {
        Potion pot = new Potion(pt);
        pot.setLevel(level);
        pot.setSplash(splash);
        if (extended_duration) {
            pot.setHasExtendedDuration(extended_duration);
        }

        return pot.toItemStack(1).getDurability();
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, String name, List<String> text) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(text);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, String name, int amount) {
        ItemStack item = createItemStack(material, name);
        item.setAmount(amount);
        return item;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, String name, int amount, List<String> text) {
        ItemStack item = createItemStack(material, name, text);
        item.setAmount(amount);
        return item;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, short subID, String name) {
        ItemStack itemStack = new ItemStack(material, 1, subID);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, short subID, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, 1, subID);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Deprecated
    public static ItemStack createItemStack(Material material, String name, Enchantment enchantment) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(enchantment, 1);

        return itemStack;
    }

    @Deprecated
    public static ItemStack getColorLeatherArmor(Material material, Color color) {
        ItemStack armor = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) armor.getItemMeta();
        lam.setColor(color);
        armor.setItemMeta(lam);

        return armor;
    }

    @Deprecated
    public static ItemStack setItemMeta(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Deprecated
    public static ItemStack setItemMeta(ItemStack item, String name, List<String> text) {
        ItemStack itemStack = item.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(text);
        for (ItemFlag itemFlag : ItemFlag.values()) {
            itemMeta.addItemFlags(itemFlag);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Deprecated
    public static ItemStack setItemMeta(ItemStack itemStack, String name, List<String> text, int amount) {
        itemStack.setAmount(amount);
        return setItemMeta(itemStack, name, text);
    }

    @Deprecated
    public static List<ItemStack> getStringItems(String string) {
        Material material;
        short subId = 0;
        List<ItemStack> items = new ArrayList<>();

        //    0            1             2                 3
        //id:subid amount(диапазон) паломанность DAMAGE_ALL:0-2(если 0, то может не наложится)
        String[] itemSplit = string.split(" ");
        int length = itemSplit.length;

        if (itemSplit[0].contains(":")) {
            String[] splitMaterial = itemSplit[0].split(":");
            material = Material.getMaterial(Integer.parseInt(splitMaterial[0]));
            subId = Short.parseShort(splitMaterial[1]);

            items.add(new ItemStack(material, 1, subId));
        } else {
            if (itemSplit[0].equals("Compass")){
                return Collections.singletonList(ItemsListener.getCompass());
            } else {
                material = Material.getMaterial(Integer.parseInt(itemSplit[0]));
                items.add(new ItemStack(material));
            }
        }

        if (length >= 2) {
            items.clear();
            if (itemSplit[1].contains("-")) {
                String[] splitAmount = itemSplit[1].split("-");
                int amount = Integer.parseInt(splitAmount[0]);
                int max = Integer.parseInt(splitAmount[1]);
                while (amount <= max) {
                    items.add(new ItemStack(material, amount, subId));
                    amount++;
                }
            } else {
                int amount = Integer.parseInt(itemSplit[1]);
                items.add(new ItemStack(material, amount, subId));
            }
        }

        if (length >= 3) {
            List<ItemStack> copyItems = new ArrayList<>(items);
            items.clear();
            if (itemSplit[2].contains("-")) {
                String[] splitDurability = itemSplit[2].split("-");
                int durability = Integer.parseInt(splitDurability[0]);
                int maxDurability = Integer.parseInt(splitDurability[1]);
                for (ItemStack copy : copyItems) {
                    int copyDurability = durability;
                    while (copyDurability <= maxDurability) {
                        copy.setDurability((short) (material.getMaxDurability() - (short) copyDurability));
                        items.add(copy);
                        copyDurability++;
                    }
                }
            } else {
                short durability = Short.parseShort(itemSplit[2]);
                for (ItemStack copy : copyItems) {
                    if (durability > 0) {
                        copy.setDurability((short) (material.getMaxDurability() - durability));
                    }
                    items.add(copy);
                }
            }
        }

        if (length >= 4) {
            for (int enchantNumber = 3; enchantNumber < length; enchantNumber++) {
                String[] splitEnchant = itemSplit[enchantNumber].split(":");
                Enchantment enchantment = Enchantment.getByName(splitEnchant[0]);

                if (splitEnchant[1].contains("-")) {
                    String[] splitLevel = splitEnchant[1].split("-");
                    int minLevel = Integer.parseInt(splitLevel[0]);
                    int maxLevel = Integer.parseInt(splitLevel[1]);

                    List<ItemStack> copyItems = new ArrayList<>(items);
                    items.clear();

                    for (ItemStack copy : copyItems) {
                        int level = minLevel;
                        while (level <= maxLevel) {
                            ItemStack itemCopy = copy.clone();
                            if (level != 0) {
                                itemCopy.addUnsafeEnchantment(enchantment, level);
                            }
                            items.add(itemCopy);
                            level++;
                        }
                    }
                } else {
                    List<ItemStack> copyItems = new ArrayList<>(items);
                    items.clear();
                    int level = Integer.parseInt(splitEnchant[1]);
                    for (ItemStack copy : copyItems) {
                        copy.addUnsafeEnchantment(enchantment, level);
                        items.add(copy);
                    }
                }
            }

        }

        return items;
    }

    @Deprecated
    public static ItemStack stringToItemOld(String item) {
        ItemStack itemStack;
        //    0            1             2                 3
        //id:subid amount(диапазон) паломанность DAMAGE_ALL:0-2(если 0, то может не наложится)
        String[] itemSplit = item.split(" ");
        int length = itemSplit.length;

        Random random = new Random();

        if (itemSplit[0].contains(":")) {
            String[] splitMaterial = itemSplit[0].split(":");
            itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(splitMaterial[0])), 1, Short.parseShort(splitMaterial[1]));
        } else {
            if (itemSplit[0].equals("Compass")){
                itemStack = ItemsListener.getCompass();
            } else {
                itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(itemSplit[0])));
            }
        }

        if (length >= 2) {
            if (itemSplit[1].contains("-")) {
                String[] splitAmount = itemSplit[1].split("-");
                int amount = random.nextInt(Integer.parseInt(splitAmount[1]) - Integer.parseInt(splitAmount[0])) + Integer.parseInt(splitAmount[0]);
                itemStack.setAmount(amount);
            } else {
                itemStack.setAmount(Integer.parseInt(itemSplit[1]));
            }
        }

        if (length >= 3) {
            if (itemSplit[2].contains("-")) {
                String[] splitDurability = itemSplit[2].split("-");
                int durability = random.nextInt(Integer.parseInt(splitDurability[1]) - Integer.parseInt(splitDurability[0])) + Integer.parseInt(splitDurability[0]);
                itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - (short) durability));
            } else {
                if (Short.parseShort(itemSplit[2]) > 0) {
                    itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - Short.parseShort(itemSplit[2])));
                }
            }
        }

        if (length >= 4) {
            int enchantRandom = random.nextInt(length - 3) + 3;
            String[] splitEnchant = itemSplit[enchantRandom].split(":");
            Enchantment enchantment = Enchantment.getByName(splitEnchant[0]);
            if (splitEnchant[1].contains("-")) {
                String[] splitLevel = splitEnchant[1].split("-");
                boolean chance = true;
                if (Integer.parseInt(splitLevel[0]) == 0) {
                    chance = (random.nextInt(5) < 2);
                }
                int level = random.nextInt(Integer.parseInt(splitLevel[1]) - Integer.parseInt(splitLevel[0])) + Integer.parseInt(splitLevel[0]);
                if (level != 0 && chance) itemStack.addUnsafeEnchantment(enchantment, level);
            } else {
                itemStack.addUnsafeEnchantment(enchantment, Integer.parseInt(splitEnchant[1]));
            }
        }

        return itemStack;
    }

    public static ItemStack stringToItem(String item) {
        String[] itemSplit = item.split(" ");

        ItemStack itemStack;

        if (itemSplit[0].contains(":")) {
            if (itemSplit[0].startsWith("Head")) {
                if (itemSplit[0].split(":")[1].length() > 16){
                    itemStack = Head.getHeadByValue(itemSplit[0].split(":")[1]);
                } else {
                    itemStack = Head.getHeadByPlayerName(itemSplit[0].split(":")[1]);
                }
            } else if (itemSplit[0].startsWith("LEATHER_HELMET") || itemSplit[0].startsWith("LEATHER_CHESTPLATE") || itemSplit[0].startsWith("LEATHER_LEGGINGS") || itemSplit[0].startsWith("LEATHER_BOOTS")) {
                itemStack = new DItemStack(Material.getMaterial(itemSplit[0].split(":")[0]), Color.fromRGB(Integer.parseInt(itemSplit[0].split(":")[1].split(";")[0]), Integer.parseInt(itemSplit[0].split(":")[1].split(";")[1]), Integer.parseInt(itemSplit[0].split(":")[1].split(";")[2])), false);
            } else {
                itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(itemSplit[0].split(":")[0])), 1, Short.parseShort(itemSplit[0].split(":")[1]));
            }
        } else {
            if (itemSplit[0].equals("Compass")){
                itemStack = ItemsListener.getCompass();
            } else {
                itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(itemSplit[0])));
            }
        }

        if (itemSplit.length >= 2){
            itemStack.setAmount(Integer.parseInt(itemSplit[1]));
        }

        if (itemSplit.length >= 3){
            if (itemSplit[2].contains(";")) {
                String[] enchants = itemSplit[2].split(";");
                for (String enchant : enchants) {
                    if (enchant.contains(":")) {
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(enchant.split(":")[0]), Integer.parseInt(enchant.split(":")[1]));
                    } else {
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(enchant), 1);
                    }
                }
            } else {
                if (itemSplit[2].contains(":")) {
                    itemStack.addUnsafeEnchantment(Enchantment.getByName(itemSplit[2].split(":")[0]), Integer.parseInt(itemSplit[2].split(":")[1]));
                } else {
                    itemStack.addUnsafeEnchantment(Enchantment.getByName(itemSplit[2]), 1);
                }
            }
        }

        return itemStack;
    }

    public static boolean compareItems(ItemStack itemInHand, ItemStack customItem) {
        if (itemInHand != null && customItem != null) {
            if (itemInHand.getType() == customItem.getType()) {
                if (itemInHand.getItemMeta().getDisplayName() != null) {
                    return itemInHand.getItemMeta().getDisplayName().equals(customItem.getItemMeta().getDisplayName());
                }
            }
        }
        return false;
    }

    public static boolean thisItem(ItemStack item, Material material, String displayName) {
        if (item.getType() == material) {
            if (item.getItemMeta().getDisplayName() != null) {
                if (item.getItemMeta().getDisplayName().equals(displayName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack addEnchantment(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return itemStack;
    }

    public static ItemStack removeEnchantment(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Enchantment enchantment : itemMeta.getEnchants().keySet())
            itemMeta.removeEnchant(enchantment);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
