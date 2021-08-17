package dev._2lstudios.gamechat.listeners;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.modules.ChatPlayerModule;
import dev._2lstudios.gamechat.modules.GlobalModule;
import dev._2lstudios.gamechat.modules.PlaceholdersModule;
import dev._2lstudios.gamechat.modules.ReformatterModule;
import dev._2lstudios.gamechat.objects.ChatPlayer;

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

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
		if (globalModule.isEnabled()) {
			String format = globalModule.getMessage();

			if (format == null || format.isEmpty())
				event.setCancelled(true);
			else {
				final Player player = event.getPlayer();
				final String playerName = player.getName();
				final String displayName = player.getDisplayName();
				final Collection<Player> recipents = event.getRecipients();
				String message = reformatterModule.formatMessage(event.getMessage());

				if (player.hasPermission("gamechat.colors")) {
					message = ChatColor.translateAlternateColorCodes('&', message);
				}

				if (!displayName.equals(playerName)) {
					format = placeholdersModule
							.replacePlaceholders(player,
									format.replace("%player_displayname%",
											globalModule.getDisplaynameIcon() + displayName))
							.replace("%message%", message).replace("%", "%%");
				} else {
					format = placeholdersModule
							.replacePlaceholders(player, format.replace("%player_displayname%", displayName))
							.replace("%message%", message).replace("%", "%%");
				}

				event.setFormat(format);

				for (final ChatPlayer chatPlayer : chatPlayerModule.getChatPlayers()) {
					final CommandSender sender = chatPlayer.getSender();

					if (sender instanceof Player) {
						if (chatPlayer.isGlobalChatEnabled() && !chatPlayer.getIgnoredPlayers().contains(playerName)) {
							final String player1Name = sender.getName();

							if (message.contains(player1Name)) {
								chatPlayer.playSound();
							}
						} else {
							recipents.remove(sender);
						}
					}
				}
			}
		}
	}
}
