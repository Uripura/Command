/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandTeleport implements CommandExecutor {

	FileConfiguration conf;

	public CommandTeleport(FileConfiguration config) {
		this.conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-player-only"));
			return true;
		}

		Player player = (Player) sender;
		Location location;

		if (!player.hasPermission("command.tp")) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-to-few-arguments"));
			return true;
		}

		if (args.length == 1) {
			if (args[0].contains(",")) {

				// Split argument and extract coordinates
				String[] strCoords = args[0].split(",");

				if (strCoords.length != 3) {
					player.sendMessage(ChatColor.YELLOW + conf.getString(
							"msg.teleport.num-coords"));
					return true;
				}
				try {
					location = new Location(player.getWorld(),
							Double.parseDouble(strCoords[0]),
							Double.parseDouble(strCoords[1]),
							Double.parseDouble(strCoords[2]));
					player.teleport(location);

				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.YELLOW + conf.getString(
							"msg.teleport.not-num"));
					return true;
				}
				return true;
			} else {
				// Go through every online player and see if we got a match
				Collection<? extends Player> playerList = player.getServer()
						.getOnlinePlayers();

				for (Player tmpPlay : playerList) {
					if (tmpPlay.getName().toLowerCase().contains(args[0]
							.toLowerCase())) {
						// Check if we want to the player,
						// or the player wants to us
						if (label.equalsIgnoreCase("tphere")) {
							tmpPlay.teleport(player.getLocation());
						} else {
							player.teleport(tmpPlay.getLocation());
						}
						return true;
					}
				}
				// No matching player was found
				player.sendMessage(ChatColor.RED + conf.getString(
						"msg.generic.error-player-not-found"));
				return true;
			}
		}

		if (args.length == 2) {
			Collection<? extends Player> playerList = player.getServer()
					.getOnlinePlayers();

			Player p1 = null, p2 = null;

			for (Player tmpPlay : playerList) {
				if (tmpPlay.getName().toLowerCase().contains(args[0]
						.toLowerCase())) {
					p1 = tmpPlay;
				}
				if (tmpPlay.getName().toLowerCase().contains(args[1]
						.toLowerCase())) {
					p2 = tmpPlay;
				}
			}
			if (p1 != null && p2 != null) {
				p1.teleport(p2);
			} else {
				sender.sendMessage(ChatColor.RED + conf.getString(
						"msg.generic.error-player-not-found"));
			}
			return true;
		}

		if (args.length == 3) {
			try {
				location = new Location(player.getWorld(),
						Double.parseDouble(args[0]),
						Double.parseDouble(args[1]),
						Double.parseDouble(args[2]));
				player.teleport(location);
				return true;

			} catch (NumberFormatException e) {
				player.sendMessage(ChatColor.RED + conf.getString(
						"msg.teleport.error-not-num"));
				return true;
			}
		}
		return false;
	}

}