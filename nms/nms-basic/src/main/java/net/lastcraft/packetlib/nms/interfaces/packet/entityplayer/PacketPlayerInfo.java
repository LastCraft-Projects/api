package net.lastcraft.packetlib.nms.interfaces.packet.entityplayer;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntityPlayer;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.DPacketEntity;
import net.lastcraft.packetlib.nms.types.PlayerInfoActionType;

public interface PacketPlayerInfo extends DPacketEntity<DEntityPlayer> {

    void setPlayerInfoAction(PlayerInfoActionType actionType);

    PlayerInfoActionType getActionType();
}
