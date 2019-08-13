package twolovers.gamechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import twolovers.gamechat.instanceables.Messages;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;
import twolovers.gamechat.modules.MessagesModule;
import twolovers.gamechat.modules.PrivateModule;
import twolovers.gamechat.objects.ChatPlayer;

public class ReplyCommand implements CommandExecutor {
	private final PrivateModule privateModule;
	private final ChatPlayerModule chatPlayerModule;
	private final MessagesModule messagesModule;

	public ReplyCommand(final ModuleManager moduleManager) {
		this.privateModule = moduleManager.getPrivateModule();
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
		this.messagesModule = moduleManager.getMessagesModule();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Messages messages;

		if (sender instanceof Player)
			messages = messagesModule.getMessages(((Player) sender).spigot().getLocale());
		else
			messages = messagesModule.getMessages("en");

		if (privateModule.isEnabled()) {
			final String senderName = sender.getName();

			if (args.length > 0 && senderName != null) {
				final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(senderName);
				final ChatPlayer chatPlayer1 = chatPlayerModule.getChatPlayer(chatPlayer.getLastRecipentName());

				chatPlayer.sendMessage(chatPlayer1, args);
			} else sender.sendMessage(messages.getUsageReply().replace("%command%", label));
		} else
			sender.sendMessage(messages.getErrorUnknown());

		return true;
	}
}
