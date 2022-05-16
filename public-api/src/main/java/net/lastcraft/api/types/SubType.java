package net.lastcraft.api.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.game.TypeGame;
import net.lastcraft.base.locale.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum SubType {

    LOBBY(null, "Lobby", "LOBBY_SERVER_TYPE", GameType.UNKNOWN, Head.LOBBY_ON.getHead()),
    MISC(null, "Misc", "MISC_SERVER_TYPE", GameType.UNKNOWN, Head.CHEST.getHead()),

    SWSN(TypeGame.SOLO, "Solo Normal", "SKYWARS_SOLO_NORMAL_TYPE", GameType.SW, new ItemStack(Material.ENDER_PEARL)),
    SWDN(TypeGame.DOUBLES,"Doubles Normal", "SKYWARS_DUO_NORMAL_TYPE", GameType.SW, new ItemStack(Material.EYE_OF_ENDER)),
    //SWTN(TypeGame.TEAM, "Team Normal", "SKYWARS_TEAM_NORMAL_TYPE", GameType.SW, new ItemStack(Material.SLIME_BALL)),

    SWSI(TypeGame.SOLO, "Solo Insane", "SKYWARS_SOLO_INSANE_TYPE", GameType.SW, new ItemStack(Material.DIAMOND_SWORD)),
    SWDI(TypeGame.DOUBLES, "Doubles Insane", "SKYWARS_DUO_INSANE_TYPE", GameType.SW, new ItemStack(Material.DIAMOND_CHESTPLATE)),

    BWS(TypeGame.SOLO, "Solo", "BEDWARS_SOLO_TYPE", GameType.BW, new ItemStack(Material.IRON_SWORD)),
    BWD(TypeGame.DOUBLES, "Doubles", "BEDWARS_DUO_TYPE", GameType.BW, new ItemStack(Material.BED)),
    BWT(TypeGame.TEAM, "Team", "BEDWARS_TEAM_TYPE", GameType.BW, new ItemStack(Material.BOOK_AND_QUILL)),

    EWS(TypeGame.SOLO, "Solo", "EGGWARS_SOLO_TYPE", GameType.EW, new ItemStack(Material.OBSIDIAN)),
    EWD(TypeGame.DOUBLES, "Doubles", "EGGWARS_DUO_TYPE", GameType.EW, new ItemStack(Material.EYE_OF_ENDER)),
    EWT(TypeGame.TEAM, "Team", "EGGWARS_TEAM_TYPE", GameType.EW, new ItemStack(Material.DRAGON_EGG)),

    KWS(TypeGame.SOLO, "Solo", "KITWARS_SOLO_TYPE", GameType.KW, new ItemStack(Material.BOW)),
    KWD(TypeGame.DOUBLES, "Doubles", "KITWARS_DUO_TYPE", GameType.KW, new ItemStack(Material.DIAMOND_HELMET)),

    LWS(TypeGame.SOLO, "Solo", "LUCKYWARS_SOLO_TYPE", GameType.LW, new ItemStack(Material.BOW)),
    LWD(TypeGame.DOUBLES, "Doubles", "LUCKYWARS_DUO_TYPE", GameType.LW, new ItemStack(Material.IRON_HELMET)),

    SGS(TypeGame.SOLO, "Solo", "SURVIVALGAMES_SOLO_TYPE", GameType.SG, new ItemStack(Material.WOOD_SWORD)),
    SGD(TypeGame.DOUBLES, "Doubles", "SURVIVALGAMES_DUO_TYPE", GameType.SG, new ItemStack(Material.IRON_SWORD)),

    PRS(TypeGame.SOLO, "Solo", "PARKOURRACERS_SOLO_TYPE", GameType.PR, new ItemStack(Material.IRON_BOOTS)),
    PRD(TypeGame.DOUBLES, "Doubles", "PARKOURRACERS_DUO_TYPE", GameType.PR, new ItemStack(Material.GOLD_BOOTS)),
    PRC(TypeGame.SOLO, "Classic", "PARKOURRACERS_CLASSIC_TYPE", GameType.PR, new ItemStack(Material.LEATHER_BOOTS)),

    BBS(TypeGame.SOLO, "BuildBattle Solo", "BUILDBATTLE_SOLO_TYPE", GameType.ARCADE, new ItemStack(Material.STAINED_CLAY, 1, (short) 1)),
    BBD(TypeGame.DOUBLES, "BuildBattle Doubles", "BUILDBATTLE_DUO_TYPE", GameType.ARCADE, new ItemStack(Material.STAINED_CLAY, 1, (short) 3)),

    HNS(TypeGame.SOLO, "HideAndSeek Classic", "HIDE_AND_SEEK_GAME", GameType.ARCADE, new ItemStack(Material.WORKBENCH));

    public static SubType current = MISC;

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private final TypeGame typeGame;
    private final String typeName;
    private final String localeName;
    private final GameType gameType;
    private final ItemStack itemStack;

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public String getName(Player player) {
        return getName(GAMER_MANAGER.getGamer(player));
    }

    public String getName(BukkitGamer gamer) {
        Language lang = Language.getDefault();
        if (gamer != null) {
            lang = gamer.getLanguage();
        }

        return lang.getMessage(localeName);
    }

    public ItemStack getItem(Player player) {
        return ItemUtil.getBuilder(itemStack.clone())
                .setName("§e" + getName(player))
                .build();
    }

    public ItemStack getModeItem(Player player) {
        return ItemUtil.getBuilder(itemStack.clone())
                .setName("§e" + getName(player))
                .build();
    }

    public static List<SubType> ofMode(GameType type) {
        return Arrays.stream(values())
                .filter(subType -> subType.getGameType() == type)
                .collect(Collectors.toList());
    }

    public static SubType getByName(String name) {
        try {
            return SubType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            return MISC;
        }
    }
}
