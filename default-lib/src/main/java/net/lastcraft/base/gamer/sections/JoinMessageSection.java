package net.lastcraft.base.gamer.sections;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.constans.JoinMessage;
import net.lastcraft.base.sql.GlobalLoader;
import net.lastcraft.base.sql.api.query.MysqlQuery;
import net.lastcraft.base.sql.api.query.QuerySymbol;

import java.util.ArrayList;
import java.util.List;

public class JoinMessageSection extends Section {

    private JoinMessage joinMessage;
    private final TIntObjectHashMap<JoinMessage> available = new TIntObjectHashMap<>();

    public JoinMessageSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        int playerId = baseGamer.getPlayerID();
        GlobalLoader.getMysqlDatabase().executeQuery(MysqlQuery.selectFrom("join_messages")
                .where("id", QuerySymbol.EQUALLY, playerId)
                .limit(JoinMessage.getMessages().size()), (rs) -> {

            while (rs.next()) {
                JoinMessage joinMessage = JoinMessage.getMessage(rs.getInt("message"));
                available.putIfAbsent(joinMessage.getId(), joinMessage);

                if (rs.getBoolean("available")) {
                    this.joinMessage = joinMessage;
                }
            }
            return Void.TYPE;
        });

        if (!baseGamer.isPlayer()) {
            available.putIfAbsent(JoinMessage.DEFAULT_MESSAGE.getId(), JoinMessage.DEFAULT_MESSAGE);
        }

        if (baseGamer.getGroup() == Group.ADMIN
                || baseGamer.getGroup() == Group.SHULKER
                || baseGamer.getGroup() == Group.YOUTUBE) {
            for (JoinMessage joinMessage : JoinMessage.getMessages()) {
                if (!joinMessage.isShulker()) {
                    continue;
                }

                available.putIfAbsent(joinMessage.getId(), joinMessage);
            }
        }

        return true;
    }

    public JoinMessage getJoinMessage() {
        if (!baseGamer.isGold()) {
            return null;
        }

        if (joinMessage == null) {
            return JoinMessage.DEFAULT_MESSAGE;
        }

        return joinMessage;
    }

    public void setDefaultJoinMessage(JoinMessage joinMessage, boolean mysql) {
        if (joinMessage == null || this.joinMessage == joinMessage)
            return;

        if (mysql) {
            int playerId = baseGamer.getPlayerID();

            if (this.joinMessage != null) {
                GlobalLoader.getMysqlDatabase().execute(MysqlQuery.update("join_messages") //старое отключить
                        .set("available", 0)
                        .where("id", QuerySymbol.EQUALLY, playerId)
                        .where("message", QuerySymbol.EQUALLY, this.joinMessage.getId()).limit());
            }

            GlobalLoader.getMysqlDatabase().executeQuery(MysqlQuery.selectFrom("join_messages")
                    .where("id", QuerySymbol.EQUALLY, playerId)
                    .where("message", QuerySymbol.EQUALLY, joinMessage.getId())
                    .limit(), (rs) -> {
                if (rs.next()) {
                    GlobalLoader.getMysqlDatabase().execute(MysqlQuery.update("join_messages")
                            .set("available", 1)
                            .where("id", QuerySymbol.EQUALLY, playerId)
                            .where("message", QuerySymbol.EQUALLY, joinMessage.getId()).limit());
                } else {
                    GlobalLoader.getMysqlDatabase().execute(MysqlQuery.insertTo("join_messages")
                            .set("available", 1)
                            .set("id", playerId)
                            .set("message", joinMessage.getId()));
                }

                return Void.TYPE;
            });
        }

        this.joinMessage = joinMessage;
    }

    public List<JoinMessage> getAvailableJoinMessage() {
        return new ArrayList<>(available.valueCollection());
    }

    public void addJoinMessage(JoinMessage joinMessage, boolean mysql) {
        if (joinMessage == null || available.containsKey(joinMessage.getId())) {
            return;
        }

        available.put(joinMessage.getId(), joinMessage);
        if (mysql) {
            GlobalLoader.getMysqlDatabase().execute(MysqlQuery.insertTo("join_messages")
                    .set("id", baseGamer.getPlayerID())
                    .set("message", joinMessage.getId()));
        }
    }

    /*
    static {
        new TableConstructor("join_messages",
                new TableColumn("id", ColumnType.INT_11).primaryKey(true),
                new TableColumn("message", ColumnType.INT_5).primaryKey(true),
                new TableColumn("available", ColumnType.BOOLEAN).setDefaultValue(0)
        ).create(GlobalLoader.getMysqlDatabase());
    }
    */

}
