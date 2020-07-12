package dev._2lstudios.gamechat.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.objects.ChatPlayer;

public class ChatPlayerModule {
	private final Map<String, ChatPlayer> chatPlayers = new HashMap<>();

	public void createChatPlayer(final ModuleManager moduleManager, final CommandSender commandSender) {
		final String name = commandSender.getName();

		chatPlayers.put(name, new ChatPlayer(moduleManager, commandSender));
	}

	public void removeChatPlayer(final String name) {
		chatPlayers.remove(name);
	}

	public ChatPlayer getChatPlayer(final String name) {
		return chatPlayers.getOrDefault(name, null);
	}

	public Collection<ChatPlayer> getChatPlayers() {
		return chatPlayers.values();
	}
}