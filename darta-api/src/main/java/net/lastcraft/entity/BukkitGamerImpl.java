package net.lastcraft.entity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.ActionBarAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.TitleAPI;
import net.lastcraft.api.event.gamer.*;
import net.lastcraft.api.event.gamer.async.AsyncGamerLoadSectionEvent;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.GamerBase;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.*;
import net.lastcraft.base.gamer.friends.Friend;
import net.lastcraft.base.gamer.sections.*;
import net.lastcraft.base.locale.Language;
import net.lastcraft.connector.bukkit.BukkitConnector;
import net.lastcraft.connector.bukkit.ConnectorPlugin;
import net.lastcraft.core.io.DefinedPacket;
import net.lastcraft.core.io.packet.bukkit.BukkitBalancePacket;
import net.lastcraft.core.io.packet.bukkit.BukkitGroupPacket;
import net.lastcraft.dartaapi.event.PlayerChangeGamemodeEvent;
import net.lastcraft.dartaapi.gamemodes.AdventureMode;
import net.lastcraft.dartaapi.gamemodes.GhostMode;
import net.lastcraft.dartaapi.gamemodes.SpectatorMode;
import net.lastcraft.dartaapi.gamemodes.SurvivalMode;
import net.lastcraft.dartaapi.utils.ProtocolSupportUtil;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.inventory.ItemStack;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

import java.net.InetAddress;
import java.util.List;
import java.util.Set;

public final class BukkitGamerImpl extends GamerBase implements BukkitGamer {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final ActionBarAPI ACTION_BAR_API = LastCraft.getActionBarAPI();
    private static final TitleAPI TITLE_API = LastCraft.getTitlesAPI();
    private static final SoundAPI SOUND_API = LastCraft.getSoundAPI();
    private static final ViaAPI VIA_API = Via.getAPI();

    private static final boolean PROTOCOL_SUPPORT = Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null;

    @Getter
    private final InetAddress ip;

    @Setter
    private Player player;
    private ItemStack head;
    private Version version;

    @Deprecated
    private int expLocal;
    @Deprecated
    private int keysLocal;
    @Deprecated
    private int moneyLocal;
    @Deprecated
    private GameModeType gameMode;

    public BukkitGamerImpl(AsyncPlayerPreLoginEvent event) {
        super(event.getName());

        this.ip = event.getAddress();

        gameMode = GameModeType.ADVENTURE;
        expLocal = 0;
        keysLocal = 0;
        moneyLocal = 0;

        SkinSection section = getSection(SkinSection.class);
        if (section != null) {
            this.head = Head.getHeadByValue(section.getSkin().getValue());
        } else {
            this.head = ItemUtil.getBuilder(Material.SKULL_ITEM)
                    .setDurability((short) 3)
                    .build();
        }
    }

    @Override
    protected Set<Class<? extends Section>> initSections() {
        AsyncGamerLoadSectionEvent event = new AsyncGamerLoadSectionEvent(this);
        BukkitUtil.callEvent(event);
        return event.getSections();
    }

    @Override
    public CommandSender getCommandSender() {
        return this.getPlayer();
    }

    @Override
    public void sendMessage(String message) {
        if (player == null || !player.isOnline()) {
            return;
        }

        player.sendMessage(message);
    }

    @Override
    public Version getVersion() {
        if (version != null) {
            return version;
        }


        if (player == null || !player.isOnline()) {
            version = Version.EMPTY;
        } else {
            try {
                if (PROTOCOL_SUPPORT) {
                    version = ProtocolSupportUtil.getVersion(player);
                } else {
                    version = Version.getVersion(VIA_API.getPlayerVersion(player.getUniqueId()));
                }
            } catch (Exception e) {
                version = Version.EMPTY;
            }
        }

        return version;
    }

    @Override
    public GameModeType getGameMode() {
        return gameMode;
    }

    @Override
    public Player getPlayer() {
        if (player == null) {
            return (player = Bukkit.getPlayer(this.name));
        }
        return player;
    }

    @Override
    public ItemStack getHead() {
        if (head == null) {
            return (this.head = ItemUtil.getBuilder(Material.SKULL_ITEM)
                    .setDurability((short) 3)
                    .build()).clone();
        }
        return this.head.clone();
    }

