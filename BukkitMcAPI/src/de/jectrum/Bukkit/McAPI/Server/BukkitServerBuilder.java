package de.jectrum.Bukkit.McAPI.Server;

import java.util.Optional;

import de.teamblackbird.mcapi.server.Server;
import de.teamblackbird.mcapi.server.ServerBuilder;

public class BukkitServerBuilder implements ServerBuilder {

	private String ip;
    private int port;
    private String version;
	
	@Override
	public Server build() {
		if(ip==null){return null;}
		Server s = new BukkitServer(ip, Optional.ofNullable(port), Optional.ofNullable(version));
		ip = null;
		port = 25565;
		version = null;
		return s;
	}

	@Override
	public ServerBuilder ip(String ip) {
		this.ip = ip;
		return this;
	}

	@Override
	public ServerBuilder port(int port) {
		this.port = port;
		return this;
	}

	@Override
	public ServerBuilder version(String version) {
		this.version = version;
		return this;
	}

}
