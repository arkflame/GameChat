package twolovers.gamechat.instanceables;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
	private String lang, reload, help, ignoreTrue, ignoreFalse,
			globalTrue, globalFalse, privateTrue, privateFalse,
			soundsTrue, soundsFalse, errorUnknown, errorPermission,
			errorConsole, usageMessage, usageReply, usageIgnore;

	public Messages(final String lang, final FileConfiguration messagesYml) {
		this.lang = lang;

		reload(messagesYml);
	}

	private void reload(final FileConfiguration messagesYml) {
		try {
			this.reload = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.reload"));
			this.help = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.help"));
			this.ignoreTrue = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.ignore.true"));
			this.ignoreFalse = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.ignore.false"));
			this.globalTrue = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.global.true"));
			this.globalFalse = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.global.false"));
			this.privateTrue = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.private.true"));
			this.privateFalse = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.private.false"));
			this.soundsTrue = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.sounds.true"));
			this.soundsFalse = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.sounds.false"));
			this.errorUnknown = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.error.unknown"));
			this.errorPermission = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.error.permission"));
			this.errorConsole = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.error.console"));
			this.usageMessage = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.usage.message"));
			this.usageReply = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.usage.reply"));
			this.usageIgnore = ChatColor.translateAlternateColorCodes('&', messagesYml.getString(lang + ".commands.usage.ignore"));
		} catch (final NullPointerException ignored) {}
	}

	public String getLang() {
		return lang;
	}

	public String getReload() {
		return reload;
	}

	public String getHelp() {
		return help;
	}

	public String getIgnoreTrue() {
		return ignoreTrue;
	}

	public String getIgnoreFalse() {
		return ignoreFalse;
	}

	public String getPrivateFalse() {
		return privateFalse;
	}

	public String getPrivateTrue() {
		return privateTrue;
	}

	public String getErrorUnknown() {
		return errorUnknown;
	}

	public String getErrorPermission() {
		return errorPermission;
	}

	public String getGlobalFalse() {
		return globalFalse;
	}

	public String getGlobalTrue() {
		return globalTrue;
	}

	public String getSoundsTrue() {
		return soundsTrue;
	}

	public String getSoundsFalse() {
		return soundsFalse;
	}

	public String getUsageMessage() {
		return usageMessage;
	}

	public String getErrorConsole() {
		return errorConsole;
	}

	public String getUsageReply() {
		return usageReply;
	}

	public String getUsageIgnore() {
		return usageIgnore;
	}
}
