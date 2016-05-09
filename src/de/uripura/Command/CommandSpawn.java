/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
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
