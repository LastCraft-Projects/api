package net.lastcraft.dartaapi.commands;

import com.google.common.collect.ImmutableList;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.CommandTabComplete;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.sql.GlobalLoader;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.connector.bukkit.BukkitConnector;
import net.lastcraft.connector.bukkit.ConnectorPlugin;
import net.lastcraft.core.io.packet.bukkit.BukkitBalancePacket;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;

import java.util.List;
import java.util.Map;

public final class GiveMoneyCommand implements CommandInterface, CommandTabComplete {

    private final GamerManager gamerManager = LastCraft.getGamerManager();
    private final ImmutableList<String> immutableList = ImmutableList.of("gold", "money");

    public GiveMoneyCommand() {
        SpigotCommand command = COMMANDS_API.register("addmoney", this);
        command.setCommandTabComplete(this);
        command.setGroup(Group.ADMIN);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        if (gamerEntity.isHuman() && !((BukkitGamer)gamerEntity).isDeveloper())
            return;

        if (args.length < 3) {
            gamerEntity.sendMessage("§6Сервер §8| §fОшибка, пишите /addmoney <ник игрока> <тип> <монеты>");
            return;
        }

        int money;
        try {
            money = Integer.parseInt(args[2]);
        } catch (Exception e) {
            money = 0;
        }

        PurchaseType purchaseType;
        try {
            purchaseType = PurchaseType.valueOf(args[1].toUpperCase());
        } catch (Exception e) {
            purchaseType = PurchaseType.MYSTERY_DUST;
        }

        addMoney(gamerEntity, args[0], purchaseType, money);
    }

    private void addMoney(GamerEntity gamerEntity, String name, PurchaseType purchaseType, int money) {
        BukkitUtil.runTaskAsync(() -> {
            IBaseGamer gamer = gamerManager.getOrCreate(name);
            if (gamer == null || gamer.getPlayerID() == -1) {
                COMMANDS_API.playerNeverPlayed(gamerEntity, name);
                return;
            }

            if (!gamer.isOnline()) {
                Map<PurchaseType, Integer> playerMoney = GlobalLoader.getPlayerMoney(gamer.getPlayerID());
                GlobalLoader.changeMoney(gamer.getPlayerID(), purchaseType, money, !playerMoney.containsKey(purchaseType));

                BukkitBalancePacket packet = new BukkitBalancePacket(gamer.getPlayerID(),
                        BukkitBalancePacket.Type.COINS,
                        purchaseType.getId(),
                        money,
                        true);
                ConnectorPlugin.instance().getConnector().sendPacketAsync(packet);
            } else if (gamer instanceof BukkitGamer) {
                BukkitGamer targetGamer = (BukkitGamer) gamer;
                targetGamer.changeMoney(purchaseType, money);
            }

            gamerEntity.sendMessage("§6Сервер §8| §fВы выдали игроку " + gamer.getDisplayName() + " §a"
                    + StringUtil.getNumberFormat(money) + " §f" + purchaseType.name());
        });
    }

    @Override
    public List<String> getComplete(GamerEntity gamerEntity, String alias, String[] args) {
        if (gamerEntity.isHuman() && !((BukkitGamer)gamerEntity).isDeveloper()) {
            return ImmutableList.of();
        }

        if (args.length == 2) {
            return COMMANDS_API.getCompleteString(immutableList, args);
        }

        return ImmutableList.of();
    }
}
