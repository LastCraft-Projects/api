package net.lastcraft.api.entity;

import net.lastcraft.api.entity.npc.NPC;
import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.HumanNPC;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.base.skin.Skin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface EntityAPI {

    /**
     * Создать армор стенд
     * по умолчанию скрыт для всех
     * @param location - локация для создания армор стенда
     * @return - вернет CustomStand
     */
    CustomStand createStand(Location location);

    /**
     * Создать НПС HUMAN
     * по умолчанию скрыт для всех
     * @param location - локация для энтити
     * @param value - часть скина(голова)
     * @param signature - часть скина(тело)
     * @return - вернет NPC
     */
    HumanNPC createNPC(Location location, String value, String signature);
    HumanNPC createNPC(Location location, Player player);
    HumanNPC createNPC(Location location, Skin skin);

    /**
     * Создать НПС
     * по умолчанию скрыт для всех
     * @param location - локация для энтити
     * @param type - тип НПС
     * @return - вернет NPC
     */
    <T extends NPC> T createNPC(Location location, NpcType type);

    List<NPC> getNPCs();

    <T extends NPC> List<T> getNPC(NpcType type);

    List<CustomStand> getCustomStands();
}
