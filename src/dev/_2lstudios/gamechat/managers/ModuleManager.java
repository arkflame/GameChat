package dev._2lstudios.gamechat.managers;

import org.bukkit.configuration.file.FileConfiguration;

import dev._2lstudios.gamechat.modules.ChatPlayerModule;
import dev._2lstudios.gamechat.modules.GlobalModule;
import dev._2lstudios.gamechat.modules.MessagesModule;
import dev._2lstudios.gamechat.modules.PlaceholdersModule;
import dev._2lstudios.gamechat.modules.PrivateModule;
import dev._2lstudios.gamechat.modules.ReformatterModule;

public class ModuleManager {
	private ChatPlayerModule chatPlayerModule;
	private GlobalModule globalModule;
	private PrivateModule privateModule;
	private ReformatterModule reformatterModule;
	private PlaceholdersModule placeholdersModule;
	private MessagesModule messagesModule;

	public ModuleManager(final FileConfiguration configYml, final FileConfiguration messagesYml) {
		chatPlayerModule = new ChatPlayerModule();
		globalModule = new GlobalModule(configYml);
		privateModule = new PrivateModule(configYml);
		reformatterModule = new ReformatterModule(configYml);
		placeholdersModule = new PlaceholdersModule();
		messagesModule = new MessagesModule(messagesYml);
	}

	public void reload(final FileConfiguration configYml, final FileConfiguration messagesYml) {
		globalModule.reload(configYml);
		privateModule.reload(configYml);
		reformatterModule.reload(configYml);
		messagesModule.reload(messagesYml);
	}

	public ChatPlayerModule getChatPlayerModule() {
		return chatPlayerModule;
	}

	public GlobalModule getGlobalModule() {
		return globalModule;
	}

	public PrivateModule getPrivateModule() {
		return privateModule;
	}

	public ReformatterModule getReformatterModule() {
		return reformatterModule;
	}

	public PlaceholdersModule getPlaceholdersModule() {
		return placeholdersModule;
	}

	public MessagesModule getMessagesModule() {
		return messagesModule;
	}
}
