import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Friends extends JavaPlugin implements Listener {

	/*
	 * Friends plugin! owoohohoohohoohowohoo it's finally here! The best friends plugin out there.
	 */

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		for(String str : getConfig().getConfigurationSection("friends").getKeys(false)) {
			friend.put(str, getConfig().getString("friends." + str));
		}
	}
	
	public void onDisable() {
		for(Entry<String, String> map : friend.entrySet()) {
			getConfig().set("friends." + map.getKey(), map.getValue());
		}
		
		saveConfig();
	}
	
    HashMap<String, String> friend = new HashMap<String, String>();
    
	public ArrayList<String> male = new ArrayList<String>();
	public ArrayList<String> female = new ArrayList<String>();
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	if(friend.containsKey(p.getName())) {
    		Player t = Bukkit.getPlayer(friend.get(p.getName()));
    		
    		if(t != null) {
    			t.sendMessage(ChatColor.YELLOW + p.getName() + " has joined.");
    		}
    	}
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	if(friend.containsKey(p.getName())) {
    		Player t = Bukkit.getPlayer(friend.get(p.getName()));
    		
    		if(t != null) {
    			t.sendMessage(ChatColor.YELLOW + p.getName() + " has left.");
    		}
    	}
    }
   
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((cmd.getName().equalsIgnoreCase("friend")) && ((sender instanceof Player))) {
			Player p = (Player) sender;
			if (p.hasPermission("friend.use")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.GRAY + "Usage: /friend [name]");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target != null) {
					getConfig().set(target.getName(), p.getName());
					saveConfig();
					return true;
				}
				
				String name = args[0];
				
				p.sendMessage(ChatColor.RED + name + " is not online!");
				return true;
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("remove")) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(target != null) {
						friend.remove(target.getName());
						p.sendMessage(ChatColor.YELLOW + "You have removed " + target.getName() + " from your friends list!");
						target.sendMessage(ChatColor.YELLOW + p.getName() + " has removed you from their friend list.");
					return true;
				}
			}
		}
			
		if(args.length == 1) {
		   if(args[0].equalsIgnoreCase("accept")) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target != null) {
				if (getConfig().getString(p.getName())
						.equalsIgnoreCase(target.getName())) {
					friend.put(target.getName(), p.getName());
					friend.put(p.getName(), target.getName());
					p.sendMessage(ChatColor.YELLOW + "You have became friends with " + target.getName());
					target.sendMessage(ChatColor.YELLOW + "You have became friends with " + p.getName());
				}
					return true;
				}
			}
		
		if(args.length == 1) {
			   if(args[0].equalsIgnoreCase("decline")) {
						// TODO: Add decline feature
						return true;
					}
				}
}
		
		return false;
		
		}
		return false;
	}	
}
