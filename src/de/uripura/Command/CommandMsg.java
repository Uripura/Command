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

public class CommandMsg implements CommandExecutor {

	private FileConfiguration conf;

	public CommandMsg(FileConfiguration config) {
		conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		Player player = (Player) sender;
		PlayerList list = new PlayerList(sender.getServer());

		if (!player.hasPermission("command.msg")) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
			return true;
		}

		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-to-few-arguments"));
			return false;
		}

		String message = "";

		Player pMsg = list.getPlayerFromName(player.getName());

		if (pMsg == null) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-player-not-found"));
			return true;
		}

		for (int i = 1; i < args.length; i++) {
			if (i < args.length) {
				message = message + args[i] + " ";
			} else {
				message = message + args[i];
			}
		}

		pMsg.sendMessage(ChatColor.GRAY + player.getName() + " " + conf
				.getString("msg.msg.whisper-from") + " " + ChatColor.RESET
				+ message);

		player.sendMessage(ChatColor.GRAY + player.getName() + " " + conf
				.getString("msg.msg.whisper-to") + " " + pMsg.getName() + ": "
				+ ChatColor.RESET + message);
		return true;
	}

}
