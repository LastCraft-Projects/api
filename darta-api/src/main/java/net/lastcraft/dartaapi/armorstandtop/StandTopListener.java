package net.lastcraft.dartaapi.armorstandtop;

import net.lastcraft.api.event.gamer.GamerChangeLanguageEvent;
import net.lastcraft.api.event.gamer.GamerChangePrefixEvent;
import net.lastcraft.api.event.gamer.GamerChangeSkinEvent;
import net.lastcraft.api.event.gamer.GamerInteractHologramEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class StandTopListener extends DListener<JavaPlugin> {

    private final TopManager manager;

    StandTopListener(TopManager standTopManager) {
        super(standTopManager.getJavaPlugin());

        this.manager = standTopManager;
    }

    @EventHandler
    public void onJoinAsync(AsyncGamerJoinEvent e) {
        if (manager.size() < 1) {
            return;
        }

        manager.getStandPlayerStorage().addPlayer(new StandPlayer(manager, e.getGamer()));
    }

    @EventHandler
    public void onRemove(AsyncGamerQuitEvent e) {
        manager.getStandPlayerStorage().removePlayer(e.getGamer().getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(GamerInteractHologramEvent e) {
        Hologram hologram = e.getHologram();
        BukkitGamer gamer = e.getGamer();

        if (manager.size() <= 1) {
            return;
        }

        StandPlayer standPlayer = manager.getStandPlayerStorage().getPlayer(gamer);
        if (standPlayer == null) {
            return;
        }

        Top topType = standPlayer.getTopType();
        Hologram mainHologram = topType.getHologramMiddle(gamer.getLanguage());
        if (mainHologram == null || mainHologram != hologram) {
            return;
        }

        standPlayer.changeSelected();
        gamer.playSound(SoundType.CLICK);
    }

    @EventHandler
    public void onChangeLang(GamerChangeLanguageEvent e) {
        Language old = e.getOldLanguage();
        Language lang = e.getLanguage();
        BukkitGamer gamer = e.getGamer();

        StandPlayer standPlayer = manager.getStandPlayerStorage().getPlayer(gamer);
        if (standPlayer == null) {
            return;
        }

        standPlayer.changeHolo(old, lang);

        gamer.playSound(SoundType.CLICK);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChangeSkin(GamerChangeSkinEvent e) {
        changeStands(e.getGamer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChangePrefix(GamerChangePrefixEvent e) {
        changeStands(e.getGamer());
    }

    private void changeStands(BukkitGamer gamer) {
        BukkitUtil.runTaskAsync(() -> manager.getAllStands().stream()
                .filter(standTop -> standTop.getStandTopData() != null)
                .filter(standTop -> standTop.getStandTopData().getPlayerID() == gamer.getPlayerID())
                .forEach(standTop -> standTop.updateSkinAndPrefix(gamer)));
    }

}
