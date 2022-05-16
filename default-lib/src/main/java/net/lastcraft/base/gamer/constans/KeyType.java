package net.lastcraft.base.gamer.constans;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.base.locale.Language;

import javax.annotation.Nullable;
import java.util.List;

@AllArgsConstructor
@Getter
public enum KeyType {
    //0 - делать нельзя, тк 0 код опыта!!!!!
    DEFAULT_KEY(1, 20, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDc4YWJkZjk4MmY5MDllYWI1ZGU5YmY5NjljZjE0ZjY2NGRiNGM0NDc3Mzg0NTllYTQwMTYyYjM3ZDEyNCJ9fX0="), //для гаджетов в лобби
    GAME_KEY(2, 21, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc1MzQ4MmMxZDUwNjFlOWIyNDNmMjU5YjUyZjk1YzQzM2ZmMDMwMzlhYzg0M2RkYzQ2MjY3YjkwMzA1MiJ9fX0="), // для наборов и перков в лобби
    GAME_COSMETIC_KEY(3, 22, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNiZmY3MjFhNDQ4ZjY5YTdmODAyYjZlYThiZDg0NTRhNWI4NDg4MmFkNGE0ZTQ4MGI0NTQ0YzE4OTY0ZSJ9fX0="), //для редких каких-то и праздничных вещей
    MONEY_KEY(4, 23, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmJhOGQ0YzM0MDgzNDY4MjYxMjY5MmMwNGJkYzUzMjk1MDI0YzQ4ZjM0MjI2ZTcyMTgyY2NkOWRlYzI1OTIifX19"), //для денег
    GROUP_KEY(5, 24, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ5ODg2YTU3YjVlNWIxMzZkZGIyYTk0NzkxZjliN2U3MzE2ZTVkODY0NmYxN2EwNDdkNWY5NWE3NDg0Y2IifX19"), //для доната
    SURVIVAL_KEY(6, 30, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNhMTE3OTJhYzc5ZjM4MjQ0OWFiZTFjNmI4ZGVmYjQwZGM1NjU0ZmM4ZjRlNDE4MDkyNzA5N2M2YTg2NCJ9fX0=");
    ;

    private static final TIntObjectMap<KeyType> KEY_TYPES = new TIntObjectHashMap<>();

    private final int id;
    private final int slotGui; //слот гуи
    private final String headValue; //голова

    public String getName(Language language) {
        return language.getMessage(this.name());
    }

    public List<String> getLore(Language language) {
        return language.getList(this.name() + "_LORE");
    }

    @Nullable
    public static KeyType getKey(int id) {
        return KEY_TYPES.get(id);
    }

    static {
        for (KeyType keyType : values()) {
            KEY_TYPES.put(keyType.id, keyType);
        }
    }
}
