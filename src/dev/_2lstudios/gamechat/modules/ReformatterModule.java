package dev._2lstudios.gamechat.modules;

import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;

import dev._2lstudios.gamechat.interfaces.BukkitModule;

public class ReformatterModule implements BukkitModule {
	private final Pattern pattern = Pattern.compile("[\\w]+");
	private boolean enabled, caps, flood, dot;

	public ReformatterModule(final FileConfiguration configYml) {
		reload(configYml);
	}

	public void reload(final FileConfiguration configYml) {
		this.enabled = configYml.getBoolean("reformatter.enabled");
		this.caps = configYml.getBoolean("reformatter.caps");
		this.flood = configYml.getBoolean("reformatter.flood");
		this.dot = configYml.getBoolean("reformatter.dot");
	}

	public String formatMessage(final String string) {
		if (enabled) {
			String formattedMessage = "";
			int amount = 0;
			char lastCharacter = ' ';
			boolean firstChar = true;

			for (char character : string.toCharArray()) {
				if (caps)
					if (firstChar) {
						character = String.valueOf(character).toUpperCase().toCharArray()[0];
						firstChar = false;
					} else if (lastCharacter != ' ')
						character = String.valueOf(character).toLowerCase().toCharArray()[0];

				if (flood)
					if (lastCharacter == character)
						amount++;
					else if (amount != 0)
						amount = 0;

				if (amount < 3)
					formattedMessage = formattedMessage.concat(String.valueOf(character));

				lastCharacter = character;
			}

			if (dot && pattern.matcher(String.valueOf(lastCharacter)).find())
				formattedMessage = formattedMessage.concat(".");

			return formattedMessage.trim();
		} else
			return string;
	}
}