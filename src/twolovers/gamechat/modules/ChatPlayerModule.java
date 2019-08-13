package twolovers.gamechat.modules;

import org.bukkit.command.CommandSender;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.objects.ChatPlayer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChatPlayerModule {
	private final Set<ChatPlayer> chatPlayers = new HashSet<>();

	public void createChatPlayer(final ModuleManager moduleManager, final CommandSender player) {
		chatPlayers.add(new ChatPlayer(moduleManager, player));
	}

	public void removeChatPlayer(final String name) {
		for (final ChatPlayer chatPlayer : chatPlayers)
			if (chatPlayer.getName().equals(name)) {
				chatPlayers.remove(chatPlayer);
				break;
			}
	}

	public ChatPlayer getChatPlayer(final String name) {
		if (name != null)
			for (final ChatPlayer chatPlayer : chatPlayers)
				if (chatPlayer.getName().contains(name))
					return chatPlayer;

		return null;
	}

	public Collection<ChatPlayer> getChatPlayers() {
		return chatPlayers;
	}
}