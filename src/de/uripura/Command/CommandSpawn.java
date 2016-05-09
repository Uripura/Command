package de.uripura.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

	FileConfiguration conf;

	public CommandSpawn(FileConfiguration config) {
		this.conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(conf.getString("msg.generic.error-player-only"));
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("command.spawn")) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
			return true;
		}

		if (!player.hasPermission("command.spawn")) {
			player.sendMessage(conf.getString(ChatColor.RED
					+ "msg.generic.error-permission"));
			return true;
		}

		Location loc = player.getWorld().getSpawnLocation();
		player.teleport(loc);

		return true;
	}
}
