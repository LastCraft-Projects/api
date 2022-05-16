package net.lastcraft.base.gamer.constans;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public enum PurchaseType {
    MYSTERY_DUST(0, '6'),
    GOLD(1, 'e')
    ;

    private static final TIntObjectMap<PurchaseType> MONEY_TYPE = new TIntObjectHashMap<>();

    private final int id;
    private final char color;

    @Nullable
    public static PurchaseType getType(int id) {
        return MONEY_TYPE.get(id);
    }

    static {
        for (PurchaseType purchaseType : values()) {
            MONEY_TYPE.put(purchaseType.id, purchaseType);
        }
    }
}