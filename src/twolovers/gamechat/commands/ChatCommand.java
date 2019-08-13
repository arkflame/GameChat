package twolovers.gamechat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import twolovers.gamechat.GameChat;
import twolovers.gamechat.instanceables.Messages;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.modules.ChatPlayerModule;
import twolovers.gamechat.modules.MessagesModule;
import twolovers.gamechat.objects.ChatPlayer;

public class ChatCommand implements CommandExecutor {
	private final GameChat gameChat;
	private final ChatPlayerModule chatPlayerModule;
	private final MessagesModule messagesModule;

	public ChatCommand(final GameChat gameChat, final ModuleManager moduleManager) {
		this.gameChat = gameChat;
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
		this.messagesModule = moduleManager.getMessagesModule();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			final int length = args.length;
			final Player player = (Player) sender;
			final Messages messages = messagesModule.getMessages(player.spigot().getLocale());

			if (length > 0) {
				final String args0 = args[0];

				if (args0.equals("reload")) {
					if (sender.hasPermission("gamechat.reload")) {
						gameChat.reload();
						sender.sendMessage(messages.getReload());
					} else
						sender.sendMessage(messages.getErrorPermission());
				} else if (args0.equals("ignore")) {
					if (length > 1) {
						final String args1 = args[1];

						final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());

						if (chatPlayer.getIgnoredPlayers().contains(args1)) {
							chatPlayer.removeIgnoredPlayer(args1);
							sender.sendMessage(messages.getIgnoreFalse().replace("%player_name%", args1));
						} else {
							chatPlayer.addIgnoredPlayer(args1);
							sender.sendMessage(messages.getIgnoreTrue().replace("%player_name%", args1));
						}
					} else
						sender.sendMessage(messages.getUsageIgnore().replace("%command%", label));
				} else if (args0.equals("global")) {
					final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());

					chatPlayer.setGlobalChat(!chatPlayer.isGlobalChatEnabled());

					if (chatPlayer.isGlobalChatEnabled()) sender.sendMessage(messages.getGlobalTrue());
					else sender.sendMessage(messages.getGlobalFalse());
				} else if (args0.equals("private")) {
					final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());

					chatPlayer.setPrivateChatEnabled(!chatPlayer.isPrivateChatEnabled());

					if (chatPlayer.isPrivateChatEnabled()) sender.sendMessage(messages.getPrivateTrue());
					else sender.sendMessage(messages.getPrivateFalse());
				} else if (args0.equals("sounds")) {
					final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());

					chatPlayer.setSound(!chatPlayer.isSound());

					if (chatPlayer.isSound()) sender.sendMessage(messages.getSoundsTrue());
					else sender.sendMessage(messages.getSoundsFalse());
				} else
					sender.sendMessage(messages.getHelp().replace("%command%", label));
			} else
				sender.sendMessage(messages.getHelp().replace("%command%", label));
		} else
			sender.sendMessage(messagesModule.getDefaultMessages().getErrorConsole());

		return true;
	}
}
