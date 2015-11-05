package de.jectrum.Bukkit.McAPI.Server;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.teamblackbird.mcapi.server.ServerData;

public class ServerDataFetchCompleteEvent extends Event {

	private ServerData sd;
	
	public static HandlerList handlers = new HandlerList();
	
	public ServerDataFetchCompleteEvent(ServerData sd){
		this.sd = sd;
	}
	
	public ServerData getServerData(){
		return sd;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}

}
