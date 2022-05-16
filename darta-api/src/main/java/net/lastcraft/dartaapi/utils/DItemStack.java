package net.lastcraft.dartaapi.utils;

import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionType;

import java.util.List;

@Deprecated
public class DItemStack extends ItemStack {

    public DItemStack(PotionType potionType, int level, boolean extended_duration, boolean splash, int amount, String name) {
        this(Material.POTION, amount, name,null, ItemUtil.getPotionType(potionType, level, extended_duration, splash));
    }

    public DItemStack(ItemStack itemStack, String name) {
        this(itemStack, name, null);
    }

    public DItemStack(ItemStack itemStack, Object... datas) {
        this(itemStack, null, null, 1, datas);
    }

    public DItemStack(Material material, Object... datas) {
        this(material, 1, (short)0, null, null, datas);
    }

    public DItemStack(ItemStack itemStack, String name, List<String> lore) {
        this(itemStack, name, lore, 1, null);
    }

    public DItemStack(ItemStack itemStack, String name, List<String> lore, int amount, Object... datas) {
        super(itemStack);
        this.setAmount(amount);
        addAtributes(name, lore);
        createDatas(datas);
    }

    public DItemStack(Material material, short subID) {
        this(material, subID, null);
    }

    public DItemStack(Material material, String name) {
        this(material, name, null);
    }

    public DItemStack(Material material, String name, int amount) {
        this(material, (short) 0, name, amount);
    }

    public DItemStack(Material material, String name, List<String> lore) {
        this(material, (short) 0, name, lore);
    }

    public DItemStack(Material material, String name, int amount, List<String> lore) {
        this(material, amount, (short) 0, name, lore, null);
    }

    public DItemStack(Material material, short subID, String name, int amount) {
        this(material, amount, subID, name, null, null);
    }

    public DItemStack(Material material, short subID, String name) {
        this(material, subID, name, null);
    }

    public DItemStack(Material material, short subID, String name, List<String> lore) {
        this(material, 1, subID, name, lore, null);
    }

    public DItemStack(Material material, int amount, short subID, String name, List<String> lore, Object... datas) {
        super(material, amount, subID);
        addAtributes(name, lore);
        createDatas(datas);
    }

    private void addAtributes(String name, List<String> lore) {
        ItemMeta im = this.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (lore != null && !lore.isEmpty()) {
            im.setLore(lore);
        }
        this.setItemMeta(im);
    }

    private void createDatas(Object... datas) {
        boolean removedFlags = true;
        if (datas != null && datas.length != 0) {
            for (int i = 0; i < datas.length; ++i) {
                Object data = datas[i];
                if (data instanceof Color) {
                    LeatherArmorMeta lam = (LeatherArmorMeta) this.getItemMeta();
                    lam.setColor((Color) data);
                    this.setItemMeta(lam);
                } else if (data instanceof Enchantment) {
                    if (datas.length > i + 1 && datas[i + 1] instanceof Integer) {
                        this.addUnsafeEnchantment((Enchantment) data, (Integer) datas[i + 1]);
                        ++i;
                    } else {
                        this.addUnsafeEnchantment((Enchantment) data, 1);
                    }
                } else if (data instanceof Integer) {
                    this.setAmount((Integer) data);
                } else if (data instanceof Short) {
                    this.setDurability((Short) data);
                } else if (data instanceof Boolean) {
                    removedFlags = (boolean) data;
                }
            }
        }

        if (removedFlags) {
            ItemUtil.removeFlags(this);
        }
    }

}
