package dev._2lstudios.gamechat.modules;

import java.util.HashSet;

import org.bukkit.configuration.file.FileConfiguration;

import dev._2lstudios.gamechat.instanceables.Messages;

public class MessagesModule {
	private Messages defaultMessages = null;
	private final HashSet<Messages> messagesHashSet = new HashSet<>();

	public MessagesModule(final FileConfiguration messagesYml) {
		reload(messagesYml);
	}

	public Messages getMessages(final String lang) {
		for (final Messages messages : messagesHashSet)
			if (lang.startsWith(messages.getLang()))
				return messages;

		return defaultMessages;
	}

	public void reload(final FileConfiguration messagesYml) {
		messagesHashSet.clear();

		for (final String lang : messagesYml.getKeys(false)) {
			final Messages messages = new Messages(lang, messagesYml);

			if (defaultMessages == null)
				defaultMessages = messages;

			messagesHashSet.add(messages);
		}

		if (defaultMessages == null)
			defaultMessages = new Messages("en", messagesYml);
	}

	public Messages getDefaultMessages() {
		return defaultMessages;
	}
}
