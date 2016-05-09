/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

import java.util.Collection;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class PlayerList {

	Collection<? extends Player> playerList;

	public PlayerList(Server srv) {
		playerList = srv.getOnlinePlayers();

	}

	public Player getPlayerFromName(String name) {

		for (Player tmpPlay : playerList) {
			if (tmpPlay.getName().toLowerCase().contains(name.toLowerCase()))
				return tmpPlay;
		}
		return null;
	}

	public void sendMsg(String msg) {
		for (Player tmpPlay : playerList) {
			tmpPlay.sendMessage(msg);
		}
	}

	public void sendMsg(String msg, Player play) {
		for (Player tmpPlay : playerList) {
			if (!play.equals(tmpPlay)) {
				tmpPlay.sendMessage(msg);
			}
		}
	}
}
