package net.lastcraft.base.gamer.sections;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.sql.GlobalLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class MoneySection extends Section {

    private final Map<PurchaseType, Integer> data = new ConcurrentHashMap<>();

    @Setter
    private double multiple;

    public MoneySection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        this.data.putAll(GlobalLoader.getPlayerMoney(baseGamer.getPlayerID()));

        this.multiple = getMultiple(baseGamer.getGroup());

        return true;
    }

    public int getMoney(PurchaseType purchaseType) {
        return this.data.getOrDefault(purchaseType, 0);
    }

    public static double getMultiple(Group group) {
        int levelGroup = group.getLevel();
        double multiple = 1.0;
        if (levelGroup >= Group.GOLD.getLevel()) {
            multiple = 1.5; //gold
        }
        if (levelGroup >= Group.EMERALD.getLevel()) {
            multiple = 1.75; //emerald
        }
        if (levelGroup >= Group.MAGMA.getLevel()) {
            multiple = 2.0; //magma
        }
        return multiple;
    }

    public boolean changeMoney(PurchaseType purchaseType, int money) {
        int value = getMoney(purchaseType);
        if (value + money < 0) {
            return false;
        }

        GlobalLoader.changeMoney(baseGamer.getPlayerID(), purchaseType, money, !data.containsKey(purchaseType));

        this.data.put(purchaseType, value + money);

        return true;
    }

    public void updateMoney(PurchaseType purchaseType, int money, boolean update) {
        if (update) {
            this.data.put(purchaseType, getMoney(purchaseType) + money);
            return;
        }

        this.data.put(purchaseType, money);
    }

    public void setMoneyToMysql(PurchaseType purchaseType, int money) {
        if (money < 0) {
            return;
        }

        GlobalLoader.setMoney(baseGamer.getPlayerID(), purchaseType, money, !this.data.containsKey(purchaseType));
        this.data.put(purchaseType, money);
    }

    /*
    static {
        new TableConstructor("players_money",
                new TableColumn("id", ColumnType.INT_11).primaryKey(true).unigue(true).autoIncrement(true),
                new TableColumn("playerId", ColumnType.INT_11).primaryKey(true),
                new TableColumn("typeMoney", ColumnType.INT_5).primaryKey(true),
                new TableColumn("value", ColumnType.INT_5).setDefaultValue(0)
        ).create(GlobalLoader.getMysqlDatabase()); // + добавить интексы
    }
    */

}
