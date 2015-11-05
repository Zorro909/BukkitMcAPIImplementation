package de.jectrum.Bukkit.McAPI.Server;

import org.bukkit.ChatColor;

import de.teamblackbird.mcapi.server.ServerMotd;

public class BukkitServerMotd implements ServerMotd {

	String rawMotd = "";
	String motd = "";
	
	public BukkitServerMotd(String string) {
		rawMotd = string;
		motd = ChatColor.translateAlternateColorCodes('§', rawMotd);
	}

	@Override
	public String getRawMotd() {
		return rawMotd;
	}

	@Override
	public String getMotd() {
		return motd;
	}

}
