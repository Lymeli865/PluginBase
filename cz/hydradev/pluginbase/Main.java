package cz.hydradev.pluginbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public final String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "ExamplePlugin" + ChatColor.DARK_AQUA + "] " + ChatColor.RESET;
	private boolean enableVersionChecker = true;
	public final String currentVersion = "1.0"; //current version of this plugin(used for checking for update)
	public final int resourceId = 0; //change to spigotmc resource id
	
	public void onLoad() {
		config();
		sendToConsole(ChatColor.YELLOW + "ExamplePlugin has been Loaded.");
	}
	
	public void onEnable() {
		sendToConsole(ChatColor.GREEN + "ExamplePlugin has been Enabled.");
		versionChecker();
	}
	
	public void onDisable() {
		sendToConsole(ChatColor.RED + "ExamplePlugin has been Disabled.");
	}
	
	private void config() {
		this.getConfig().addDefault("VersionChecker", true);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        enableVersionChecker = this.getConfig().getBoolean("VersionChecker");
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { //called when someone sends command
		if (cmd.getName().equalsIgnoreCase("examplecommand")) { //example command
			sendTo(sender, ChatColor.RED + "this is example message.");
			return true;
		}
		return false;
    }
	
	public void sendToConsole(String msg) { //sends message to console
		getServer().getConsoleSender().sendMessage(prefix + msg);
	}
	
	public void sendTo(CommandSender sender, String msg) { //sends message to sender
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(prefix + msg);
		}else {
			sendToConsole(msg);
		}
	}
	
	private void versionChecker() { //checks for newer versions
		if (enableVersionChecker) {
			try {
	        	HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openConnection();
	        	connection.setDoOutput(true);
	        	String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
	        	if (!version.equals(currentVersion)) {
	        		sendToConsole(ChatColor.RED + "New Version " + ChatColor.AQUA + version + ChatColor.RED + " Was Relased!");
	        	} else {
	        		sendToConsole(ChatColor.GREEN + "You Have Lasted Version!");
	        	}
	    	} catch (IOException e) {
	    		sendToConsole(ChatColor.RED + "ERROR: Could not make connection to SpigotMC!");
	    	}
		}
    }
}