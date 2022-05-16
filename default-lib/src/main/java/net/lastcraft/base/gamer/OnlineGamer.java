package net.lastcraft.base.gamer;

import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.base.gamer.constans.Version;
import net.lastcraft.base.locale.Language;

import java.net.InetAddress;

public interface OnlineGamer extends IBaseGamer {

    void sendMessage(String message);

    void sendTitle(String title, String subTitle);
    void sendTitle(String title, String subTitle, long fadeInTime, long stayTime, long fadeOutTime);
    void sendActionBar(String msg);
    void sendActionBarLocale(String key, Object... replaced);

    Version getVersion(); //версия с которой играет игрок

    InetAddress getIp();

    Language getLanguage();

    boolean getSetting(SettingsType type);
    void setSetting(SettingsType type, boolean key);
}
