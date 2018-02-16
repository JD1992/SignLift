package de.jand.deinlift;

import de.jand.deinlift.command.CommandDeinLift;
import de.jand.deinlift.listener.ClickListener;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class DeinLift extends JavaPlugin implements Listener {
	
	private String consolePrefix;
	private String ingamePrefix;
	
	private @Getter FileConfiguration configuration;
	
	private @Getter File signFile;
	private @Getter YamlConfiguration signConfig;
	
	private @Getter HashMap < Player, Pair < Location, Boolean > > liftLocation;
	
	
	@Override
	public void onEnable () {
		init();
		System.out.println( this.consolePrefix + "Plugin erfolgreich aktiviert!" );
		
	}
	
	private void init () {
		initConfig();
		initSignConfig();
		initListener();
		initCommands();
		this.liftLocation = new HashMap <>();
	}
	
	private void initConfig () {
		this.configuration = getConfig();
		
		setConfigValues();
		setConfigMessages();
		setConfigSignValues();
		
		this.configuration.options().copyDefaults( true );
		saveConfig();
		
		this.consolePrefix = ChatColor.translateAlternateColorCodes( '&', this.configuration.getString( "plugin.consolePrefix" ) + " " );
		this.ingamePrefix = ChatColor.translateAlternateColorCodes( '&', this.configuration.getString( "plugin.userPrefix" ) + " " );
	}
	
	private void setConfigValues () {
		this.configuration.addDefault( "plugin.userPrefix", "&6&o&ldeinLift&8>" );
		this.configuration.addDefault( "plugin.consolePrefix", "[deinLift]" );
		this.configuration.addDefault( "plugin.debug", false );
	}
	
	private void setConfigMessages () {
		this.configuration.addDefault( "plugin.message.cancel", "&cDer Vorgang zum erstellen eines Lifts wurde abgebrochen." );
		this.configuration.addDefault( "plugin.message.delete", "&cSchlage auf das Schild des zu löschenden Lifts." );
		this.configuration.addDefault( "plugin.message.deleted", "&cDieser Lift wurde erfolgreich gelöscht. Entferne nun das Schild." );
		
		this.configuration.addDefault( "plugin.message.up.save", "&eDu hast die Position für eine Lift-Hoch Postition gespeichert." );
		this.configuration.addDefault( "plugin.message.up.set", "&eDu hast die Position für eine Lift-Hoch Postition auf ein Schild gesetzt." );
		
		this.configuration.addDefault( "plugin.message.down.save", "&eDu hast die Position für eine Lift-Runter Postition gespeichert." );
		this.configuration.addDefault( "plugin.message.down.set", "&eDu hast die Position für eine Lift-Runter Postition auf ein Schild gesetzt." );
		
		this.configuration.addDefault( "plugin.message.declaration", "&eRechtsklicke auf ein Schild um die Position zu setzen." );
		this.configuration.addDefault( "plugin.message.override", "&cAchtung: Vorhandene Positionen könnten überschrieben werden." );
		
	}
	
	private void setConfigSignValues () {
		
		this.configuration.addDefault( "config.sign.line.0", "&c[&9dein&6Lift&c]" );
		this.configuration.addDefault( "config.sign.line.1", "" );
		this.configuration.addDefault( "config.sign.line.2", "&l⬆ Linksklick ⬆" );
		this.configuration.addDefault( "config.sign.line.3", "&l⬇ Rechtsklick ⬇" );
		
	}
	
	private void initSignConfig () {
		if ( ! getDataFolder().exists() ) {
			if ( getDataFolder().mkdir() ) {
				System.out.println( this.consolePrefix + "Der Plugin Ordner wurde neu erstellt!" );
			}
		}
		this.signFile = new File( getDataFolder(), "signs.yml" );
		if ( ! this.signFile.exists() ) {
			try {
				if ( this.signFile.createNewFile() ) {
					System.out.println( this.consolePrefix + "Das File für die Schilderpositionen wurde neu erstellt!" );
				}
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		this.signConfig = YamlConfiguration.loadConfiguration( this.signFile );
	}
	
	private void initListener () {
		this.getServer().getPluginManager().registerEvents( new ClickListener( this ), this );
	}
	
	private void initCommands () {
		getCommand( "deinLift" ).setExecutor( new CommandDeinLift( this ) );
	}
	
	@Override
	public void onDisable () {
		System.out.println( this.consolePrefix + "Plugin erfolgreich deaktiviert!" );
		
	}
	
	public void sendConfigMessage ( CommandSender sender, String node ) {
		String message = ChatColor.translateAlternateColorCodes( '&', this.configuration.getString( node ) );
		if ( sender instanceof Player ) {
			sender.sendMessage( this.ingamePrefix + message );
			return;
		}
		System.out.println( this.consolePrefix + ChatColor.stripColor( message ) );
	}
	
	public void sendPluginMessage ( CommandSender sender, String message ) {
		if ( sender instanceof Player ) {
			sender.sendMessage( this.ingamePrefix + ChatColor.translateAlternateColorCodes( '&', message ) );
			return;
		}
		System.out.println( this.consolePrefix + ChatColor.stripColor( message ) );
	}
	
}
