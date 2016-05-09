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

		if (sender.hasPermission("command.motd")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					conf.getString("msg.motd.motd")));
		} else {
			sender.sendMessage(ChatColor.RED + conf.getString(
					"msg.generic.error-permission"));
		}
		return true;
	}
}
