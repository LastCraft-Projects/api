package net.lastcraft.connector;

import lombok.Getter;
import net.lastcraft.api.CoreAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.base.sql.ConnectionConstants;
import net.lastcraft.connector.connections.Data;
import net.lastcraft.connector.connections.Messages;
import org.bukkit.entity.Player;

import java.io.IOException;

@Deprecated
public class Core {

	@Getter
	private static Data dataConnection;
	private static Messages messagesConnection;

	private static final CoreAPI CORE_API = LastCraft.getCoreAPI();

	public static boolean sendMessageToCore(String server, String tag, String message) throws IOException {
		return dataConnection.sendMessageToCore(server, tag, message);
	}

	public static String getUsername() {
		return CORE_API.getServerName();
	}

	public static void redirect(Player player, String server) {
		CORE_API.sendToServer(player, server);
	}

	static {
		new Thread(() -> dataConnection = new Data(CORE_API.getServerName(), "mysql"
				+ ConnectionConstants.DOMAIN.getValue()), "DataThread").start();
		new Thread(() -> messagesConnection = new Messages(CORE_API.getServerName(), "mysql"
				+ ConnectionConstants.DOMAIN.getValue()), "MessageThread").start();
	}

	/*

	public static Data getDataConnection() {
		return DartaAPI.getDataConnection();
	}

	private static Set<String> getServers(String prefixName) {
		if (DartaAPI.getDataConnection() != null) {
			return DartaAPI.getDataConnection().getServers(prefixName);
		} else {
			return newlocale HashSet<>();
		}
	}

	public static Collection<String> loadServers(String prefix) {
		Set<String> servers = Core.getServers(prefix);

		StringBuilder log = newlocale StringBuilder("Загружено %" + prefix + "%: [");
		int count = 0;
		for (String lobbys : servers) {
			if (count == servers.size() - 1) {
				log.append(lobbys);
				break;
			}

			log.append(lobbys).append(", ");

			count++;
		}
		log.append("]");

		MessageUtil.sendConsole("§e" + log);

		return servers;
	}
	*/
}