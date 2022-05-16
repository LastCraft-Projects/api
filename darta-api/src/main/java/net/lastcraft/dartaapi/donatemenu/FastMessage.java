package net.lastcraft.dartaapi.donatemenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.donatemenu.event.AsyncGamerSendFastMessageEvent;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum FastMessage {
    GO("┌( ಠ_ಠ)┘"),
    SLEEP("－ω－) zzZ"),
    CAPITULATE("(×_×)尸"),
    MAJOR("$ (ಠ_ಠ) $"),
    ENRAGES("─=≡Σ((( つ＞＜)つ"),
    HELLO("(^ω^)ノ"),
    GOODBYE("╰(╯︵╰,)"),
    HUGGING("(ノ^_^)ノ"),
    SAD("(╯︵╰,)"),
    JEEZ("ヽ(ﾟ〇ﾟ)ﾉ"),
    WHY("＼(〇_ｏ)／"),
    ACCESSIBLY("┐(︶▽︶)┌"),
    DONT_STUPID("(; -_-)"),
    NICELY("(＾• ω •＾)"),
    OFFENSIVELY("(个_个)"),
    WTF("(⊙_⊙)"),
    KILLED("(ﾒ￣▽￣)︻┳═一"),
    DANCING("┌(^_^)┘"),
    XZ("¯＼_(ツ)_/¯"),
    EATING("( ˘▽˘)っ♨"),
    GO_AWAY("( ╯°□°)╯ ┻━━┻"),
    MUSIC("(￣▽￣)/♫¸¸♪"),
    FUCK("└(￣-￣└)"),
    WOW("٩(◕‿◕)۶"),
    LOVE("( ˘⌣˘)♡"),
    LAGS("(ノ°益°)ノ"),
    EASY("<(￣︶￣)>"),
    GIVE_RESOURCES("凸◟(º o º )"),
    ;

    private final String smile;

    private final GamerManager gamerManager = LastCraft.getGamerManager();

    public String getKey() {
        return "FAST_MESSAGE_" + name();
    }

    public Group getGroup() {
        return Group.DIAMOND;
    }

    public void sendToAll(BukkitGamer gamer) {
        if (gamer == null || gamer.getPlayer() == null) {
            return;
        }

        AsyncGamerSendFastMessageEvent event = new AsyncGamerSendFastMessageEvent(gamer,
                this, new HashSet<>(gamerManager.getGamers().values()));
        BukkitUtil.runTaskAsync(() -> BukkitUtil.callEvent(event));
    }

    public static Map<String, FastMessage> getMessages(Language lang) {
        Map<String, FastMessage> map = new HashMap<>();
        Arrays.stream(values()).forEach(fastMessage -> map.put(lang.getMessage(fastMessage.getKey()), fastMessage));
        return map;
    }
}
