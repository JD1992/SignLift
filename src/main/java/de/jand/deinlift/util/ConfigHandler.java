package de.jand.deinlift.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handler for the plugin config
 *
 * @author Jan Dietze
 * @version 1.0
 */

public class ConfigHandler {
	
	private final FileConfiguration config;
	
	public ConfigHandler ( JavaPlugin plugin ) {
		
		this.config = plugin.getConfig();
		setDefaultPermissions();
		setDefaultValues();
		setDefaultMessages();
		
		this.config.options().copyDefaults( true );
		plugin.saveConfig();
		
	}
	
	/**
	 * Setting the default values for the permissions in the config
	 */
	private void setDefaultPermissions () {
		
		this.config.addDefault( Constants.Permission.ADMIN, "deinlift.admin" );
		this.config.addDefault( Constants.Permission.TEAM, "deinlift.team" );
		this.config.addDefault( Constants.Permission.CREATE, "deinlift.create" );
		this.config.addDefault( Constants.Permission.USE, "deinlift.use" );
		
	}
	
	/**
	 * Config Standard-Werte für Datenbank Verbindungsdaten
	 */
	private void setDefaultValues () {
		
		this.config.addDefault( Constants.Plugin.Prefix.CONSOLE, "[deinLift]" );
		this.config.addDefault( Constants.Plugin.Prefix.INGAME, "&6&o&ldeinLift&8>" );
		
		
		this.config.addDefault( Constants.Plugin.Config.SIGN_LINE_0, "&c[&9dein&6Lift&c]" );
		this.config.addDefault( Constants.Plugin.Config.SIGN_LINE_1, "" );
		this.config.addDefault( Constants.Plugin.Config.SIGN_LINE_2, "&l⬆ Linksklick ⬆" );
		this.config.addDefault( Constants.Plugin.Config.SIGN_LINE_3, "&l⬇ Rechtsklick ⬇" );
		
	}
	
	/**
	 * Setting the default values for messages in the config
	 */
	private void setDefaultMessages () {
		
		this.config.addDefault( Constants.Message.CANCEL, "&cDer Vorgang zum erstellen eines Lifts wurde abgebrochen." );
		this.config.addDefault( Constants.Message.DELETE, "&cSchlage auf das Schild des zu löschenden Lifts." );
		this.config.addDefault( Constants.Message.DELETED, "&cDieser Lift wurde erfolgreich gelöscht. Entferne nun das Schild." );
		
		this.config.addDefault( Constants.Message.UP_SAVE, "&eDu hast die Position für eine Lift-Hoch Postition gespeichert." );
		this.config.addDefault( Constants.Message.UP_SET, "&eDu hast die Position für eine Lift-Hoch Postition auf ein Schild gesetzt." );
		
		this.config.addDefault( Constants.Message.DOWN_SAVE, "&eDu hast die Position für eine Lift-Runter Postition gespeichert." );
		this.config.addDefault( Constants.Message.DOWN_SET, "&eDu hast die Position für eine Lift-Runter Postition auf ein Schild gesetzt." );
		
		this.config.addDefault( Constants.Message.DECLARATION, "&eRechtsklicke auf ein Schild um die Position zu setzen." );
		this.config.addDefault( Constants.Message.OVERRIDE, "&cAchtung: Vorhandene Positionen könnten überschrieben werden." );
		
	}
	
	/**
	 * Getting a config string with translated colorcodes
	 *
	 * @param node The node from the config to find the string e.g messages.player.noMoney
	 *
	 * @return The config string with translated colorcodes
	 */
	public String getConfigStringFormatted ( String node ) {
		return ChatColor.translateAlternateColorCodes( '&', this.config.getString( node ) );
	}
	
	/**
	 * Getting a config string with translated colorcodes
	 *
	 * @param node The node from the config to find the string e.g messages.player.noMoney
	 *
	 * @return The config string with translated colorcodes
	 */
	public String getConfigString ( String node ) {
		return this.config.getString( node );
	}
	
	/**
	 * Getting a config int
	 *
	 * @param node The node from the config to find the int e.g database.port
	 *
	 * @return The config int
	 */
	public int getConfigInt ( String node ) {
		return this.config.getInt( node );
	}
	
	/**
	 * Getting a permission string out of the config and strip the colorcodes for savety
	 *
	 * @param node The node from the config to find the string e.g permissions.vip
	 *
	 * @return The config string with stipped out colorcodes
	 */
	public String getConfigPermission ( String node ) {
		return ChatColor.stripColor( this.config.getString( node ) );
	}
	
}

