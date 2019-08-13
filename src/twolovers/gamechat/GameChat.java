package twolovers.gamechat;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import twolovers.gamechat.commands.ChatCommand;
import twolovers.gamechat.commands.MessageCommand;
import twolovers.gamechat.commands.ReplyCommand;
import twolovers.gamechat.commands.TogglePMCommand;
import twolovers.gamechat.listeners.AsyncPlayerChatListener;
import twolovers.gamechat.listeners.PlayerJoinListener;
import twolovers.gamechat.listeners.PlayerQuitListener;
import twolovers.gamechat.managers.ModuleManager;
import twolovers.gamechat.utils.ConfigurationUtil;
import twolovers.gamechat.utils.VersionUtil;

public class GameChat extends JavaPlugin {
	private ModuleManager moduleManager;

	public void onEnable() {
		VersionUtil.init();

		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createYamlConfiguration("%datafolder%/config.yml");
		configurationUtil.createYamlConfiguration("%datafolder%/messages.yml");

		final FileConfiguration configYml = configurationUtil.getYamlConfiguration("%datafolder%/config.yml");
		final FileConfiguration messagesYml = configurationUtil.getYamlConfiguration("%datafolder%/messages.yml");
		final Server server = Bukkit.getServer();
		final PluginManager pluginManager = server.getPluginManager();

		moduleManager = new ModuleManager(configYml, messagesYml);

		pluginManager.registerEvents(new AsyncPlayerChatListener(moduleManager), this);
		pluginManager.registerEvents(new PlayerJoinListener(moduleManager), this);
		pluginManager.registerEvents(new PlayerQuitListener(moduleManager), this);

		server.getPluginCommand("chat").setExecutor(new ChatCommand(this, moduleManager));
		server.getPluginCommand("message").setExecutor(new MessageCommand(moduleManager));
		server.getPluginCommand("reply").setExecutor(new ReplyCommand(moduleManager));
		server.getPluginCommand("togglepm").setExecutor(new TogglePMCommand(moduleManager));

		for (final Player player : Bukkit.getOnlinePlayers())
			moduleManager.getChatPlayerModule().createChatPlayer(moduleManager, player);

		moduleManager.getChatPlayerModule().createChatPlayer(moduleManager, Bukkit.getConsoleSender());
	}

	public void reload() {
		final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

		configurationUtil.createYamlConfiguration("%datafolder%/config.yml");
		configurationUtil.createYamlConfiguration("%datafolder%/messages.yml");

		final FileConfiguration configYml = configurationUtil.getYamlConfiguration("%datafolder%/config.yml");
		final FileConfiguration messagesYml = configurationUtil.getYamlConfiguration("%datafolder%/messages.yml");

		moduleManager.reload(configYml, messagesYml);
	}
}
