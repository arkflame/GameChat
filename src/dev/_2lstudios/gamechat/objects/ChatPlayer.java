package dev._2lstudios.gamechat.objects;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.modules.PlaceholdersModule;
import dev._2lstudios.gamechat.modules.PrivateModule;
import dev._2lstudios.gamechat.modules.ReformatterModule;
import dev._2lstudios.gamechat.utils.VersionUtil;

public class ChatPlayer {
	private final PrivateModule privateModule;
	private final PlaceholdersModule placeholdersModule;
	private final ReformatterModule reformatterModule;
	private final Collection<String> ignoredPlayers = new HashSet<>();
	private final CommandSender sender;
	private String lastRecipentName = "";
	private boolean sound, messages = true, chat = true;

	public ChatPlayer(final ModuleManager moduleManager, final CommandSender sender) {
		this.privateModule = moduleManager.getPrivateModule();
		this.placeholdersModule = moduleManager.getPlaceholdersModule();
		this.reformatterModule = moduleManager.getReformatterModule();
		this.sender = sender;
		this.sound = privateModule.getSound();
	}

	public String getLastRecipentName() {
		return lastRecipentName;
	}

	public void removeIgnoredPlayer(final String name) {
		ignoredPlayers.remove(name);
	}

	public void addIgnoredPlayer(final String name) {
		ignoredPlayers.add(name);
	}

	public Collection<String> getIgnoredPlayers() {
		return ignoredPlayers;
	}

	public boolean isPrivateChatEnabled() {
		return messages;
	}

	public void setPrivateChatEnabled(final boolean messages) {
		this.messages = messages;
	}

	public boolean isGlobalChatEnabled() {
		return chat;
	}

	public void setGlobalChat(final boolean chat) {
		this.chat = chat;
	}

	public CommandSender getSender() {
		return sender;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean b) {
		sound = b;
	}

	public void sendMessage(ChatPlayer chatPlayer1, String[] args) {
		if (chatPlayer1 != null) {
			final String senderName = sender.getName();
			final CommandSender receiver = chatPlayer1.getSender();

			if (chatPlayer1.isPrivateChatEnabled() && !chatPlayer1.getIgnoredPlayers().contains(senderName)) {
				String format = privateModule.getMessage();
				String message = "";

				for (int i = 0; i < args.length; i++) {
					if (i > 0)
						message = message.concat(" " + args[i]);
					else
						message = message.concat(args[i]);
				}

				final String messageSender = placeholdersModule.replacePlaceholders(sender, privateModule.getSender());
				final String messageReceiver = placeholdersModule.replacePlaceholders(receiver,
						privateModule.getReceiver());

				format = format.replace("%sender%", messageSender).replace("%receiver%", messageReceiver);

				format = placeholdersModule.replacePlaceholders(sender, format).replace("%message%",
						reformatterModule.formatMessage(message));

				if (sender.hasPermission("gamechat.colors"))
					format = ChatColor.translateAlternateColorCodes('&', format);

				sender.sendMessage(format);
				receiver.sendMessage(format);

				setLastRecipent(chatPlayer1.getSender().getName());
				chatPlayer1.setLastRecipent(senderName);

				if (chatPlayer1.isSound() && receiver instanceof Player) {
					final Player player = (Player) receiver;

					if (VersionUtil.isOneDotNine())
						player.playSound(player.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 1, 2);
					else
						player.playSound(player.getLocation(), Sound.valueOf("NOTE_PLING"), 1, 2);
				}
			} else
				sender.sendMessage(ChatColor.RED + "El jugador te ignora o tiene los mensajes desactivados!");
		} else
			sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
	}

	private void setLastRecipent(final String name) {
		this.lastRecipentName = name;
	}
}
