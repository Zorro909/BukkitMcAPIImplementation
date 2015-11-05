package de.jectrum.Bukkit.McAPI.Server;

import de.teamblackbird.mcapi.server.Server;
import de.teamblackbird.mcapi.server.ServerAPI;
import de.teamblackbird.mcapi.server.ServerBuilder;
import de.teamblackbird.mcapi.server.ServerBuilder.Data;
import de.teamblackbird.mcapi.server.ServerData;

public class BukkitServerApi implements ServerAPI {
		
	@Override
	public ServerData fetchServerData(Server server) {
		return new BukkitServerData(server,false);
	}

	@Override
	public ServerData fetchComplexServerData(Server server) {
		return new BukkitServerData(server,true);
	}

	@Override
	public ServerBuilder newServerBuilder() {
		return new BukkitServerBuilder();
	}

	@Override
	public Data newServerDataBuilder() {
		return new BukkitServerDataBuilder();
	}

}
