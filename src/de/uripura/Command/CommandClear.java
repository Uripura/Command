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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandClear implements CommandExecutor {

	private FileConfiguration conf;

	public CommandClear(FileConfiguration config) {
		conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		Player player = (Player) sender;
		org.bukkit.inventory.PlayerInventory inventory = player.getInventory();

		if (!player.hasPermission("command.clear")) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
			return true;
		}

		if (args.length == 0) {

			for (int i = 9; i < 36; i++) {
				inventory.setItem(i, null);
			}
			player.sendMessage(ChatColor.YELLOW + conf.getString(
					"msg.clear.notify-cleared"));
			return true;
		}

		if (args.length == 1) {

			// see if we got a flag
			if (args[0].equalsIgnoreCase("-a")) {
				inventory.clear();

				player.sendMessage(ChatColor.YELLOW + conf.getString(
						"msg.clear.notify-cleared-full"));
				return true;
			}
			// arg must be a player name
			else {
				Collection<? extends Player> playerList = player.getServer()
						.getOnlinePlayers();

				for (Player tmpPlay : playerList) {
					if (tmpPlay.getName().toLowerCase().contains(args[0]
							.toLowerCase())) {

						inventory = tmpPlay.getInventory();
						for (int i = 9; i < 36; i++) {
							inventory.setItem(i, null);
						}
						tmpPlay.sendMessage(ChatColor.YELLOW + conf.getString(
								"msg.clear.notify-cleared"));
						return true;
					}
				}
				player.sendMessage(ChatColor.RED + conf.getString(
						"msg.generic.error-player-not-found"));
				return true;
			}
		}

		if (args.length == 2) {
			boolean flag_all = false;
			String name = "";

			if (args[0].equalsIgnoreCase("-a")) {
				flag_all = true;
				name = args[1];
			}
			if (args[1].equalsIgnoreCase("-a")) {
				flag_all = true;
				name = args[0];
			}

			if (!flag_all)
				// player.sendMessage(ChatColor.RED + conf.getString(
				// "msg.generic.error-to-many-arguments"));
				return false;

			Collection<? extends Player> playerList = player.getServer()
					.getOnlinePlayers();

			for (Player tmpPlay : playerList) {
				if (tmpPlay.getName().toLowerCase().contains(name
						.toLowerCase())) {

					inventory = tmpPlay.getInventory();
					inventory.clear();
					tmpPlay.sendMessage(ChatColor.YELLOW + conf.getString(
							"msg.clear.notify-cleared-full"));
					player.sendMessage(ChatColor.RED + conf.getString(
							"msg.clear.notify-cleared"));
					return true;
				}
			}
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-player-not-found"));
			return true;
		}
		return false;
	}
}
