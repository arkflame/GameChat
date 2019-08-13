package twolovers.gamechat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;

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
