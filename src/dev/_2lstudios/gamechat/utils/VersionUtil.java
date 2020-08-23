package dev._2lstudios.gamechat.utils;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class VersionUtil {
	private static boolean isOneDotNine, oneDotSeven;

	public static void initialize(final Server server) {
		final String version = server.getVersion();

		isOneDotNine = version.contains("1.9");
		oneDotSeven = version.contains("1.7");
	}

	public static String getLocale(final Player player) {
		String locale;

		try {
			player.getClass().getMethod("getLocale");
			locale = player.getLocale();
		} catch (final NoSuchMethodException exception) {
			try {
				player.spigot().getClass().getMethod("getLocale");
				locale = player.spigot().getLocale();
			} catch (final Exception exception1) {
				locale = "en";
			}
		}

		if (locale != null && locale.length() > 1) {
			return locale.substring(0, 2);
		} else {
			return "en";
		}
	}

	public static boolean isOneDotNine() {
		return isOneDotNine;
	}

	public static boolean isOneDotSeven() {
		return oneDotSeven;
	}
}