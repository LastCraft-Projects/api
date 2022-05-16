package net.lastcraft.packetlib.libraries.hologram.lines;

import net.lastcraft.api.depend.CraftVector;
import net.lastcraft.api.entity.EntityEquip;
import net.lastcraft.api.hologram.lines.ItemFloatingLine;
import net.lastcraft.packetlib.libraries.hologram.CraftHologram;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class CraftItemFloatingLine extends CraftHoloLine implements ItemFloatingLine {

    private ItemStack item;
    private float yaw = 0.0f;
    private boolean rotate;

    public CraftItemFloatingLine(CraftHologram hologram, boolean rotate, Location location, ItemStack item) {
        super(hologram, location.clone());
        customStand.setSmall(false);
        this.rotate = rotate;
        setItem(item);
    }

    @Override
    public boolean isRotate() {
        return rotate;
    }

    @Override
    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public ItemStack getItem(){
        return item.clone();
    }

    @Override
    public void setItem(ItemStack item){
        this.item = item;

        EntityEquip entityEquip = customStand.getEntityEquip();
        if (item.getType().isBlock()){
            entityEquip.setHelmet(item);
            return;
        }

        switch (item.getType()){
            case BOW:
            case DIAMOND_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case STONE_AXE:
            case WOOD_AXE:
            case IRON_SWORD:
            case GOLD_SWORD:
            case STONE_SWORD:
            case WOOD_SWORD:
            case DIAMOND_SWORD:
            case IRON_PICKAXE:
            case WOOD_PICKAXE:
            case DIAMOND_PICKAXE:
            case GOLD_PICKAXE:
            case STONE_PICKAXE:
            case IRON_SPADE:
            case STONE_SPADE:
            case DIAMOND_SPADE:
            case WOOD_SPADE:
            case GOLD_SPADE:
            case WOOD_HOE:
            case GOLD_HOE:
            case IRON_HOE:
            case DIAMOND_HOE:
            case STONE_HOE:
            case STICK:
            case BLAZE_ROD:
            case BONE:
            case FISHING_ROD:
            case CARROT_STICK:
                customStand.setRightArmPose(new CraftVector(-135, -90, 0));
                entityEquip.setItemInMainHand(item);
                break;
            case SKULL_ITEM:
            case LEATHER_HELMET:
            case GOLD_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
                entityEquip.setHelmet(item);
                break;
            case LEATHER_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                entityEquip.setChestplate(item);
                break;
            case LEATHER_LEGGINGS:
            case GOLD_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
                entityEquip.setLeggings(item);
                break;
            case LEATHER_BOOTS:
            case GOLD_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
                entityEquip.setBoots(item);
                break;
        }
    }

    public void update(){
        if (!rotate)
            return;

        if (yaw >= 360){
            yaw = 0.0f;
        } else {
            customStand.setLook(yaw, 0.0f);
            yaw += 4.0f;
        }
    }

    public static double getItemFloatingEnter(ItemStack item){
        if (item.getType().isBlock())
            return 0.35;

        double enter = 0;
        switch (item.getType()) {
            case BOW:
                enter = 0.45;
                break;
            case SKULL_ITEM:
            case LEATHER_HELMET:
            case GOLD_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case DIAMOND_HELMET:
                enter = 0.35;
                break;
            case LEATHER_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
                enter = 0.85;
                break;
            case LEATHER_LEGGINGS:
            case GOLD_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case DIAMOND_LEGGINGS:
                enter = 1.4;
                break;
            case LEATHER_BOOTS:
            case GOLD_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case DIAMOND_BOOTS:
                enter = 1.85;
                break;
        }
        return enter;
    }
}
