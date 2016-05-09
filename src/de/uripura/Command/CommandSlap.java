/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

import java.util.Collection;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CommandSlap implements CommandExecutor {

	private FileConfiguration conf;

	public CommandSlap(FileConfiguration config) {
		this.conf = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		Random random = new Random();
		Player player = (Player) sender;

		if (!player.hasPermission("command.slap")) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-to-few-arguments"));
			return false;
		}
		if (args.length > 1) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-to-many-arguments"));
			return false;
		}

		Collection<? extends Player> playerList = player.getServer()
				.getOnlinePlayers();

		Player pSlap = null;
		// Find a player to slap
		for (Player tmpPlay : playerList) {
			if (tmpPlay.getName().toLowerCase().contains(args[0]
					.toLowerCase())) {
				pSlap = tmpPlay;
				break;
			}
		}

		if (pSlap == null) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-player-not-found"));
			return true;
		}

		// slap the player
		pSlap.setVelocity(new Vector(random.nextDouble() * 2.0 - 1,
				random.nextDouble() * 1,
				random.nextDouble() * 2.0 - 1));
		pSlap.sendMessage(ChatColor.YELLOW + conf.getString(
				"msg.slap.notify-slap") + " " + player.getName());

		// inform other players
		for (Player tmpPlay : playerList) {
			if (!tmpPlay.equals(pSlap)) {
				tmpPlay.sendMessage(ChatColor.YELLOW + pSlap.getName() + " "
						+ conf.getString("msg.slap.notify-slap-announce")
						+ " by " + player.getName());
			}
		}
		return true;
	}
}
