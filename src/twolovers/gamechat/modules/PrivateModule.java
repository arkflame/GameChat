package twolovers.gamechat.modules;

import org.bukkit.configuration.file.FileConfiguration;
import twolovers.gamechat.interfaces.BukkitModule;

public class PrivateModule implements BukkitModule {
	private boolean enabled, sound;
	private String sender, receiver, message;

	public PrivateModule(final FileConfiguration configYml) {
		reload(configYml);
	}

	public void reload(final FileConfiguration configYml) {
		this.enabled = configYml.getBoolean("private.enabled");
		this.sender = configYml.getString("private.format.sender");
		this.receiver = configYml.getString("private.format.receiver");
		this.message = configYml.getString("private.format.message");
		this.sound = configYml.getBoolean("private.sound");
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}

	public boolean getSound() {
		return sound;
	}
}
