package net.lastcraft.dartaapi.armorstandtop;

import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.depend.CraftVector;
import net.lastcraft.api.entity.EntityAPI;
import net.lastcraft.api.entity.EntityEquip;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.hologram.HologramAPI;
import net.lastcraft.api.hologram.lines.TextHoloLine;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.locale.Language;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class StandTop {

    private static final HologramAPI HOLOGRAM_API = LastCraft.getHologramAPI();
    private static final EntityAPI ENTITY_API = LastCraft.getEntityAPI();

    private final int position;
    private final Top topType;
    private final CustomStand customStand;
    private final Map<Language, Hologram> holograms = new ConcurrentHashMap<>();

    private final Location location;

    private StandTopData standTopData;

    public StandTop(Top topType, Location location, int position) {
        this.position = position;
        this.topType = topType;
        this.location = location;

        this.customStand = ENTITY_API.createStand(location);
        this.customStand.setArms(true);
        this.customStand.setSmall(true);
        this.customStand.setBasePlate(false);

        setEquipment();

        for (Language language : Language.values()) {
            Hologram hologram = HOLOGRAM_API.createHologram(location.clone().add(0.0, 1.2, 0.0));

            hologram.addTextLine(language.getMessage("TOP_POSITION", String.valueOf(position)));
            hologram.addTextLine("§cN/A");
            hologram.addTextLine("§cДанные отсутствуют");

            holograms.put(language, hologram);
        }
    }

    public void setStandTopData(StandTopData standTopData) {
        this.standTopData = standTopData;
        update();
    }

    /**
     * обновить информацию которая сейчас на стендах
     * standTopData - тут хранится голова и кол-во того, что стенд отображает
     */
    public void update() {
        if (standTopData == null) {
            return;
        }

        for (Map.Entry<Language, Hologram> entry : holograms.entrySet()) {
            Hologram hologram = entry.getValue();
            Language language = entry.getKey();

            TextHoloLine holoLine1 = hologram.getHoloLine(1);
            TextHoloLine holoLine2 = hologram.getHoloLine(2);

            holoLine1.setText(standTopData.getDisplayName());
            holoLine2.setText(standTopData.getLastString(language));
        }

        customStand.getEntityEquip().setHelmet(standTopData.getHead());
    }

    void updateSkinAndPrefix(BukkitGamer gamer) {
        if (standTopData == null) {
            return;
        }

        standTopData.setGamer(gamer);
        update();
    }

    public Location getLocation() {
        return location.clone();
    }

    private void setEquipment() {
        Color color;
        ItemStack itemInHand;

        switch (position) {
            case 1:
                color = Color.fromRGB(23, 164, 79);
                itemInHand = new ItemStack(Material.EMERALD);
                customStand.setLeftArmPose(new CraftVector(-20,0,-120));
                customStand.setRightArmPose(new CraftVector(-40,50,90));
                customStand.setRightLegPose(new CraftVector(-10,70,40));
                customStand.setLeftLegPose(new CraftVector(-10,-60,-40));
                customStand.setHeadPose(new CraftVector(15,0,0));
                break;
            case 2:
                color = Color.fromRGB(46, 210, 185);
                itemInHand = new ItemStack(Material.DIAMOND);
                customStand.setLeftArmPose(new CraftVector(-20,0,-140));
                customStand.setRightArmPose(new CraftVector(-50,20,10));
                customStand.setRightLegPose(new CraftVector(-5,-10,10));
                customStand.setLeftLegPose(new CraftVector(-10,-10,-6));
                customStand.setHeadPose(new CraftVector(5,0,5));
                break;
            case 3:
                color = Color.fromRGB(179, 132, 16);
                itemInHand = new ItemStack(Material.GOLD_INGOT);
                customStand.setLeftArmPose(new CraftVector(50,15,-7));
                customStand.setRightArmPose(new CraftVector(-50,10,5));
                customStand.setRightLegPose(new CraftVector(-20,0,5));
                customStand.setLeftLegPose(new CraftVector(20,0,-5));
                customStand.setHeadPose(new CraftVector(0,0,2));
                break;
            case 4:
                color = Color.fromRGB(190, 190, 194);
                itemInHand = new ItemStack(Material.IRON_INGOT);
                customStand.setLeftArmPose(new CraftVector(-10,0,-60));
                customStand.setRightArmPose(new CraftVector(-10,0,130));
                customStand.setRightLegPose(new CraftVector(0,0,60));
                customStand.setHeadPose(new CraftVector(0,0,10));
                break;
            case 5:
                color = Color.fromRGB(176, 86, 63);
                itemInHand = new ItemStack(Material.CLAY_BRICK);
                customStand.setLeftArmPose(new CraftVector(-90,-20,-30));
                customStand.setRightArmPose(new CraftVector(50,30,90));
                customStand.setRightLegPose(new CraftVector(50,0,15));
                customStand.setLeftLegPose(new CraftVector(-7,-6,-5));
                customStand.setHeadPose(new CraftVector(30,10,10));
                customStand.setBodyPose(new CraftVector(6,6,0));
                break;
            default:
                color = Color.fromRGB(0,0,0);
                itemInHand = new ItemStack(Material.BARRIER);
        }

        EntityEquip equip = customStand.getEntityEquip();

        equip.setChestplate(ItemUtil.getBuilder(Material.LEATHER_CHESTPLATE)
                .setColor(color)
                .build());
        equip.setLeggings(ItemUtil.getBuilder(Material.LEATHER_LEGGINGS)
                .setColor(color)
                .build());
        equip.setBoots(ItemUtil.getBuilder(Material.LEATHER_BOOTS)
                .setColor(color)
                .build());

        equip.setHelmet(ItemUtil.getBuilder(Material.SKULL_ITEM)
                .setDurability((short) 3)
                .build());
        equip.setItemInMainHand(itemInHand);
    }

    public void removeTo(BukkitGamer gamer, Language language, boolean hideStand) {
        if (gamer == null) {
            return;
        }

        Hologram hologram = holograms.get(language);
        if (hologram != null) {
            hologram.removeTo(gamer);
        } else {
            holograms.get(Language.getDefault()).removeTo(gamer);
        }

        if (hideStand) {
            customStand.removeTo(gamer);
        }
    }

    public void showTo(BukkitGamer gamer, Language language, boolean showStand) {
        if (gamer == null) {
            return;
        }

        Hologram hologram = holograms.get(language);
        if (hologram != null) {
            hologram.showTo(gamer);
        } else {
            holograms.get(Language.getDefault()).showTo(gamer);
        }

        if (showStand) {
            customStand.showTo(gamer);
        }
    }
}
