package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.effect.ParticleAPI;
import net.lastcraft.api.event.gamer.GamerFriendEvent;
import net.lastcraft.api.event.gamer.GamerLvlUpEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.sections.FriendsSection;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.connector.bukkit.event.CoreInputPacketEvent;
import net.lastcraft.core.io.packet.bukkit.BukkitPlaySound;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class NetworkingListener extends DListener<DartaAPI> {

    private final SoundAPI soundAPI = LastCraft.getSoundAPI();
    private final ParticleAPI particleAPI = LastCraft.getParticleAPI();

    public NetworkingListener(DartaAPI dartaAPI) {
        super(dartaAPI);
    }

    @EventHandler
    public void onCoreSound(CoreInputPacketEvent event) {
        if(event.getPacket() instanceof BukkitPlaySound) {
            BukkitPlaySound packet = (BukkitPlaySound) event.getPacket();
            SoundType sound = SoundType.values()[packet.getSoundType()];

            if (!packet.isTargeted() && !LastCraft.isGame()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    soundAPI.play(player, sound, packet.getVolume(), packet.getPitch());
                }
                return;
            }

            BukkitGamer gamer = GAMER_MANAGER.getGamer(packet.getPlayerId());
            if (gamer == null) {
                return;
            }

            soundAPI.play(gamer.getPlayer(), sound, packet.getVolume(), packet.getPitch());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onLvlUp(GamerLvlUpEvent e) {
        int level = e.getLevel();
        int expNextLevel = e.getExpNextLevel();

        BukkitGamer gamer = e.getGamer();

        sendEffect(gamer);

        Language lang = gamer.getLanguage();
        gamer.sendMessage("");
        gamer.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        gamer.sendMessage(StringUtil.stringToCenter(lang.getMessage("LVL_UP")));
        gamer.sendMessage("");
        gamer.sendMessage(StringUtil.stringToCenter(lang.getMessage("NEW_LVL",
                StringUtil.getNumberFormat(level))));
        gamer.sendMessage(StringUtil.stringToCenter(lang.getMessage("EXP_TO_NEW_LVL",
                StringUtil.getNumberFormat(expNextLevel))));
        gamer.sendMessage("");
        gamer.sendMessage(StringUtil.stringToCenter(lang.getMessage("GET_REWARD")));
        gamer.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        gamer.sendMessage("");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChangePlayerFriends(GamerFriendEvent e) { //добавить/удалить новых друзей в мапу
        BukkitGamer gamer = e.getGamer();

        FriendsSection section = gamer.getSection(FriendsSection.class);
        if (section != null) {
            section.changeFriend(e.getAction(), e.getFriend());
        }
    }

    private void sendEffect(BukkitGamer gamer) {
        gamer.playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE);
        Player player = gamer.getPlayer();
        if (player == null) {
            return;
        }
        particleAPI.launchInstantFirework(FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.WHITE, Color.PURPLE)
                .build(), player.getLocation().clone().add(0.0, 0.75, 0.0));
    }
}
