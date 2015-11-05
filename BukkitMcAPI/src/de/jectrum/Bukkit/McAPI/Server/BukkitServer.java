package de.jectrum.Bukkit.McAPI.Server;

import java.util.Optional;

import de.teamblackbird.mcapi.server.Server;
import de.teamblackbird.mcapi.server.ServerBuilder;
import de.teamblackbird.mcapi.server.ServerData;

public class BukkitServer implements Server {

	private String ip;
	private Optional<Integer> port;
	private Optional<String> version;
	private long lastGot = -1;
	private ServerData last;
	
	BukkitServer(String ip, Optional<Integer> port, Optional<String> version){
		this.ip = ip;
		this.port = port;
		this.version = version;
	}
	
	ServerData getLastServerData(){
		if(lastGot==-1)return null;
		if(System.currentTimeMillis() - lastGot <= 60000*5){
			return last;
		}
		return null;
	}
	
	void setLastServerData(ServerData sd){
		last = sd;
		lastGot = System.currentTimeMillis();
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public int getPort() {
		if(port.isPresent()){
			return port.get();
		}else{
			return 25565;
		}
	}

	@Override
	public Optional<String> getVersion() {
		if(version.isPresent()){
			return version;
		}else{
			return Optional.of("1.8");
		}
	}

}
