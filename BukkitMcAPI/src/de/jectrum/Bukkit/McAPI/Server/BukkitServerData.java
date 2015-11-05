package de.jectrum.Bukkit.McAPI.Server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.jectrum.Bukkit.McAPI.HTTPManager.Connection;
import de.jectrum.Bukkit.McAPI.HTTPManager.InetManager;
import de.teamblackbird.mcapi.APIResponse;
import de.teamblackbird.mcapi.server.Server;
import de.teamblackbird.mcapi.server.ServerData;
import de.teamblackbird.mcapi.server.ServerMotd;

public class BukkitServerData implements ServerData {

	String apiLocation = "http://mcapi.de/api/server";
	String ip;
	int port;
	APIResponse response;
	String hostname;
	int online = -1;
	int max = -1;
	Date lastUpdated;
	TimeZone timezone;
	Date expires;
	boolean complex = false;
	Optional<String> software = Optional.ofNullable(null);
	Optional<String> version = Optional.ofNullable(null);
	Optional<Integer> protocol = Optional.ofNullable(null);
	Optional<ServerMotd> motd = Optional.ofNullable(null);
	Optional<String> favicon = Optional.ofNullable(null);
	Optional<Integer> ping = Optional.ofNullable(null);
	
	
	BukkitServerData(){}
	
	BukkitServerData(Server server, boolean complex) {
		this.complex = complex;
		final BukkitServerData bsd = this;
		new Thread(new Runnable(){

			@Override
			public void run() {
				ip = server.getIp();
				port = server.getPort();
				version = server.getVersion();
				Connection con = null;
				try {
					con = InetManager.openConnection(apiLocation + (!complex ? ("/" + ip + "/" + port + "/" + version) : ""));
					System.out.println(apiLocation + (!complex ? ("/" + ip + "/" + port + "/" + version) : ""));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bukkit.getLogger().info("Connection Established");
				String got = null;
				if(complex){
					HashMap<String,String> properties = new HashMap<String,String>();
					properties.put("ip", ip);
					if(port!=25565)properties.put("port", String.valueOf(port));
					if(version.get()!="1.8")properties.put("version", version.get());
					try {
						con.initPost(properties);
						got = con.post();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					HashMap<String,String> properties = new HashMap<String,String>();
					try {
						con.initGet(false, properties);
						got = con.get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try{
				Bukkit.getLogger().info("Got: \n" + got);
				Gson g = new Gson();
				Bukkit.getLogger().info("Got Reply");
				JsonObject json = g.fromJson(got, JsonObject.class);
				Bukkit.getLogger().info("Parsed Reply");
				JsonObject result = json.getAsJsonObject("result");
				if(result.get("status").getAsString().equalsIgnoreCase("successfully")){
					response = APIResponse.SUCCESSFULL;
				}else if(result.get("status").getAsString().equalsIgnoreCase("cant_connect")){
					response = APIResponse.OFFLINE;
					Bukkit.getLogger().info("ServerDataFetchCompleteEvent");
					return;
				}else{
					response = APIResponse.UNKOWN;
					Bukkit.getLogger().info("ServerDataFetchCompleteEvent");
					return;
				}
				hostname = json.get("hostname").getAsString();
				JsonObject players = json.getAsJsonObject("players");
				online = players.getAsJsonObject("online").getAsInt();
				max = players.getAsJsonObject("max").getAsInt();
				JsonObject d = json.getAsJsonObject("updated");
				lastUpdated = new Date(d.getAsJsonObject("time").getAsString());
				expires = new Date(d.getAsJsonObject("expires").getAsString());
				timezone = TimeZone.getTimeZone(d.getAsJsonObject("zone").getAsString());
				Bukkit.getLogger().info("Got Simple API Request Values");
				if(complex){
					JsonObject software = json.getAsJsonObject("software");
					bsd.software = Optional.ofNullable(software.getAsJsonObject("name").getAsString());
					bsd.version = Optional.ofNullable(software.getAsJsonObject("version").getAsString());
					protocol = Optional.ofNullable(json.getAsJsonObject("protocol").getAsInt());
					JsonObject list = json.getAsJsonObject("list");
					BukkitServerMotd m = new BukkitServerMotd(list.getAsJsonObject("motd").getAsString());
					motd = Optional.ofNullable(m);
					favicon = Optional.ofNullable(list.getAsJsonObject("favicon").getAsString());
					ping = Optional.ofNullable(list.getAsJsonObject("ping").getAsInt());
					Bukkit.getLogger().info("Got Complex API Request Values");
				}
				Bukkit.getPluginManager().callEvent(new ServerDataFetchCompleteEvent(bsd));
				Bukkit.getLogger().info("ServerDataFetchCompleteEvent");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	private void waitFor(Object o) {
		if(response==APIResponse.ERROR||response==APIResponse.OFFLINE||response==APIResponse.UNKOWN)return;
		while(o==null || (o instanceof Optional && !((Optional)o).isPresent())){
			try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void waitFor(int o) {
		if(response==APIResponse.ERROR||response==APIResponse.OFFLINE||response==APIResponse.UNKOWN)return;
		while(o==-1){
			try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getHostname() {
		if(hostname==null){
			waitFor(hostname);
		}
		return hostname;
	}

	@Override
	public Optional<String> getSoftware() {
		if(complex && software==null){
			waitFor(software);
		}
		return software;
	}

	@Override
	public Optional<String> getVersion() {
		if(complex && version==null){
			waitFor(version);
		}
		return version;
	}

	@Override
	public Optional<Integer> getProtocol() {
		if(complex && protocol==null){
			waitFor(protocol);
		}
		return protocol;
	}

	@Override
	public int getMaxPlayers() {
		if(max==-1){
			waitFor(max);
		}
		return max;
	}

	@Override
	public int getOnlinePlayers() {
		if(online==-1){
			waitFor(online);
		}
		return online;
	}

	@Override
	public Optional<ServerMotd> getMotd() {
		if(complex && motd==null){
			waitFor(motd);
		}
		return motd;
	}

	@Override
	public Optional<Integer> getPing() {
		if(complex&&ping==null){
			waitFor(ping);
		}
		return ping;
	}

	@Override
	public Date getLastUpdated() {
		if(lastUpdated==null){
			waitFor(lastUpdated);
		}
		return lastUpdated;
	}

	@Override
	public TimeZone getTimeZone() {
		if(timezone==null){
			waitFor(timezone);
		}
		return timezone;
	}

	@Override
	public APIResponse getResponse() {
		if(response==null){
			waitFor(response);
		}
		return response;
	}

	@Override
	public Date getExpires() {
		if(expires==null){
			waitFor(expires);
		}
		return expires;
	}

	@Override
	public Optional<String> getRawFavicon() {
		return favicon;
	}

}
