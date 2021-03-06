/*
 * This file is part of SayNoToMcLeaks.
 *
 * SayNoToMcLeaks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SayNoToMcLeaks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SayNoToMcLeaks.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.saynotomcleaks.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Commands implements CommandExecutor, TabCompleter {
	
	private final BukkitSayNoToMcLeaks plugin;

	public Commands(BukkitSayNoToMcLeaks plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmds, String commandLabel, String[] args) {
		if (!this.plugin.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "---------- [SayNoToMcLeaks v" + this.plugin.getDescription().getVersion() + " : By rexbut] ----------");
			sender.sendMessage(ChatColor.RED + "Plugin : Disable");
			if (!this.plugin.getServer().getOnlineMode()) {
				sender.sendMessage(ChatColor.RED + "Reason : This plugin only run in online mode.");
			}
			return true;
		}
		
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission(BukkitSayNoToMcLeaks.PERMISSION_RELOAD)) {
				this.plugin.onReload();
				sender.sendMessage(ChatColor.GREEN + "[SayNoToMcLeaks] Reloaded");
			} else {
				sender.sendMessage(ChatColor.RED + "[SayNoToMcLeaks] You don't have permission !");
			}
			return true;
		} else {
			sender.sendMessage(ChatColor.GREEN + "---------- [SayNoToMcLeaks v" + this.plugin.getDescription().getVersion() + " : By rexbut] ----------");
			sender.sendMessage(ChatColor.GREEN + "/mcleaks help : Help plugin");
			if (sender.hasPermission(BukkitSayNoToMcLeaks.PERMISSION_RELOAD)) {
				sender.sendMessage(ChatColor.GREEN + "/mcleaks reload : Reload plugin");
			}
			return true;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> suggests = new ArrayList<String>();
		if((alias.equalsIgnoreCase("saynotomcleaks") || alias.equalsIgnoreCase("mcleaks")) && args.length <= 1) {
			suggests.add("help");
			if (sender.hasPermission(BukkitSayNoToMcLeaks.PERMISSION_RELOAD)) {
				suggests.add("reload");
			}
		}
		return suggests;
	}
}
