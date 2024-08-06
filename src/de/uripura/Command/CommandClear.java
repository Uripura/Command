/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

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

		PlayerList list = new PlayerList(sender.getServer());
		org.bukkit.inventory.PlayerInventory inventory;

		// if sender is the console
		if (sender instanceof Player) {
			if (args.length == 1) {
				Player tmp = list.getPlayerFromName(args[0]);
				inventory = tmp.getInventory();

				if (tmp != null) {
					for (int i = 9; i < 36; i++) {
						inventory.setItem(i, null);
					}
					tmp.sendMessage(ChatColor.YELLOW + conf.getString(
							"msg.clear.notify-cleared"));
				}
				sender.sendMessage(ChatColor.YELLOW + conf.getString(
						"msg.clear.notify-cleared"));

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

				Player tmpPlay = list.getPlayerFromName(name);

				if (!flag_all)
					return false;

				if (tmpPlay == null) {
					sender.sendMessage(ChatColor.RED + conf.getString(
							"msg.generic.error-player-not-found"));
					return true;
				}

				tmpPlay.getInventory().clear();

				tmpPlay.sendMessage(ChatColor.YELLOW + conf.getString(
						"msg.clear.notify-cleared-full"));
				sender.sendMessage(ChatColor.RED + conf.getString(
						"msg.clear.notify-cleared"));
				return true;
			}
			return false;
		}

		// if the sender is a player
		Player player = (Player) sender;
		inventory = player.getInventory();

		if (!player.hasPermission("comm.clear")) {
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
				Player tmpPlay = list.getPlayerFromName(args[0]);

				if (tmpPlay == null) {
					player.sendMessage(ChatColor.RED + conf.getString(
							"msg.generic.error-player-not-found"));
					return true;
				}

				inventory = tmpPlay.getInventory();
				for (int i = 9; i < 36; i++) {
					inventory.setItem(i, null);
				}
				tmpPlay.sendMessage(ChatColor.YELLOW + conf.getString(
						"msg.clear.notify-cleared"));
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

			Player tmpPlay = list.getPlayerFromName(name);

			if (!flag_all)
				return false;

			if (tmpPlay == null) {
				player.sendMessage(ChatColor.RED + conf.getString(
						"msg.generic.error-player-not-found"));
				return true;
			}

			tmpPlay.getInventory().clear();

			tmpPlay.sendMessage(ChatColor.YELLOW + conf.getString(
					"msg.clear.notify-cleared-full"));
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.clear.notify-cleared"));
			return true;

		}
		return false;
	}
}
