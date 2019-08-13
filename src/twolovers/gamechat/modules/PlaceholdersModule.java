package twolovers.gamechat.modules;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class PlaceholdersModule {
	public String replacePlaceholders(final Permissible permissible, String string) {
		if (permissible instanceof Player) {
			final Player player = (Player) permissible;

			string = PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', string)
					.replace("%player_name%", player.getName())
					.replace("%vault_prefix_color%", ChatColor.getLastColors(PlaceholderAPI.setPlaceholders(player, "%vault_prefix%"))));
		} else if (permissible instanceof CommandSender) {
			final CommandSender commandSender = (CommandSender) permissible;

			string = ChatColor.translateAlternateColorCodes('&', string)
					.replace("%player_name%", commandSender.getName())
					.replace("%vault_prefix_color%", "");
		}

		return string;
	}
}
