package de.jectrum.Bukkit.McAPI;

import de.jectrum.Bukkit.McAPI.Server.BukkitServerApi;
import de.teamblackbird.mcapi.McImplementation;
import de.teamblackbird.mcapi.server.ServerAPI;
import de.teamblackbird.mcapi.user.UserAPI;
import de.teamblackbird.mcapi.vote.VotifierAPI;

public class BukkitMcAPI implements McImplementation {

	@Override
	public ServerAPI getServerImplementation() {
		return new BukkitServerApi();
	}

	@Override
	public UserAPI getUserImplementation() {
		return null;
	}

	@Override
	public VotifierAPI getVotifierImplementation() {
		return null;
	}

}
