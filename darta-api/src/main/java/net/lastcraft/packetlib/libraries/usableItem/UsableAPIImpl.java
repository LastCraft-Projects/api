package net.lastcraft.packetlib.libraries.usableItem;

import net.lastcraft.api.usableitem.ClickAction;
import net.lastcraft.api.usableitem.UsableAPI;
import net.lastcraft.api.usableitem.UsableItem;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsableAPIImpl implements UsableAPI {

    private final DartaAPI dartaAPI;

    private UsablesManager manager;

    public UsableAPIImpl(DartaAPI dartaAPI) {
        this.dartaAPI = dartaAPI;
    }

    @Override
    public UsableItem createUsableItem(ItemStack itemStack, Player owner, ClickAction clickAction) {
        return create(itemStack, owner, clickAction);
    }

    @Override
    public UsableItem createUsableItem(ItemStack itemStack, ClickAction clickAction) {
        return create(itemStack, null, clickAction);
    }

    @Override
    public void removeItem(UsableItem item) {
        item.remove();
    }

    @Override
    public List<UsableItem> getUsableItems() {
        if (manager == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(manager.getUsableItems().values());
    }


    private UsableItem create(ItemStack itemStack, Player owner, ClickAction clickAction) {
        if (manager == null) {
            manager = new UsablesManager(dartaAPI);
        }

        return new CraftUsableItem(itemStack, owner, clickAction, manager);
    }
}
