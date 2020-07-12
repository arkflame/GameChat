package dev._2lstudios.gamechat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.modules.ChatPlayerModule;

public class PlayerJoinListener implements Listener {
	private final ModuleManager moduleManager;
	private final ChatPlayerModule chatPlayerModule;

	public PlayerJoinListener(final ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		chatPlayerModule.createChatPlayer(moduleManager, event.getPlayer());
	}
}
