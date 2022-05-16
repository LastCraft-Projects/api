package net.lastcraft.dartaapi.game.spectator;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

public class SpectatorSettings {
    private static SoundAPI soundAPI = LastCraft.getSoundAPI();

    private DInventory dInventory;
    private SPlayer sPlayer;
    private Language lang;

    SpectatorSettings(SPlayer sPlayer, Language lang){
        dInventory = LastCraft.getInventoryAPI().createInventory(sPlayer.getPlayer(), lang.getMessage("SPECTATOR_SETTINGS_GUI_NAME"), 4);
        this.sPlayer = sPlayer;
        this.lang = lang;
        updateInventory();
    }

    public void openInventory(){
        dInventory.openInventory(sPlayer.getPlayer());
    }

    public void updateInventory(){
        dInventory.clearInventory();

        setItemSpeed(11, Material.LEATHER_BOOTS, lang.getMessage("SPECTATOR_SETTINGS_NO_SPEED"), -1);
        setItemSpeed(12, Material.CHAINMAIL_BOOTS, lang.getMessage("SPECTATOR_SETTINGS_SPEED") + " 1", 0);
        setItemSpeed(13, Material.IRON_BOOTS, lang.getMessage( "SPECTATOR_SETTINGS_SPEED") + " 2", 1);
        setItemSpeed(14, Material.GOLD_BOOTS, lang.getMessage("SPECTATOR_SETTINGS_SPEED") + " 3", 2);
        setItemSpeed(15, Material.DIAMOND_BOOTS, lang.getMessage( "SPECTATOR_SETTINGS_SPEED") + " 4", 3);

        if (sPlayer.getAlwaysFly() == 1){
            dInventory.setItem(21, new DItem(ItemUtil.createItemStack(Material.SULPHUR, lang.getMessage("SPECTATOR_SETTINGS_FLY_OFF")),
                    (player, clickType, slot) -> {
                        sPlayer.setAlwaysFly(0);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
        } else {
            dInventory.setItem(21, new DItem(ItemUtil.createItemStack(Material.SUGAR, lang.getMessage("SPECTATOR_SETTINGS_FLY_ON")),
                    (player, clickType, slot) -> {
                        sPlayer.setAlwaysFly(1);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
        }

        if (sPlayer.getVision() == 1){
            dInventory.setItem(22, new DItem(ItemUtil.createItemStack(Material.ENDER_PEARL, lang.getMessage("SPECTATOR_SETTINGS_VISION_OFF")),
                    (player, clickType, slot) -> {
                        sPlayer.setVision(0);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
        } else {
            dInventory.setItem(22, new DItem(ItemUtil.createItemStack(Material.EYE_OF_ENDER, lang.getMessage("SPECTATOR_SETTINGS_VISION_ON")),
                    (player, clickType, slot) -> {
                        sPlayer.setVision(1);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
    }

        if (sPlayer.getHideSpectators() == 0){
            dInventory.setItem(23, new DItem(ItemUtil.createItemStack(Material.REDSTONE, lang.getMessage("SPECTATOR_SETTINGS_HIDE")),
                    (player, clickType, slot) -> {
                        sPlayer.setHideSpectators(1);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
        } else {
            dInventory.setItem(23, new DItem(ItemUtil.createItemStack(Material.GLOWSTONE_DUST, lang.getMessage("SPECTATOR_SETTINGS_SHOW")),
                    (player, clickType, slot) -> {
                        sPlayer.setHideSpectators(0);
                        updateInventory();
                        soundAPI.play(player, SoundType.POP);
                    }));
        }
    }

    private void setItemSpeed(int slot, Material material, String name, int speedItem){
        int speed = speedItem + 1;
        if (sPlayer.getSpeedSpec() == speed) {
            dInventory.setItem(slot, new DItem(ItemUtil.createItemStack(material, "§a" + name, Enchantment.PROTECTION_FALL)));
        } else {
            dInventory.setItem(slot, new DItem(ItemUtil.createItemStack(material, "§c" + name),
                    (player, clickType, slot1) -> {
                        if (speed == 0) {
                            PlayerUtil.removePotionEffect(player, PotionEffectType.SPEED);
                        }
                        else {
                            PlayerUtil.addPotionEffect(player, PotionEffectType.SPEED, speed - 1);
                        }
                        sPlayer.setSpeedSpec(speed);
                        updateInventory();
                        soundAPI.play(player, SoundType.CLICK);
                    }));
        }
    }
}
