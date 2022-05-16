package net.lastcraft.api;

import net.lastcraft.api.command.CommandsAPI;
import net.lastcraft.api.effect.ParticleAPI;
import net.lastcraft.api.entity.EntityAPI;
import net.lastcraft.api.exeption.ApiNotLoadedException;
import net.lastcraft.api.hologram.HologramAPI;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.api.types.GameType;
import net.lastcraft.api.types.SubType;
import net.lastcraft.api.usableitem.UsableAPI;

public final class LastCraft {

    private static GamerManager gamerManagerAPI;
    private static UsableAPI usableAPI;
    private static HologramAPI hologramAPI;
    private static ScoreBoardAPI scoreBoardAPI;
    private static EntityAPI entityAPI;
    private static CommandsAPI commandsAPI;
    private static InventoryAPI inventoryAPI;
    private static TitleAPI titleAPI;
    private static BorderAPI borderAPI;
    private static ActionBarAPI actionBarAPI;
    private static SoundAPI soundAPI;
    private static JSONMessageAPI jsonMessageAPI;
    private static ParticleAPI particleAPI;
    private static CoreAPI coreAPI;

    public static boolean isLobby() {
        return SubType.current == SubType.LOBBY
                && GameType.current != GameType.UNKNOWN;
    }

    public static boolean isMisc() {
        return SubType.current == SubType.MISC;
    }

    public static boolean isGame() {
        return !isMisc() && !isLobby() && !isHub();
    }

    public static boolean isHub() {
        return SubType.current == SubType.LOBBY
                && GameType.current == GameType.UNKNOWN;
    }

    public static CoreAPI getCoreAPI() {
        if (coreAPI == null)
            throw new ApiNotLoadedException();
        return coreAPI;
    }

    public static GamerManager getGamerManager() {
        if (gamerManagerAPI == null)
            throw new ApiNotLoadedException ();
        return gamerManagerAPI;
    }

    public static ParticleAPI getParticleAPI() {
        if (particleAPI == null)
            throw new ApiNotLoadedException ();
        return particleAPI;
    }

    public static UsableAPI getUsableAPI() {
        if (usableAPI == null)
            throw new ApiNotLoadedException ();
        return usableAPI;
    }

    public static JSONMessageAPI getJsonMessageAPI() {
        if (jsonMessageAPI == null)
            throw new ApiNotLoadedException ();
        return jsonMessageAPI;
    }

    public static SoundAPI getSoundAPI() {
        if (soundAPI == null)
            throw new ApiNotLoadedException ();
        return soundAPI;
    }

    public static TitleAPI getTitlesAPI() {
        if (titleAPI == null)
            throw new ApiNotLoadedException ();
        return titleAPI;
    }

    public static ActionBarAPI getActionBarAPI() {
        if (actionBarAPI == null)
            throw new ApiNotLoadedException ();
        return actionBarAPI;
    }

    public static BorderAPI getBorderAPI() {
        if (borderAPI == null)
            throw new ApiNotLoadedException ();
        return borderAPI;
    }

    public static CommandsAPI getCommandsAPI() {
        if (commandsAPI == null)
            throw new ApiNotLoadedException ();
        return commandsAPI;
    }

    public static HologramAPI getHologramAPI() {
        if (hologramAPI == null)
            throw new ApiNotLoadedException ();
        return hologramAPI;
    }

    public static EntityAPI getEntityAPI() {
        if (entityAPI == null)
            throw new ApiNotLoadedException ();
        return entityAPI;
    }

    public static InventoryAPI getInventoryAPI() {
        if (inventoryAPI == null)
            throw new ApiNotLoadedException ();
        return inventoryAPI;
    }

    public static ScoreBoardAPI getScoreBoardAPI() {
        if (scoreBoardAPI == null)
            throw new ApiNotLoadedException ();
        return scoreBoardAPI;
    }
}
