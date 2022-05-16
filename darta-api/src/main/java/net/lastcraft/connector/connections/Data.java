package net.lastcraft.connector.connections;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class Data extends Connection {
	public Data(String name, String host) {
		super(name, host, 1);
	}

	public void onConnected() {
		this.locker.lock();
		try {
			this.out.writeInt(6);
			this.out.writeUTF(this.socket.getLocalAddress().getHostAddress());
			this.out.writeInt(Bukkit.getPort());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			this.locker.unlock();
		}
	}

	public void lock() {
		this.locker.lock();
	}

	public void unlock() {
		this.locker.unlock();
	}

	public Set<String> getServers(String prefixName) {
		Set<String> servers = new HashSet<>();
		lock();
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeInt(77);
			out.writeInt(0);
			out.writeUTF(prefixName);
			sendPacket(out.toByteArray());

			in.readInt(); //Количество серверов
			while (in.available() > 0) {
				String str = in.readUTF();
				servers.add(str);
			}
			return servers;
		} catch (Exception ex) {
			return servers;
		} finally {
			unlock();
		}
	}

	public boolean sendMessageToCore(String server, String tag, String message)
			throws IOException {
		lock();
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeInt(0);
			out.writeUTF(server);
			out.writeUTF(tag);
			out.writeUTF(message);
			sendPacket(out.toByteArray());
			return this.in.readBoolean();
		} finally {
			unlock();
		}
	}
}