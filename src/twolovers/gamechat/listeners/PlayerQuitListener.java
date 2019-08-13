package twolovers.gamechat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;

public class PlayerQuitListener implements Listener {
	private ChatPlayerModule chatPlayerModule;

	public PlayerQuitListener(final ModuleManager moduleManager) {
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
	}

	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		chatPlayerModule.removeChatPlayer(event.getPlayer().getName());
	}
}