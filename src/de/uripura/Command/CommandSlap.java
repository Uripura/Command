/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
	public boolean onCommand(CommandSender player, Command cmd, String label,
			String[] args) {

		Random random = new Random();
		PlayerList list = new PlayerList(player.getServer());

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

		Player pSlap = list.getPlayerFromName(args[0]);

		if (pSlap == null) {
			player.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-player-not-found"));
			return true;
		}

		// slap the player
		pSlap.setVelocity(new Vector(random.nextDouble() * 2.0 - 1,
				0.5,
				random.nextDouble() * 2.0 - 1));

		double health = pSlap.getHealth();
		if( health > 2)
			pSlap.setHealth(health-1);

		pSlap.playSound(pSlap, pSlap.getHurtSound(), 100, 1);
		
		pSlap.sendMessage(ChatColor.YELLOW + conf.getString(
				"msg.slap.notify-slap") + " " + player.getName());

		list.sendMsg(ChatColor.YELLOW + pSlap.getName() + " " + conf.getString(
				"msg.slap.notify-slap-announce") + player.getName(), pSlap);

		return true;
	}
}
