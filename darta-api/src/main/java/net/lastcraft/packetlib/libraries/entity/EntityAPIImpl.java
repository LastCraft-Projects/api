package net.lastcraft.packetlib.libraries.entity;

import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.entity.EntityAPI;
import net.lastcraft.api.entity.npc.NPC;
import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.HumanNPC;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.exeption.NpcErrorTypeException;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.skin.Skin;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.packetlib.libraries.entity.customstand.CraftStand;
import net.lastcraft.packetlib.libraries.entity.customstand.StandListener;
import net.lastcraft.packetlib.libraries.entity.customstand.StandManager;
import net.lastcraft.packetlib.libraries.entity.npc.NPCListener;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.libraries.entity.npc.type.*;
import net.lastcraft.packetlib.libraries.entity.tracker.EntityTrackerListener;
import net.lastcraft.packetlib.libraries.entity.tracker.TrackerEntity;
import net.lastcraft.packetlib.libraries.entity.tracker.TrackerManager;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EntityAPIImpl implements EntityAPI, TrackerManager {

    private final GamerManager gamerManager = LastCraft.getGamerManager();

    private final DartaAPI dartaAPI;

    private final StandManager standManager;
    private final NPCManager npcManager;

    private final NmsManager nmsManager = NmsAPI.getManager();

    public EntityAPIImpl(DartaAPI dartaAPI) {
        this.dartaAPI = dartaAPI;

        this.standManager = new StandManager();
        this.npcManager = new NPCManager();

        new StandListener(this);
        new NPCListener(this);

        new EntityTrackerListener(dartaAPI, this);
    }

    @Override
    public CustomStand createStand(Location location) {
        return new CraftStand(standManager, location);
    }

    @Override
    public HumanNPC createNPC(Location location, String value, String signature) {
        return new CraftHumanNPC(npcManager, location, value, signature);
    }

    @Override
    public HumanNPC createNPC(Location location, Skin skin) {
        return createNPC(location, skin.getValue(), skin.getSignature());
    }

    @Override
    public HumanNPC createNPC(Location location, Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = gamerManager.getGamer(player);
            if (gamer != null)
                return createNPC(location, gamer.getSkin());
        }

        return createNPC(location, "", "");
    }

    @Override
    public <T extends NPC> T createNPC(Location location, NpcType type) {
        NPC npc;
        switch (type) {
            case COW:
                npc = new CraftCowNPC(npcManager, location);
                break;
            case ZOMBIE:
                npc = new CraftZombieNPC(npcManager, location);
                break;
            case VILLAGER:
                npc = new CraftVillagerNPC(npcManager, location);
                break;
            case MUSHROOM_COW:
                npc = new CraftMushroomCowNPC(npcManager, location);
                break;
            case SLIME:
                npc = new CraftSlimeNPC(npcManager, location);
                break;
            case CREEPER:
                npc = new CraftCreeperNPC(npcManager, location);
                break;
            case WOLF:
                npc = new CraftWolfNPC(npcManager, location);
                break;
            case BLAZE:
                npc = new CraftBlazeNPC(npcManager, location);
                break;
            case ENDER_DRAGON:
                npc = new CraftEnderDragonNPC(npcManager, location);
                break;
            case HUMAN:
            default:
                npc = createNPC(location, "", "");
        }

        if (npc.getType() != type) {
            npcManager.getNPCs().remove(npc.getEntityID());
            throw new NpcErrorTypeException("вы указали неверный Type NPC");
        }

        return (T) npc;
    }

    @Override
    public List<NPC> getNPCs() {
        return npcManager.getNPCs().values()
                .stream()
                .map(craftNPC -> (NPC)craftNPC)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends NPC> List<T> getNPC(NpcType type) {
        return npcManager.getNPCs().values()
                .stream()
                .filter(craftNPC -> craftNPC.getType() == type)
                .map(craftNPC -> (T)craftNPC)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomStand> getCustomStands() {
        return standManager.getStands().values()
                .stream()
                .map(craftStand -> (CustomStand)craftStand)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackerEntity> getTrackerEntities() {
        List<TrackerEntity> entities = new ArrayList<>();
        entities.addAll(npcManager.getNPCs().values());
        entities.addAll(standManager.getStands().values());
        return entities;
    }
}
