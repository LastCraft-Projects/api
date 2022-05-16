package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.constans.KeyType;
import net.lastcraft.base.sql.PlayerInfoLoader;
import net.lastcraft.base.util.Pair;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.connector.bukkit.BukkitConnector;
import net.lastcraft.connector.bukkit.ConnectorPlugin;
import net.lastcraft.core.io.packet.bukkit.BukkitBalancePacket;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;

import java.util.Map;

public final class GiveKeyCommand implements CommandInterface {

    private final GamerManager gamerManager = LastCraft.getGamerManager();

    public GiveKeyCommand() {
        SpigotCommand command = COMMANDS_API.register("addkeys", this);
        command.setGroup(Group.ADMIN);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        if (gamerEntity.isHuman() && !((BukkitGamer) gamerEntity).isDeveloper()) {
            return;
        }

        if (args.length < 3) {
            gamerEntity.sendMessage("§6Сервер §8| §fОшибка, пишите /addkeys <ник игрока> <type> <ключи>");
            return;
        }

        KeyType keyType;
        try {
            keyType = KeyType.getKey(Integer.parseInt(args[1]));
        } catch (Exception e) {
            keyType = null;
        }

        if (keyType == null) {
            gamerEntity.sendMessage("§6Сервер §8| §fОшибка, неверно указан тип ключей!");
            return;
        }

        int keys;
        try {
            keys = Integer.parseInt(args[2]);
        } catch (Exception e) {
            keys = 0;
        }

        KeyType finalKeyType = keyType;
        int finalKeys = keys;
        BukkitUtil.runTaskAsync(() -> addKeys(gamerEntity, args[0], finalKeyType, finalKeys));
    }

    private void addKeys(GamerEntity gamerEntity, String name, KeyType keyType, int keys) {
        IBaseGamer gamer = gamerManager.getOrCreate(name);
        if (gamer == null || gamer.getPlayerID() == -1) {
            COMMANDS_API.playerNeverPlayed(gamerEntity, name);
            return;
        }

        if (!gamer.isOnline()) {
            Map<KeyType, Pair<Integer, Integer>> keysData = PlayerInfoLoader.getData(gamer.getPlayerID()).getSecond();
            boolean insert = !keysData.containsKey(keyType);

            int random = 0;
            Pair<Integer, Integer> pair = keysData.get(keyType);
            if (pair != null) {
                random = pair.getSecond();
            }

            PlayerInfoLoader.updateData(gamer.getPlayerID(), keyType.getId(), keys, random, insert);

            BukkitBalancePacket packet = new BukkitBalancePacket(gamer.getPlayerID(),
                    BukkitBalancePacket.Type.KEYS,
                    keyType.getId(),
                    keys,
                    true);
            ConnectorPlugin.instance().getConnector().sendPacketAsync(packet);
        } else if (gamer instanceof BukkitGamer) {
            BukkitGamer targetGamer = (BukkitGamer) gamer;
            targetGamer.changeKeys(keyType, keys);
        }

        gamerEntity.sendMessage("§6Сервер §8| §fВы выдали игроку "
                + gamer.getDisplayName() + " §a"
                + StringUtil.getNumberFormat(keys) + " §fключей ("
                + keyType.getName(gamerEntity.getLanguage()) + ")");
    }
}
