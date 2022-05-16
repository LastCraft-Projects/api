package net.lastcraft.connector.connections;

import net.lastcraft.connector.events.CoreMessageEvent;
import org.bukkit.Bukkit;

import java.io.IOException;

@Deprecated
public class Messages extends Connection {
	private Thread readThread;

	public Messages(String name, String host) {
		super(name, host, 2);
	}

	protected void onConnected() {
		if (this.readThread != null) {
			this.readThread.interrupt();
		}
		(this.readThread = new Thread(() -> {
            do {
                try {
                    int packetID = Messages.this.in.readInt();
                    Messages.this.locker.lock();
                    try {
                        Messages.this.readPacket(packetID);
                    } finally {
                        Messages.this.locker.unlock();
                    }
                } catch (Exception ex) {
                    Messages.this.reconnect("Error on read message - "
                            + ex.getMessage());
                    break;
                }
            } while (Messages.this.connected);
            Messages.this.readThread = null;
        })).start();
	}

	private void readPacket(int packetID) {
		if (packetID != 0) {
			return;
		}
		try {
			String sender = this.in.readUTF();
			String tag = this.in.readUTF();
			String message = this.in.readUTF();
			Bukkit.getServer().getPluginManager()
					.callEvent(new CoreMessageEvent(sender, tag, message));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}