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
		PlayerList list = new PlayerList(sender.getServer());

		if (!player.hasPermission("comm.tp")) {
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
				Player tmpPlay = list.getPlayerFromName(args[0]);

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

		if (args.length == 2)

		{
			Player p1, p2;

			p1 = list.getPlayerFromName(args[0]);
			p2 = list.getPlayerFromName(args[1]);

			if (p1 != null && p2 != null) {
				p1.teleport(p2);
			} else {
				sender.sendMessage(ChatColor.RED + conf.getString(
						"msg.generic.error-player-not-found"));
			}
			return true;
		}

		if (args.length == 3)

		{
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