package net.lastcraft.api.player;

import gnu.trove.map.TIntObjectMap;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.OnlineGamer;
import net.lastcraft.base.gamer.constans.JoinMessage;
import net.lastcraft.base.gamer.constans.KeyType;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.gamer.friends.Friend;
import net.lastcraft.base.locale.Language;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface BukkitGamer extends GamerEntity, OnlineGamer, IBaseGamer, IGamer {
    Player getPlayer();
    ItemStack getHead();

    TIntObjectMap<Friend> getFriends();
    boolean isFriend(int playerID);
    boolean isFriend(IBaseGamer gamer);
    boolean isFriend(Player player);
    boolean isFriend(Friend friend);
    int getFriendsLimit();

    void setLanguage(Language language);

    ChatColor getPrefixColor();

    void sendMessage(BaseComponent... baseComponents);

    /**
     * множитель монет
     * @return - множитель
     */
    double getMultiple();

    /**
     * воспроизвести звук
     * @param sound - какой звук
     */
    void playSound(Sound sound);
    void playSound(Sound sound, float volume, float pitch);
    void playSound(SoundType sound);
    void playSound(SoundType sound, float volume, float pitch);

    /**
     * сообщения при входе игрока
     * @return - сообщение
     */
    JoinMessage getJoinMessage();
    void setDefaultJoinMessage(JoinMessage joinMessage);
    void addJoinMessage(JoinMessage joinMessage);
    List<JoinMessage> getAvailableJoinMessages();

    boolean addExp(int exp);

    /**
     * получить информацию о балансе игрока
     * @param purchaseType - что именно
     * @return - значение
     */
    int getMoney(PurchaseType purchaseType);

    /**
     * установить опр кол-во монет
     * @param purchaseType - тип монет
     * @param money - кол-во
     */
    void setMoney(PurchaseType purchaseType, int money);

    /**
     * поменять информацию об игроке(добавить или удалить)
     * @param purchaseType - что именно
     * @param amount - на сколько
     * @return - поменялось или нет
     */
    boolean changeMoney(PurchaseType purchaseType, int amount);

    /**
     * получить список ключей игрока
     * @param keyType - тип ключа
     * @return - ключи
     */
    int getKeys(KeyType keyType);

    /**
     * установить опр кол-во ключей
     * @param keyType - тип ключей
     * @param keys - кол-во
     */
    void setKeys(KeyType keyType, int keys);

    /**
     * поменять колз-во ключей(добавить или удалить)
     * @param keyType - какой тип ключей
     * @param amount - на сколько
     * @return - прошла операция или нет
     */
    boolean changeKeys(KeyType keyType, int amount);
}
