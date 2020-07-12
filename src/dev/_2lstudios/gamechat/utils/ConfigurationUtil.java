package dev._2lstudios.gamechat.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationUtil {
	final private Plugin plugin;

	public ConfigurationUtil(final Plugin plugin) {
		this.plugin = plugin;
	}

	public YamlConfiguration getYamlConfiguration(String file) {
		final File dataFolder = plugin.getDataFolder();

		file = file.replace("%datafolder%", dataFolder.toPath().toString());

		return YamlConfiguration.loadConfiguration(new File(file));
	}

	public void createYamlConfiguration(String file) {
		try {
			final File dataFolder = plugin.getDataFolder();

			file = file.replace("%datafolder%", dataFolder.toPath().toString());

			final File configFile = new File(file);

			if (!configFile.exists()) {
				final String[] files = file.split("/");
				final InputStream inputStream = plugin.getClass().getClassLoader()
						.getResourceAsStream(files[files.length - 1]);
				final File parentFile = configFile.getParentFile();

				if (parentFile != null)
					parentFile.mkdirs();

				if (inputStream != null) {
					Files.copy(inputStream, configFile.toPath());
					System.out.print(("[%pluginname%] File " + configFile + " has been created!")
							.replace("%pluginname%", plugin.getDescription().getName()));
				} else
					configFile.createNewFile();
			}
		} catch (final IOException e) {
			System.out.print(("[%pluginname%] Unable to create configuration file!").replace("%pluginname%",
					plugin.getDescription().getName()));
		}
	}
}