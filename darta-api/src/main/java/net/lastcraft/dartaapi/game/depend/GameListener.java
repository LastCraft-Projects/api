package net.lastcraft.dartaapi.game.depend;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

@Deprecated
public class GameListener extends DListener {

    private final GamerManager gamerManager = LastCraft.getGamerManager();
    private final NmsManager nmsManager = NmsAPI.getManager();
    //тут выдавать монеты и тд, когда игрок ливает во время игры
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer == null)
            return;

        int keysLocal = gamer.getKeysLocal();
        int moneyLocal = gamer.getMoneyLocal();
        int expLocal = gamer.getExpLocal();

        if (moneyLocal >= 1) {
            gamer.changeMoney(PurchaseType.MYSTERY_DUST, (int) (moneyLocal * gamer.getMultiple()));
            gamer.setMoneyLocal();
        }
        if (expLocal >= 1) {
            gamer.addExp(expLocal);
            gamer.setExpLocal();
        }
        if (keysLocal >= 1) {
            //gamer.changeKeys(KeyType.DEFAULT_KEY, keysLocal); больше не даем ключи
            gamer.setKeysLocal();
        }
        if (!PlayerUtil.isSpectator(player)) {
            for (BukkitGamer all : gamerManager.getGamers().values()) {
                all.sendMessageLocale("PLAYER_LEFT", gamer.getChatName());
            }
            if (!GameSettings.teamMode) {
                GAMER_MANAGER.removeGamer(player);
            }

        }
    }

    //запрещаем наносить урон тиммейтам через зельки
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPotionThrow(PotionSplashEvent e) {
        if (!GameSettings.teamMode) return;

        ThrownPotion potion = e.getEntity();
        if (!(potion.getShooter() instanceof Player)) return;

        Player player = (Player) potion.getShooter();

        TeamManager team = SelectionTeam.getSelectedTeams().get(player);
        for (Player teamPlayer : SelectionTeam.getPlayersByTeam(team)) {
            if (player == teamPlayer) continue;
            if (!e.getAffectedEntities().contains(teamPlayer)) continue;
            for (PotionEffect potionEffect : potion.getEffects()) {
                if (potionEffect.getType().getName().equals("POISON")) {
                    e.setIntensity(teamPlayer, 0L);
                }
            }
        }
    }

    //запрещаем ломать блоки под тиммейтами
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!GameSettings.teamMode)
            return;

        Block block = e.getBlock();
        if (block.getType() == Material.BED_BLOCK
                || block.getType() == Material.BED
                || block.getType() == Material.DRAGON_EGG
                || block.getType() == Material.CHEST
                || block.getType() == Material.TRAPPED_CHEST) {
            return;
        }

        Player player = e.getPlayer();
        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer == null) return;
        Language lang = gamer.getLanguage();

        TeamManager team = SelectionTeam.getSelectedTeams().get(player);
        for (Player teamPlayer : SelectionTeam.getPlayersByTeam(team)) {
            if (PlayerUtil.isSpectator(teamPlayer))
                continue;
            if (player == teamPlayer)
                continue;
            if (nmsManager.getBlocksBelow(teamPlayer).contains(block)) {
                e.setCancelled(true);
                player.sendMessage(GameSettings.prefix + lang.getMessage("BREAK_BLOCK_UNDER_TEAM"));
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        if (e.getItem().getType() != Material.POTION) {
            return;
        }

        BukkitUtil.runTaskLaterAsync(1, () -> {
            PlayerInventory inventory = player.getInventory();

            ItemStack itemInOffHand = inventory.getItemInOffHand();
            if (itemInOffHand != null && itemInOffHand.getType() == Material.GLASS_BOTTLE) {
                inventory.setItemInOffHand(null);
            } else {
                inventory.setItemInMainHand(null);
            }
        });
    }


    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if (GameSettings.minigame == MiniGameType.SURVIVAL) {
            return;
        }
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.setCancelled(true);
        }
    }
}
