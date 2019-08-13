package twolovers.gamechat.modules;

import org.bukkit.configuration.file.FileConfiguration;
import twolovers.gamechat.instanceables.Messages;

import java.util.HashSet;

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
