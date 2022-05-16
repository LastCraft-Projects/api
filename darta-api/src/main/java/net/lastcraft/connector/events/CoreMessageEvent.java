package net.lastcraft.connector.events;

import lombok.Getter;
import net.lastcraft.api.event.DEvent;

@Getter
@Deprecated
public class CoreMessageEvent extends DEvent {
	private final String sender;
	private final String tag;
	private final String message;

	public CoreMessageEvent(String sender, String tag, String message) {
		super(true);
		this.sender = sender;
		this.tag = tag;
		this.message = message;
	}
}