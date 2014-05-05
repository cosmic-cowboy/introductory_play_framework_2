package events;

public enum WebSocketEvent {
	
	JOIN {
		@Override
		public String event() { return "JOIN"; } 
	},
	MESSAGE {
		@Override
		public String event() { return "MESSAGE"; }
	},
	QUIT {
		@Override
		public String event() { return "QUIT"; }
	};
	
	abstract public String event();
}