    @Override
    public void setGameMode(GameModeType gameMode) {
        if (player == null) {
            return;
        }

        if (this.gameMode == GameModeType.SPECTATOR) {
            SpectatorMode.removeSpectatorMode(player);
        }

        if (this.gameMode == GameModeType.GHOST) {
            GhostMode.removeGhostMode(player);
        }

        this.gameMode = gameMode;
        PlayerUtil.reset(player);

        switch (gameMode) {
            case ADVENTURE:
                AdventureMode.setAdventureMode(player);
                break;
            case SURVIVAL:
                SurvivalMode.setSurvivalMode(player);
                break;
            case SPECTATOR:
                BukkitUtil.runTask(() -> SpectatorMode.setSpectatorMode(player));
                break;
            case GHOST:
                GhostMode.setGhostMode(player);
                break;
        }

        PlayerChangeGamemodeEvent event = new PlayerChangeGamemodeEvent(getPlayer(), gameMode);
        BukkitUtil.callEvent(event);
    }

    @Override
    public String getChatName() {
        return getDisplayName();
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void setLanguage(Language language) {
        if (player == null) {
            return;
        }

        Language oldLanguage = this.getLanguage();
        if (oldLanguage == language) {
            return;
        }

        GamerChangeLanguageEvent event = new GamerChangeLanguageEvent(this, language, oldLanguage);
        BukkitUtil.callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        getSection(SettingsSection.class).setLang(language, true);
    }

    @Override
    public ChatColor getPrefixColor() {
        try {
             return ChatColor.getByChar(getPrefix().charAt(1));
        } catch (Exception ignored) {}

        return ChatColor.GRAY;
    }

    @Override
    public void sendMessage(BaseComponent... baseComponents) {
        if (player == null) {
            return;
        }

        player.spigot().sendMessage(baseComponents);
    }

    @Override
    public void setPrefix(String prefix) {
        if (getPrefix().equalsIgnoreCase(prefix)) {
            return;
        }

        super.setPrefix(prefix);
        if (player == null) {
            return;
        }

        player.setDisplayName("§r" + prefix + player.getName());

        GamerChangePrefixEvent event = new GamerChangePrefixEvent(this, prefix);
        BukkitUtil.runTask(()-> BukkitUtil.callEvent(event));
    }

    @Override
    public int getExpLocal() {
        return expLocal;
    }

    @Override
    public void setExpLocal() {
        this.expLocal = 0;
    }

    @Override
    public void addExpLocal(int expLocal) {
        this.expLocal += expLocal;
    }

    @Override
    public int getMoneyLocal() {
        return this.moneyLocal;
    }

    @Override
    public void addMoneyLocal(int moneyLocal) {
        this.moneyLocal += moneyLocal;
    }

    @Override
    public void setMoneyLocal() {
        this.moneyLocal = 0;
    }

    @Override
    public int getKeysLocal() {
        return keysLocal;
    }

    @Override
    public void setKeysLocal() {
        this.keysLocal = 0;
    }

    @Override
    public void addKeysLocal(int keysLocal) {
        this.keysLocal += keysLocal;
    }

    @Override
    public boolean addExp(int exp) {
        NetworkingSection networkingSection = getSection(NetworkingSection.class);
        boolean result = networkingSection.addExp(exp);

        if (result && player != null) {
            int newLevel = networkingSection.getLevel();

            sendPacket(new BukkitBalancePacket(getPlayerID(), BukkitBalancePacket.Type.LEVEL, 0, newLevel, false));

            GamerLvlUpEvent event = new GamerLvlUpEvent(this, newLevel, networkingSection.getExpNextLevel());
            BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
        }

        return result;
    }

    @Override
    public int getMoney(PurchaseType purchaseType) {
        return getSection(MoneySection.class).getMoney(purchaseType);
    }

    @Override
    public void setMoney(PurchaseType purchaseType, int money) {
        if (money < 0) {
            return;
        }
        MoneySection section = getSection(MoneySection.class);
        section.setMoneyToMysql(purchaseType, money);

        sendPacket(new BukkitBalancePacket(getPlayerID(),
                BukkitBalancePacket.Type.COINS,
                purchaseType.getId(),
                money,
                false));
    }

    @Override
    public boolean changeMoney(PurchaseType purchaseType, int amount) {
        MoneySection section = getSection(MoneySection.class);
        boolean result = section.changeMoney(purchaseType, amount);

        if (result) {
            sendPacket(new BukkitBalancePacket(getPlayerID(),
                    BukkitBalancePacket.Type.COINS,
                    purchaseType.getId(),
                    amount,
                    true));
        } else {
            SOUND_API.play(player, SoundType.NO);
            sendMessage("");
            sendMessageLocale("GAMER_NO_" + purchaseType.name());
            sendMessage("");
        }
        return result;
    }

    @Override
    public int getKeys(KeyType keyType) {
        return getSection(NetworkingSection.class).getKeys(keyType);
    }

    @Override
    public void setKeys(KeyType keyType, int keys) {
        if (keys < 0 || keyType == null) {
            return;
        }
        NetworkingSection section = getSection(NetworkingSection.class);
        section.setKeysToMysql(keyType, keys);

        sendPacket(new BukkitBalancePacket(getPlayerID(),
                BukkitBalancePacket.Type.KEYS,
                keyType.getId(),
                keys,
                false));
    }

    @Override
    public boolean changeKeys(KeyType keyType, int amount) {
        if (keyType == null) {
            return false;
        }

        NetworkingSection networkingSection = getSection(NetworkingSection.class);
        boolean result = networkingSection.changeKeys(keyType, amount);
        if (result) {
            sendPacket(new BukkitBalancePacket(getPlayerID(),
                    BukkitBalancePacket.Type.KEYS,
                    keyType.getId(),
                    amount,
                    true));
        } else {
            SOUND_API.play(player, SoundType.NO);
            sendMessage("");
            sendMessageLocale("GAMER_NO_KEYS");
            sendMessage("");
        }
        return result;
    }

    @Override
    public void sendTitle(String title, String subTitle) {
        TITLE_API.sendTitle(getPlayer(), title, subTitle);
    }

    @Override
    public void sendTitle(String title, String subTitle, long fadeInTime, long stayTime, long fadeOutTime) {
        TITLE_API.sendTitle(player, title, subTitle, fadeInTime, stayTime, fadeOutTime);
    }

    @Override
    public void sendActionBar(String msg) {
        ACTION_BAR_API.sendBar(getPlayer(), msg);
    }

    @Override
    public void sendActionBarLocale(String key, Object... objects) {
        ACTION_BAR_API.sendBar(player, this.getLanguage().getMessage(key, objects));
    }

    @Override
    public int getFriendsLimit() {
        FriendsSection section = getSection(FriendsSection.class);
        return section.getFriendsLimit();
    }

    @Override
    public TIntObjectMap<Friend> getFriends() {
        FriendsSection section = getSection(FriendsSection.class);
        TIntObjectMap<Friend> friends = new TIntObjectHashMap<>();
        for (int friend : section.getFriends().toArray()) {
            friends.put(friend, new BukkitFriend(friend));
        }
        return friends;
    }

    @Override
    public double getMultiple() {
        return getSection(MoneySection.class).getMultiple();
    }

    @Override
    public void playSound(Sound sound) {
        SOUND_API.play(player, sound);
    }

    @Override
    public void playSound(Sound sound, float volume, float pitch) {
        SOUND_API.play(player, sound, volume, pitch);
    }

    @Override
    public void playSound(SoundType sound) {
        playSound(SOUND_API.getSound(sound));
    }

    @Override
    public void playSound(SoundType sound, float volume, float pitch) {
        playSound(SOUND_API.getSound(sound), volume, pitch);
    }

    @Override
    public JoinMessage getJoinMessage() {
        return getSection(JoinMessageSection.class).getJoinMessage();
    }

    @Override
    public void setDefaultJoinMessage(JoinMessage joinMessage) {
        BukkitUtil.runTaskAsync(() -> getSection(JoinMessageSection.class).setDefaultJoinMessage(joinMessage, true));
    }

    @Override
    public void addJoinMessage(JoinMessage joinMessage) {
        BukkitUtil.runTaskAsync(() -> getSection(JoinMessageSection.class).addJoinMessage(joinMessage, true));
    }

    @Override
    public List<JoinMessage> getAvailableJoinMessages() {
        return getSection(JoinMessageSection.class).getAvailableJoinMessage();
    }

    @Override
    public void setSetting(SettingsType type, boolean key) {
        if (getSetting(type) == key || player == null) {
            return;
        }

        super.setSetting(type, key);

        GamerChangeSettingEvent event = new GamerChangeSettingEvent(this, type, key);
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    @Override
    public void setGroup(Group group) {
        if (getGroup() == group || player == null) {
            return;
        }

        super.setGroup(group);

        sendPacket(new BukkitGroupPacket(getPlayerID(), group.getId()));

        GamerChangeGroupEvent event = new GamerChangeGroupEvent(this, group);
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    public void setHead(String value) {
        this.head = Head.getHeadByValue(value);
    }

    @Override
    public boolean isFriend(int playerID) {
        return getFriends().containsKey(playerID);
    }

    @Override
    public boolean isFriend(IBaseGamer gamer) {
        if (gamer == null) {
            return false;
        }

        return isFriend(gamer.getPlayerID());
    }

    @Override
    public boolean isFriend(Player player) {
        if (player == null) {
            return false;
        }

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) {
            return false;
        }

        return isFriend(gamer.getPlayerID());
    }

    @Override
    public boolean isFriend(Friend friend) {
        return isFriend(friend.getPlayerId());
    }

    private void sendPacket(DefinedPacket packet) {
        ConnectorPlugin.instance().getConnector().sendPacketAsync(packet);
    }
}
