package dev._2lstudios.gamechat;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.gamechat.commands.ChatCommand;
import dev._2lstudios.gamechat.commands.MessageCommand;
import dev._2lstudios.gamechat.commands.ReplyCommand;
import dev._2lstudios.gamechat.listeners.AsyncPlayerChatListener;
import dev._2lstudios.gamechat.listeners.PlayerJoinListener;
import dev._2lstudios.gamechat.listeners.PlayerQuitListener;
import dev._2lstudios.gamechat.managers.ModuleManager;
import dev._2lstudios.gamechat.modules.ChatPlayerModule;
import dev._2lstudios.gamechat.utils.ConfigurationUtil;
import dev._2lstudios.gamechat.utils.VersionUtil;

public class GameChat extends JavaPlugin {
	private ModuleManager moduleManager;

	public void onEnable() {
		final Server server = Bukkit.getServer();

		VersionUtil.initialize(server);

		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createYamlConfiguration("%datafolder%/config.yml");
		configurationUtil.createYamlConfiguration("%datafolder%/messages.yml");

		final FileConfiguration configYml = configurationUtil.getYamlConfiguration("%datafolder%/config.yml");
		final FileConfiguration messagesYml = configurationUtil.getYamlConfiguration("%datafolder%/messages.yml");
		final PluginManager pluginManager = server.getPluginManager();

		moduleManager = new ModuleManager(configYml, messagesYml);

		final ChatPlayerModule chatPlayerModule = moduleManager.getChatPlayerModule();

		pluginManager.registerEvents(new AsyncPlayerChatListener(moduleManager), this);
		pluginManager.registerEvents(new PlayerJoinListener(moduleManager), this);
		pluginManager.registerEvents(new PlayerQuitListener(moduleManager), this);

		server.getPluginCommand("chat").setExecutor(new ChatCommand(this, moduleManager));
		server.getPluginCommand("msg").setExecutor(new MessageCommand(moduleManager));
		server.getPluginCommand("reply").setExecutor(new ReplyCommand(moduleManager));
	}

	public void reload() {
		final Server server = getServer();
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createYamlConfiguration("%datafolder%/config.yml");
		configurationUtil.createYamlConfiguration("%datafolder%/messages.yml");

		final FileConfiguration configYml = configurationUtil.getYamlConfiguration("%datafolder%/config.yml");
		final FileConfiguration messagesYml = configurationUtil.getYamlConfiguration("%datafolder%/messages.yml");

		moduleManager.reload(configYml, messagesYml);

		final ChatPlayerModule chatPlayerModule = moduleManager.getChatPlayerModule();

		for (final Player player : server.getOnlinePlayers()) {
			chatPlayerModule.createChatPlayer(moduleManager, player);
		}

		chatPlayerModule.createChatPlayer(moduleManager, server.getConsoleSender());
	}
}
