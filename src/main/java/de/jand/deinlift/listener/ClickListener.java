package de.jand.deinlift.listener;

import de.jand.deinlift.DeinLift;
import de.jand.deinlift.util.SignHelper;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
/**
 * Created by Jan on 29.01.2017
 */
public class ClickListener implements Listener {
	
	private final DeinLift plugin;
	
	public ClickListener ( DeinLift plugin ) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSignClick ( PlayerInteractEvent event ) {
		if ( event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Sign ) {
			Sign s = ( Sign ) event.getClickedBlock().getState();
			
			Location l = s.getLocation();
			String key = SignHelper.convertLocationToKey( l );
			Player player = event.getPlayer();
			if ( this.plugin.getLiftLocation().containsKey( player ) ) {
				configureSignState( player, key, s );
				event.setCancelled( true );
				s.update( true );
				return;
			}
			if ( ! this.plugin.getSignConfig().contains( key ) ) { return; }
			if ( event.getAction() == Action.LEFT_CLICK_BLOCK
			     && this.plugin.getSignConfig().contains( key + ".up" ) ) {
				Location loc = ( Location ) this.plugin.getSignConfig().get( key + ".up" );
				Bukkit.getScheduler().runTaskLater( this.plugin,
						() -> player.teleport( loc ), 3L );
			} else if ( event.getAction() == Action.RIGHT_CLICK_BLOCK
			            && this.plugin.getSignConfig().contains( key + ".down" ) ) {
				Location loc = ( Location ) this.plugin.getSignConfig().get( key + ".down" );
				Bukkit.getScheduler().runTaskLater( plugin,
						() -> player.teleport( loc ), 3L );
			}
			event.setCancelled( true );
		}
	}
	
	private void configureSignState ( Player player, String key, Sign s ) {
		Pair < Location, Boolean > state = this.plugin.getLiftLocation().get( player );
		if ( state.getKey() != null ) {
			s.setLine( 0, ChatColor.translateAlternateColorCodes( '&', this.plugin.getConfiguration().getString( "config.sign.line.0" ) ) );
			s.setLine( 1, ChatColor.translateAlternateColorCodes( '&', this.plugin.getConfiguration().getString( "config.sign.line.1" ) ) );
			if ( state.getValue() ) {
				this.plugin.getSignConfig().set( key + ".up", state.getKey() );
				this.plugin.sendConfigMessage( player, "plugin.message.up.set" );
				s.setLine( 2, ChatColor.translateAlternateColorCodes( '&', this.plugin.getConfiguration().getString( "config.sign.line.2" ) ) );
			} else {
				this.plugin.getSignConfig().set( key + ".down", state.getKey() );
				this.plugin.sendConfigMessage( player, "plugin.message.down.set" );
				s.setLine( 3, ChatColor.translateAlternateColorCodes( '&', this.plugin.getConfiguration().getString( "config.sign.line.3" ) ) );
			}
			this.plugin.getLiftLocation().remove( player );
		} else {
			if ( this.plugin.getSignConfig().contains( key ) ) {
				this.plugin.getSignConfig().set( key, null );
				s.setLine( 0, "" );
				s.setLine( 1, "" );
				s.setLine( 2, "" );
				s.setLine( 3, "" );
			}
			this.plugin.getLiftLocation().remove( player );
			this.plugin.sendConfigMessage( player, "plugin.message.deleted" );
			
		}
		try {
			this.plugin.getSignConfig().save( this.plugin.getSignFile() );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
	}
}
