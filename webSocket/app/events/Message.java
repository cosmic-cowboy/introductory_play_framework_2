package events;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;

public class Message implements Event, Serializable {

	private static final long serialVersionUID = 1L;

	final String username;
	final String x;
	final String y;
	final WebSocketEvent event;
	final WebSocket.Out<JsonNode> channel;
	
	@Override
	public WebSocketEvent getEventType() {
		return event;
	}

	public Message(String username, String x, String y, WebSocketEvent event,
			Out<JsonNode> channel) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.event = event;
		this.channel = channel;
	}


	// Getter
	public String getUsername() {
		return username;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public WebSocketEvent getEvent() {
		return event;
	}

	public WebSocket.Out<JsonNode> getChannel() {
		return channel;
	}

}
