package bz.dcr.deinlift;

import bz.dcr.deinlift.Commands.CommandDeinLift;
import bz.dcr.deinlift.Listener.ClickListener;
import javafx.util.Pair;
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
	
	private String CPREFIX;
	private String UPREFIX;
	
	public FileConfiguration CONFIG;
	
	public File              signFile;
	public YamlConfiguration signConfig;
	
	public HashMap < Player, Pair < Location, Boolean > > liftLocation;
	
	
	@Override
	public void onEnable () {
		init();
		System.out.println( CPREFIX + "Plugin erfolgreich aktiviert!" );
		
	}
	
	private void init () {
		initConfig();
		initSignConfig();
		initListener();
		initCommands();
		liftLocation = new HashMap <>();
	}
	
	private void initConfig () {
		CONFIG = getConfig();
		
		setConfigValues();
		setConfigMessages();
		setConfigSignValues();
		
		CONFIG.options().copyDefaults( true );
		saveConfig();
		
		CPREFIX = ChatColor.translateAlternateColorCodes( '&', CONFIG.getString( "plugin.consolePrefix" ) + " " );
		UPREFIX = ChatColor.translateAlternateColorCodes( '&', CONFIG.getString( "plugin.userPrefix" ) + " " );
	}
	
	private void setConfigValues () {
		CONFIG.addDefault( "plugin.userPrefix", "&6&o&ldeinLift&8>" );
		CONFIG.addDefault( "plugin.consolePrefix", "[deinLift]" );
		CONFIG.addDefault( "plugin.debug", false );
	}
	
	private void setConfigMessages () {
		CONFIG.addDefault( "plugin.message.cancel", "&cDer Vorgang zum erstellen eines Lifts wurde abgebrochen." );
		CONFIG.addDefault( "plugin.message.delete", "&cSchlage auf das Schild des zu löschenden Lifts." );
		CONFIG.addDefault( "plugin.message.deleted", "&cDieser Lift wurde erfolgreich gelöscht. Entferne nun das Schild." );
		
		CONFIG.addDefault( "plugin.message.up.save", "&eDu hast die Position für eine Lift-Hoch Postition gespeichert." );
		CONFIG.addDefault( "plugin.message.up.set", "&eDu hast die Position für eine Lift-Hoch Postition auf ein Schild gesetzt." );
		
		CONFIG.addDefault( "plugin.message.down.save", "&eDu hast die Position für eine Lift-Runter Postition gespeichert." );
		CONFIG.addDefault( "plugin.message.down.set", "&eDu hast die Position für eine Lift-Runter Postition auf ein Schild gesetzt." );
		
		CONFIG.addDefault( "plugin.message.declaration", "&eRechtsklicke auf ein Schild um die Position zu setzen." );
		CONFIG.addDefault( "plugin.message.override", "&cAchtung: Vorhandene Positionen könnten überschrieben werden." );
		
	}
	
	private void setConfigSignValues () {
		
		CONFIG.addDefault( "config.sign.line.0", "&c[&9dein&6Lift&c]" );
		CONFIG.addDefault( "config.sign.line.1", "" );
		CONFIG.addDefault( "config.sign.line.2", "&l⬆ Linksklick ⬆" );
		CONFIG.addDefault( "config.sign.line.3", "&l⬇ Rechtsklick ⬇" );
		
	}
	
	private void initSignConfig () {
		if ( ! getDataFolder().exists() ) {
			if ( getDataFolder().mkdir() ) {
				System.out.println( CPREFIX + "Der Plugin Ordner wurde neu erstellt!" );
			}
		}
		signFile = new File( getDataFolder(), "signs.yml" );
		if ( ! signFile.exists() ) {
			try {
				if ( signFile.createNewFile() ) {
					System.out.println( CPREFIX + "Das File für die Schilderpositionen wurde neu erstellt!" );
				}
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		signConfig = YamlConfiguration.loadConfiguration( signFile );
	}
	
	private void initListener () {
		this.getServer().getPluginManager().registerEvents( new ClickListener( this ), this );
	}
	
	private void initCommands () {
		getCommand( "deinLift" ).setExecutor( new CommandDeinLift( this ) );
	}
	
	@Override
	public void onDisable () {
		System.out.println( CPREFIX + "Plugin erfolgreich deaktiviert!" );
		
	}
	
	public void sendConfigMessage ( CommandSender sender, String node ) {
		String message = ChatColor.translateAlternateColorCodes( '&', this.CONFIG.getString( node ) );
		if ( sender instanceof Player ) {
			sender.sendMessage( UPREFIX + message );
			return;
		}
		System.out.println( CPREFIX + ChatColor.stripColor( message ) );
	}
	
	public void sendPluginMessage ( CommandSender sender, String message ) {
		if ( sender instanceof Player ) {
			sender.sendMessage( UPREFIX + ChatColor.translateAlternateColorCodes( '&', message ) );
			return;
		}
		System.out.println( CPREFIX + ChatColor.stripColor( message ) );
	}
	
}
