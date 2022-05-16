package net.lastcraft.dartaapi.game.cosmetics.winner;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.cosmetics.CosmeticItem;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.shop.BuyableCoins;
import net.lastcraft.api.shop.Choosable;
import net.lastcraft.api.shop.Shopable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WinEffect implements BuyableCoins, CosmeticItem, Choosable, Shopable {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private static List<WinEffect> winEffects = new ArrayList<>();
    public ItemStack icon;
    private Consumer<Player> winner;
    private int cost;
    private int id = 0;

    public WinEffect(int cost, ItemStack icon, Consumer<Player> winner) {
        id = winEffects.size();
        winEffects.add(this);
        this.cost = cost;
        this.icon = icon;
        this.winner = winner;
    }

    public static List<WinEffect> getWinEffects() {
        return winEffects;
    }

    public void use(Player player) {
        winner.accept(player);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof CosmeticItem))
            return false;
        CosmeticItem item = (CosmeticItem) o;
        return item.getType() == this.getType() && item.getId() == this.getId();
    }
    @Override
    public int hashCode() {
        return getType() * 31 + this.getId();
    }

    @Override
    public int cost(String playerName) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(playerName);
        double multiplier = 1.0;
        if (gamer.isEmerald()) {
            multiplier = 0.8;
        }
        if (gamer.isMagma()) {
            multiplier = 0.65;
        }
        return (int) (cost * multiplier);
    }

    @Override
    public String canBuy(String playerName) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(playerName);
        if (gamer.getLevelNetwork() < 30 && !gamer.isDiamond()) {
            return "§cДля покупки данного эффекта вам нужен §d30 уровень §cили выше!";
        }
        return null;
    }

    @Override
    public void buy(String playerName) {
    }

    @Override
    public ItemStack getIcon(String playerName) {
        return icon;
    }

    @Override
    public boolean have(String playerName) {
        return false;
    }

    @Override
    public void choose(String playerName) {
    }

    @Override
    public boolean isChoosed(String playerName) {
        return false;
    }
}
