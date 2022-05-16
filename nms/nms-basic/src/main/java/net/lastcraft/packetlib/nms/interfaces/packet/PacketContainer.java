package net.lastcraft.packetlib.nms.interfaces.packet;

import net.lastcraft.api.effect.ParticleEffect;
import net.lastcraft.api.entity.EquipType;
import net.lastcraft.api.entity.npc.AnimationNpcType;
import net.lastcraft.api.scoreboard.DisplaySlot;
import net.lastcraft.packetlib.nms.interfaces.DWorldBorder;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityPlayer;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.*;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketBed;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketCamera;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import net.lastcraft.packetlib.nms.interfaces.packet.world.PacketWorldParticles;
import net.lastcraft.packetlib.nms.scoreboard.*;
import net.lastcraft.packetlib.nms.types.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PacketContainer {

    void sendPacket(Player player, DPacket... dPackets);

    void sendChatPacket(Player player, String message, ChatMessageType messageType);

    void sendTitlePacket(Player player, TitleActionType type, String message);
    void sendTitlePacket(Player player, int fadeIn, int stay, int fadeOut);

    void sendWorldBorderPacket(Player player, DWorldBorder border, WorldBorderActionType type);

    PacketScoreBoardTeam getScoreBoardTeamPacket(DTeam team, TeamAction action);

    PacketDisplayObjective getDisplayObjectivePacket(DisplaySlot slot, DObjective objective);

    PacketScoreboardObjective getScoreboardObjectivePacket(DObjective objective, ObjectiveActionMode mode);

    PacketScoreboardScore getScoreboardScorePacket(DScore score, ScoreboardAction action);

    PacketNamedEntitySpawn getNamedEntitySpawnPacket(DEntityPlayer entityPlayer);

    PacketPlayerInfo getPlayerInfoPacket(DEntityPlayer entityPlayer, PlayerInfoActionType actionType);

    PacketAnimation getAnimationPacket(DEntity entity, AnimationNpcType animation);

    PacketAttachEntity getAttachEntityPacket(DEntity entity, DEntity vehicle);

    PacketEntityDestroy getEntityDestroyPacket(int... entityIDs);

    PacketMount getMountPacket(DEntity entity);

    PacketEntityMetadata getEntityMetadataPacket(DEntity entity);

    PacketCamera getCameraPacket(Player player);

    PacketEntityLook getEntityLookPacket(DEntity entity, byte yaw, byte pitch);

    PacketEntityEquipment getEntityEquipmentPacket(DEntity entity, EquipType slot, ItemStack itemStack);

    PacketEntityHeadRotation getEntityHeadRotationPacket(DEntity entity, byte yaw);

    PacketSpawnEntity getSpawnEntityPacket(DEntity entity, EntitySpawnType entitySpawnType, int objectData);

    PacketSpawnEntityLiving getSpawnEntityLivingPacket(DEntityLiving entityLiving);

    PacketEntityTeleport getEntityTeleportPacket(DEntity entity);

    PacketBed getBedPacket(DEntityPlayer entity, Location bed);

    PacketWorldParticles getWorldParticlesPacket(ParticleEffect effect, boolean longDistance, Location center,
                                                 float offsetX, float offsetY, float offsetZ, float speed,
                                                 int amount, int... data);

}
