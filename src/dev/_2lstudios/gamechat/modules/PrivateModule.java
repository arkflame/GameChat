package dev._2lstudios.gamechat.modules;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import dev._2lstudios.gamechat.interfaces.BukkitModule;

public class PrivateModule implements BukkitModule {
	private Sound sound;
	private String sender, receiver, message;
	private boolean enabled;

	public PrivateModule(final FileConfiguration configYml) {
		reload(configYml);
	}

	public void reload(final FileConfiguration configYml) {
		this.enabled = configYml.getBoolean("private.enabled");
		this.sender = configYml.getString("private.format.sender");
		this.receiver = configYml.getString("private.format.receiver");
		this.message = configYml.getString("private.format.message");

		try {
			this.sound = Sound.valueOf(configYml.getString("private.sound"));
		} catch (final Exception ex) {
			this.sound = null;
		}
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

	public Sound getSound() {
		return sound;
	}
}
