package twolovers.gamechat.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;
import twolovers.gamechat.modules.GlobalModule;
import twolovers.gamechat.modules.PlaceholdersModule;
import twolovers.gamechat.modules.ReformatterModule;
import twolovers.gamechat.objects.ChatPlayer;

import java.util.Set;

public class AsyncPlayerChatListener implements Listener {
	private final GlobalModule globalModule;
	private final ChatPlayerModule chatPlayerModule;
	private final PlaceholdersModule placeholdersModule;
	private final ReformatterModule reformatterModule;

	public AsyncPlayerChatListener(final ModuleManager moduleManager) {
		this.globalModule = moduleManager.getGlobalModule();
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
		this.placeholdersModule = moduleManager.getPlaceholdersModule();
		this.reformatterModule = moduleManager.getReformatterModule();
	}

	@EventHandler(ignoreCancelled = true)
	public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
		String message = event.getMessage();

		message = reformatterModule.formatMessage(message);

		if (globalModule.isEnabled()) {
			String format = globalModule.getMessage();

			if (format.equals(""))
				event.setCancelled(true);
			else {
				final Player player = event.getPlayer();
				final String displayName = player.getDisplayName();

				if (!displayName.equals(player.getName()))
					format = format.replace("%player_displayname%", globalModule.getDisplaynameIcon() + displayName);
				else
					format = format.replace("%player_displayname%", displayName);

				format = placeholdersModule.replacePlaceholders(player, format
						.replace("%message%", reformatterModule.formatMessage(message)))
						.replace("%", "%%");

				if (player.hasPermission("gamechat.colors"))
					format = ChatColor.translateAlternateColorCodes('&', format);

				event.setFormat(format);

				final Set<Player> recipents = event.getRecipients();

				for (final ChatPlayer chatPlayer : chatPlayerModule.getChatPlayers())
					if (!chatPlayer.isGlobalChatEnabled() || chatPlayer.getIgnoredPlayers().contains(player.getName())) {
						final CommandSender commandSender = chatPlayer.getSender();

						if (commandSender instanceof Player)
							recipents.remove(commandSender);
					}
			}
		}
	}
}
