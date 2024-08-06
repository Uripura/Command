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

public class CommandMotd implements CommandExecutor {

	private FileConfiguration conf;

	public CommandMotd(FileConfiguration config) {
		conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (sender.hasPermission("comm.motd")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					conf.getString("msg.motd.motd")));
		} else {
			sender.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
		}
		return true;
	}
}
