package dev._2lstudios.gamechat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.gamechat.instanceables.Messages;
import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.modules.ChatPlayerModule;
import dev._2lstudios.gamechat.modules.MessagesModule;
import dev._2lstudios.gamechat.modules.PrivateModule;
import dev._2lstudios.gamechat.objects.ChatPlayer;
import dev._2lstudios.gamechat.utils.VersionUtil;

public class MessageCommand implements CommandExecutor {
	private final PrivateModule privateModule;
	private final ChatPlayerModule chatPlayerModule;
	private final MessagesModule messagesModule;

	public MessageCommand(final ModuleManager moduleManager) {
		this.privateModule = moduleManager.getPrivateModule();
		this.chatPlayerModule = moduleManager.getChatPlayerModule();
		this.messagesModule = moduleManager.getMessagesModule();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Messages messages;

		if (sender instanceof Player)
			messages = messagesModule.getMessages(VersionUtil.getLocale((Player) sender));
		else
			messages = messagesModule.getMessages("en");

		if (privateModule.isEnabled()) {
			if (args.length > 1) {
				final ChatPlayer chatPlayer = chatPlayerModule.getChatPlayer(sender.getName());
				final ChatPlayer chatPlayer1 = chatPlayerModule.getChatPlayer(args[0]);

				Bukkit.getPlayer(args[0]);

				args[0] = "";

				chatPlayer.sendMessage(chatPlayer1, args);
			} else
				sender.sendMessage(messages.getUsageMessage().replace("%command%", label));
		} else
			sender.sendMessage(messages.getErrorUnknown());

		return true;
	}
}
