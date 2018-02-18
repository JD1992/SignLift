package de.jand.deinlift;

import de.jand.deinlift.command.CommandDeinLift;
import de.jand.deinlift.listener.ClickListener;
import de.jand.deinlift.util.ConfigHandler;
import de.jand.deinlift.util.MessageHandler;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class DeinLift extends JavaPlugin implements Listener {
	
	private @Getter File signFile;
	private @Getter YamlConfiguration signConfig;
	
	private @Getter HashMap < Player, Pair < Location, Boolean > > liftLocation;
	private @Getter MessageHandler messageHandler;
	private @Getter ConfigHandler configHandler;
	
	
	@Override
	public void onEnable () {
		init();
	}
	
	@Override
	public void onDisable () {
	
	}
	
	private void init () {
		
		configHandler = new ConfigHandler( this );
		messageHandler = new MessageHandler( this );
		
		initSignConfig();
		initListener();
		initCommands();
		this.liftLocation = new HashMap <>();
	}
	
	private void initSignConfig () {
		if ( ! getDataFolder().exists() ) {
			if ( getDataFolder().mkdir() ) {
				this.getMessageHandler().sendPluginMessage( "Der Plugin Ordner wurde neu erstellt!" );
			}
		}
		this.signFile = new File( getDataFolder(), "signs.yml" );
		if ( ! this.signFile.exists() ) {
			try {
				if ( this.signFile.createNewFile() ) {
					this.getMessageHandler().sendPluginMessage( "Das File für die Schilderpositionen wurde neu erstellt!" );
					
				}
			} catch ( IOException e ) {
				this.getMessageHandler().log( e );
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
	
}
