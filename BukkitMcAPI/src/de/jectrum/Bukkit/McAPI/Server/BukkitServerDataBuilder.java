package de.jectrum.Bukkit.McAPI.Server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import de.teamblackbird.mcapi.APIResponse;
import de.teamblackbird.mcapi.server.ServerBuilder.Data;
import de.teamblackbird.mcapi.server.ServerData;
import de.teamblackbird.mcapi.server.ServerMotd;

public class BukkitServerDataBuilder implements Data {

	BukkitServerData sd = new BukkitServerData();
	boolean complex = false;
	ArrayList<String> set = new ArrayList<String>();
	ArrayList<String> s = new ArrayList<String>();
	ArrayList<String> a = new ArrayList<String>();
	
	public BukkitServerDataBuilder(){
		s.add("software");
		s.add("protocol");
		s.add("motd");
		s.add("ping");
		s.add("favicon");
		
		a.add("hostname");
		a.add("version");
		a.add("maxPlayers");
		a.add("onlinePlayers");
		a.add("expires");
		a.add("lastUpdated");
		a.add("timezone");
		a.add("response");
	}
	
	@Override
	public ServerData build() {
		if(complex){
			if(!set.containsAll(s) || !set.containsAll(a)){
				return null;
			}
		}else{
			if(!set.containsAll(a)){
				return null;
			}
		}
		BukkitServerData sd2 = sd;
		sd = new BukkitServerData();
		return sd2;
	}

	@Override
	public Data hostname(String hostname) {
		sd.hostname = hostname;
		set.add("hostname");
		return this;
	}

	@Override
	public Data software(String software) {
		sd.software = Optional.ofNullable(software);
		complex = true;
		set.add("software");
		return this;
	}

	@Override
	public Data version(String version) {
		sd.version = Optional.ofNullable(version);
		set.add("version");
		return this;
	}

	@Override
	public Data protocol(int protocol) {
		sd.protocol = Optional.ofNullable(protocol);
		complex = true;
		set.add("protocol");
		return this;
	}

	@Override
	public Data maxPlayers(int maxPlayers){
		sd.max = maxPlayers;
		set.add("maxPlayers");
		return this;
	}

	@Override
	public Data onlinePlayers(int onlinePlayers) {
		sd.online = onlinePlayers;
		set.add("onlinePlayers");
		return this;
	}

	@Override
	public Data motd(ServerMotd motd) {
		sd.motd = Optional.ofNullable(motd);
		complex = true;
		set.add("motd");
		return this;
	}

	@Override
	public Data ping(int ping) {
		sd.ping = Optional.ofNullable(ping);
		complex = true;
		set.add("ping");
		return this;
	}

	@Override
	public Data expires(Date expires) {
		sd.expires = expires;
		set.add("expires");
		return this;
	}

	@Override
	public Data lastUpdated(Date lastUpdated) {
		sd.lastUpdated = lastUpdated;
		set.add("lastUpdated");
		return this;
	}

	@Override
	public Data timezone(TimeZone zone) {
		sd.timezone = zone;
		set.add("timezone");
		return this;
	}

	@Override
	public Data response(APIResponse response) {
		sd.response = response;
		set.add("response");
		return this;
	}

	@Override
	public Data favicon(String favicon) {
		sd.favicon = Optional.ofNullable(favicon);
		complex = true;
		set.add("favicon");
		return this;
	}

}
