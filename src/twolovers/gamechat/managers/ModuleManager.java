package twolovers.gamechat.managers;

import org.bukkit.configuration.file.FileConfiguration;
import twolovers.gamechat.modules.*;

public class ModuleManager {
	private ChatPlayerModule chatPlayerModule;
	private GlobalModule globalModule;
	private PrivateModule privateModule;
	private ReformatterModule reformatterModule;
	private PlaceholdersModule placeholdersModule;
	private MessagesModule messagesModule;

	public ModuleManager(final FileConfiguration configYml, final FileConfiguration messagesYml) {
		this.chatPlayerModule = new ChatPlayerModule();
		this.globalModule = new GlobalModule(configYml);
		this.privateModule = new PrivateModule(configYml);
		this.reformatterModule = new ReformatterModule(configYml);
		this.placeholdersModule = new PlaceholdersModule();
		this.messagesModule = new MessagesModule(messagesYml);
	}

	public void reload(final FileConfiguration configYml, final FileConfiguration messagesYml) {
		this.globalModule.reload(configYml);
		this.privateModule.reload(configYml);
		this.reformatterModule.reload(configYml);
		this.messagesModule.reload(messagesYml);
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
