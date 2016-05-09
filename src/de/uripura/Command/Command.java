/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package de.uripura.Command;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Command extends JavaPlugin {

	private Logger log = Logger.getLogger("Minecraft");

	FileConfiguration config = getConfig();

	@Override
	public void onEnable() {
		config.options().copyDefaults(true);
		saveConfig();

		getServer().getPluginManager().registerEvents(new OnJoin(config), this);

		if (config.getBoolean("enable.clear")) {
			this.getCommand("clear").setExecutor(new CommandClear(config));
		}

		if (config.getBoolean("enable.motd")) {
			this.getCommand("motd").setExecutor(new CommandMotd(config));
		}

		if (config.getBoolean("enable.msg")) {
			this.getCommand("msg").setExecutor(new CommandMsg(config));
		}

		if (config.getBoolean("enable.slap")) {
			this.getCommand("slap").setExecutor(new CommandSlap(config));
		}

		if (config.getBoolean("enable.spawn")) {
			this.getCommand("spawn").setExecutor(new CommandSpawn(config));
		}

		if (config.getBoolean("enable.teleport")) {
			this.getCommand("tp").setExecutor(new CommandTeleport(config));
		}
	}

	@Override
	public void onDisable() {

	}

	public void logMessage(String msg) {
		PluginDescriptionFile pdFile = this.getDescription();
		this.log.info("[" + pdFile.getName() + "] " + msg);
	}

	public void errorMessage(String msg) {
		PluginDescriptionFile pdFile = this.getDescription();
		this.log.severe("[" + pdFile.getName() + "] " + msg);
	}
}
