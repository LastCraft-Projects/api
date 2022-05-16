package net.lastcraft.api.inventory.type;

import gnu.trove.map.TIntObjectMap;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.util.InventoryUtil;
import org.bukkit.inventory.Inventory;

public interface DInventory extends BaseInventory {

    /**
     * добавить айтем
     * @param slot - куда сетить айтем
     * @param item - DItem с действием
     */
    void setItem(int slot, DItem item);
    default void setItem(int x, int y, DItem item) {
        setItem(InventoryUtil.getSlotByXY(x, y), item);
    }
    void addItem(DItem dItem);

    /**
     * Узнать имя инв
     * @return name инвенторя
     */
    String getName();

    /**
     * удалить айтем по слоту
     * @param slot - айтем
     */
    void removeItem(int slot);

    /**
     * очистить инвентарь
     */
    void clearInventory();

    /**
     * кол-во слотов в инв
     * @return кол-во слотов в инв
     */
    int size();

    /**
     * получить бакитовский инв
     * @return бакитовский инв
     */
    Inventory getInventory();

    /**
     * получить все айтемы и их слоты
     * @return все айтемы
     */
    TIntObjectMap<DItem> getItems();

    /**
     * задать дополнительные задачи
     * на открытие и закрытие инв
     * @param inventoryAction - код который будет выполнятся
     */
    void createInventoryAction(InventoryAction inventoryAction);

    /**
     * получить код для работы
     * @return вернет InventoryAction или Null(если его нет)
     */
    InventoryAction getInventoryAction();
}
