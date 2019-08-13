package twolovers.gamechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import twolovers.gamechat.instanceables.Messages;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;
import twolovers.gamechat.modules.MessagesModule;
import twolovers.gamechat.objects.ChatPlayer;

public class TogglePMCommand implements CommandExecutor {
	private final ChatPlayerModule chatPlayerModule;
	private final MessagesModule messagesModule;

	public TogglePMCommand(final ModuleManager moduleManager) {
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
		this.messagesModule = moduleManager.getMessagesModule();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());
			final Messages messages;

			messages = messagesModule.getMessages(((Player) sender).spigot().getLocale());
			chatPlayer.setPrivateChatEnabled(!chatPlayer.isPrivateChatEnabled());

			if (chatPlayer.isPrivateChatEnabled())
				sender.sendMessage(messages.getPrivateTrue());
			else
				sender.sendMessage(messages.getPrivateFalse());
		}

		return true;
	}
}
