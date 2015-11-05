package de.jectrum.Bukkit.McAPI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.jectrum.Bukkit.McAPI.Server.ServerDataFetchCompleteEvent;
import de.teamblackbird.mcapi.server.Server;
import de.teamblackbird.mcapi.server.ServerAPI;
import de.teamblackbird.mcapi.server.ServerBuilder;
import de.teamblackbird.mcapi.server.ServerData;

public class Plugin extends JavaPlugin implements Listener {

	BufferedImage fileObject;
	String motd;
	int max = -1, online = -1;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		ServerAPI servers = new BukkitMcAPI().getServerImplementation();
		ServerBuilder builder = servers.newServerBuilder();
		Server gommehd = builder.ip("gommehd.net").build();
		servers.fetchComplexServerData(gommehd);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			@Override
			public void run() {
				servers.fetchComplexServerData(gommehd);
			}
			
		}, 20*60*5+1, 20*60*5+1);
	}

	@EventHandler
	public void onFind(ServerDataFetchCompleteEvent e){
		Bukkit.broadcastMessage("Got Server Data!");
		ServerData sd = e.getServerData();
		try {
			fileObject = toBufferedImage(sd.getFavicon().get());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		motd = sd.getMotd().get().getMotd();
		max = sd.getMaxPlayers();
		online = sd.getOnlinePlayers();
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onServerListPing(ServerListPingEvent e) {
		Bukkit.broadcastMessage("ServerListPingEvent!");
		if (fileObject != null) {
			try {
				e.setServerIcon(Bukkit.loadServerIcon(fileObject));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Bukkit.broadcastMessage("Set Favicon!");
		}
		if(motd!=null){
			e.setMotd(motd + "\nPlayers: " + online);
			Bukkit.broadcastMessage("Set MOTD!");
		}
		if(max!=-1){
			e.setMaxPlayers(max);
		}
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
	
}
