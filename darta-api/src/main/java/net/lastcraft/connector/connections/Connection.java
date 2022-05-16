package net.lastcraft.connector.connections;

import net.lastcraft.dartaapi.loader.DartaAPI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

@Deprecated
public abstract class Connection {
	protected Socket socket;
	public ObjectInputStream in;
	protected final ReentrantLock locker = new ReentrantLock();
	protected ObjectOutputStream out;
	protected String host;
	protected int port;
	protected String name;
	protected int type;
	boolean connected;
	private Thread reconnectThread;

	public ObjectInputStream getIn() {
		return this.in;
	}

	public ObjectOutputStream getOut() {
		return this.out;
	}

	public Connection(String name, String host, int type) {
		this.name = name;
		this.host = host;
		this.port = 50000;
		this.type = type;
		connect();
	}

	private void connect() {
		this.locker.lock();
		try {
			this.socket = new Socket(InetAddress.getByName(this.host), this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.out.writeUTF("QwErTy");
			this.out.writeInt(this.type);
			this.out.writeUTF(this.name);
			this.out.flush();
			this.in = new ObjectInputStream(this.socket.getInputStream());
			DartaAPI.getInstance().getLogger().info("#" + getClass().getSimpleName() + " Connected to Core");
			onConnected();
			this.connected = true;
		} catch (Exception ex) {
			reconnect("Error on connecting to core: " + ex.getMessage());
		} finally {
			this.locker.unlock();
		}
	}

	protected void onConnected() {
	}

	protected void reconnect(String cause) {
		if (Thread.currentThread() == this.reconnectThread) {
			return;
		}
		this.connected = false;
		DartaAPI.getInstance()
				.getLogger()
				.info("#" + getClass().getSimpleName()
						+ " | Connection closed. Cause - " + cause);
		if (this.reconnectThread != null) {
			this.reconnectThread.interrupt();
			this.reconnectThread = null;
		}
		startReconnect();
	}

	private synchronized void startReconnect() {
		this.reconnectThread = new Thread(() -> {
            while (!Connection.this.connected) {
                try {
                    if (Connection.this.socket != null) {
                        Connection.this.socket.close();
                    }
                    if (Connection.this.in != null) {
                        Connection.this.in.close();
                    }
                    if (Connection.this.out != null) {
                        Connection.this.out.close();
                    }
                } catch (Exception ignore) {
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {
                }
                Connection.this.connect();
            }
        });
		this.reconnectThread.start();
	}

	public void sendPacket(byte[] bytes) throws IOException {
		if (!this.connected) {
			throw new IOException("Not connected to Core");
		}
		this.locker.lock();
		try {
			this.out.write(bytes);
			this.out.flush();
		} catch (Exception ex) {
			reconnect(ex.getMessage());
			throw ex;
		} finally {
			this.locker.unlock();
		}
	}
}