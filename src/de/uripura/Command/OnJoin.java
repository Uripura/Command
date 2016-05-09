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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

	private FileConfiguration conf;

	public OnJoin(FileConfiguration config) {
		conf = config;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String onlineList = "";

		player.sendMessage(conf.getString("msg.motd.motd"));

		Collection<? extends Player> playerList = player.getServer()
				.getOnlinePlayers();

		int i = 1;

		for (Player tmpPlay : playerList) {
			if (i < playerList.size()) {
				onlineList = onlineList + tmpPlay.getName() + ", ";
			} else {
				onlineList = onlineList + tmpPlay.getName();
			}
			i++;
		}
		player.sendMessage(ChatColor.YELLOW + conf.getString("msg.motd.online")
				+ " " + onlineList);
	}
}
