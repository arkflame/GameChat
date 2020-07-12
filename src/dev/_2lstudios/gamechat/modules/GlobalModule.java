package dev._2lstudios.gamechat.modules;

import org.bukkit.configuration.file.FileConfiguration;

import dev._2lstudios.gamechat.interfaces.BukkitModule;

public class GlobalModule implements BukkitModule {
	private boolean enabled, world;
	private String displaynameIcon, message;

	public GlobalModule(final FileConfiguration configYml) {
		reload(configYml);
	}

	public void reload(final FileConfiguration configYml) {
		this.enabled = configYml.getBoolean("global.enabled");
		this.world = configYml.getBoolean("global.world");
		this.displaynameIcon = configYml.getString("global.displayname_icon");
		this.message = configYml.getString("global.format.message");
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isWorld() {
		return world;
	}

	public String getDisplaynameIcon() {
		return displaynameIcon;
	}

	public String getMessage() {
		return message;
	}
}
