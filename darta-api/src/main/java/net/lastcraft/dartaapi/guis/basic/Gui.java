package net.lastcraft.dartaapi.guis.basic;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;

public abstract class Gui {
    protected static final InventoryAPI INVENTORY_API = LastCraft.getInventoryAPI();
    protected static final SoundAPI SOUND_API = LastCraft.getSoundAPI();
    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    protected final GuiDefaultContainer listener;
    protected final DInventory dInventory;
    protected final Language lang;

    protected Gui(GuiDefaultContainer listener, Language lang, String name) {
        this.listener = listener;
        this.lang = lang;
        dInventory = INVENTORY_API.createInventory(name, 5);
        this.setItems();
    }

    public DInventory getInventory() {
        return dInventory;
    }

    protected abstract void setItems();
}
