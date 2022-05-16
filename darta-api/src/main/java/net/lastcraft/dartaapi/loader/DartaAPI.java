package net.lastcraft.dartaapi.loader;

import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.Spigot;
import net.lastcraft.api.types.GameType;
import net.lastcraft.api.types.SubType;
import net.lastcraft.base.sql.GlobalLoader;
import net.lastcraft.base.sql.PlayerInfoLoader;
import net.lastcraft.base.sql.api.AbstractDatabase;
import net.lastcraft.dartaapi.commands.*;
import net.lastcraft.dartaapi.donatemenu.DonateMenuListener;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import net.lastcraft.dartaapi.listeners.BungeeMessageListener;
import net.lastcraft.dartaapi.listeners.ChatListener;
import net.lastcraft.dartaapi.listeners.GamerListener;
import net.lastcraft.dartaapi.listeners.NetworkingListener;
import net.lastcraft.dartaapi.utils.WorldTime;
import net.lastcraft.dartaapi.utils.bukkit.EmptyWorldGenerator;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.packetreader.PacketReaderListener;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;

@Getter
public final class DartaAPI extends JavaPlugin {

    private String username;
    private EmptyWorldGenerator generator;

    private GuiDefaultContainer guiDefaultContainer;
    private DonateMenuListener donateMenuListener;

    @Override
    public final void onEnable() {
        try {
            this.username = Bukkit.getWorldContainer().getCanonicalFile().getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        generator = new EmptyWorldGenerator();

        NmsAPI.init(this);
        PublicApiLoader.init(this);

        registerType(); //для геймАПИ

        new GamerListener(this);
        new NetworkingListener(this);
        new ChatListener(this);

        new BungeeMessageListener(this); //синхронизация данных с прокси на которой игрок
        new PacketReaderListener(this); //для голограмм и кликам по ним, как и нпс
        //new FigureFixListener(this); //фикс говна всякого которое в ядре...

        donateMenuListener = new DonateMenuListener(this);
        guiDefaultContainer = new GuiDefaultContainer();

        registerCommands();

        Bukkit.getScheduler().runTaskTimer(this, new WorldTime.TimeTask(), 20L, 30L);
        //WorldTime.freezeTime("lobby", 6000L, false); //todo потом вернуть когда откажемся от старого WorldTime

        new net.lastcraft.dartaapi.utils.bukkit.WorldTime(); //todo удалить потом и вернуть строчку что выше
    }

    @Override
    public final void onDisable() {
        //for (World world: Bukkit.getWorlds()) { //почему-то хуево сохраняется с этим кодом(
        //    //if (world.getName().equalsIgnoreCase("lobby"))
        //    //    continue;
        //    world.save();
        //}

        closeMysql();

        GameManager gameManager = GameManager.getInstance();
        if (gameManager == null) {
            return;
        }

        gameManager.getStats().close();
    }

    @Deprecated
    public static DartaAPI getInstance() {
        return (DartaAPI) Bukkit.getPluginManager().getPlugin("DartaAPI");
    }

    private void closeMysql() {
        AbstractDatabase globalBase = GlobalLoader.getMysqlDatabase();
        if (globalBase != null) {
            globalBase.close();
        }

        AbstractDatabase playerInfoBase = PlayerInfoLoader.getMysqlDatabase();
        if (playerInfoBase != null) {
            playerInfoBase.close();
        }
    }

    private void registerCommands(){
        new FwCommand();
        new RulesCommand();
        new MemoryCommand();
        new CrashCommand();
        new LevelCommand();
        new ListCommand();

        if (GameType.current != GameType.UNKNOWN) {
            new MoneyCommand();
        }

        new GiveMoneyCommand();
        new GiveKeyCommand();

        new PlayMusicCommand();
    }

    private void registerType() {
        Spigot spigot = LastCraft.getGamerManager().getSpigot();
        String serverType = username.split("-")[0];

        try {
            SubType.current = SubType.valueOf(serverType.toUpperCase());
            GameType.current = SubType.current.getGameType();
        } catch (IllegalArgumentException exception) {
            SubType.current = SubType.getByName(System.getProperty("subType", "misc"));
            GameType.current = Arrays.stream(GameType.values())
                    .filter(gameType -> gameType.getLobbyChannel().equalsIgnoreCase(serverType))
                    .findFirst()
                    .orElse(SubType.current.getGameType());
        }
        /*
        SubType.current = SubType.getByName(serverType.toUpperCase());

        if (SubType.current == SubType.MISC) {
            try {
                SubType.current = SubType.valueOf(System.getProperty("subType").toUpperCase());
            } catch (IllegalArgumentException | NullPointerException exception) {
                SubType.current = SubType.MISC;
            }
        }

        GameType.current = SubType.current.getGameType();

        if (GameType.current == GameType.UNKNOWN) {
            try {
                GameType.current = GameType.valueOf(System.getProperty("gameType").toUpperCase());
            } catch (IllegalArgumentException | NullPointerException exception) {
                GameType.current = GameType.UNKNOWN;
            }
        }
        */

        spigot.sendMessage("Тип сервера определен как §e" + GameType.current.name());
        spigot.sendMessage("Подтип сервера определен как §e" + SubType.current.name());
    }

    @Override
    public final ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return generator;
    }

}