package net.lastcraft.dartaapi.utils;

import lombok.experimental.UtilityClass;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.gamer.GamerChangeSkinEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerSkinApplyEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.sections.SkinSection;
import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.skin.SkinType;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.entity.BukkitGamerImpl;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.entity.Player;

@UtilityClass
public class SkinUtil {

    private final NmsManager NMS_MANAGER = NmsAPI.getManager();
    private final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public void setSkin(Player player, String skinName, String value, String signature, SkinType skinType) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null || skinName == null || value == null || signature == null) {
            return;
        }

        SkinSection section = gamer.getSection(SkinSection.class);
        if (section == null) {
            return;
        }

        Skin skin = new Skin(skinName, value, signature, skinType, System.currentTimeMillis());
        section.setSkin(skin);
        ((BukkitGamerImpl)gamer).setHead(value);

        gamer.playSound(SoundType.DESTROY);

        GamerChangeSkinEvent event = new GamerChangeSkinEvent(gamer, skin);
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));

        AsyncGamerSkinApplyEvent skinApplyEvent = new AsyncGamerSkinApplyEvent(gamer);
        BukkitUtil.callEvent(skinApplyEvent);

        if (skinApplyEvent.isCancelled()) {
            return;
        }

        NMS_MANAGER.setSkin(player, value, signature);
    }

}