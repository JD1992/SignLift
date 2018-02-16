package bz.dcr.deinlift.Listener;

import bz.dcr.deinlift.DeinLift;
import bz.dcr.deinlift.Util.SignHelper;
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
	
	private final DeinLift INSTANCE;
	
	public ClickListener ( DeinLift instance ) {
		this.INSTANCE = instance;
	}
	
	@EventHandler
	public void onSignClick ( PlayerInteractEvent event ) {
		if ( event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Sign ) {
			Sign s = ( Sign ) event.getClickedBlock().getState();
			
			Location l = s.getLocation();
			String key = SignHelper.convertLocationToKey( l );
			Player player = event.getPlayer();
			if ( INSTANCE.liftLocation.containsKey( player ) ) {
				configureSignState( player, key, s );
				event.setCancelled( true );
				s.update( true );
				return;
			}
			if ( ! INSTANCE.signConfig.contains( key ) ) { return; }
			if ( event.getAction() == Action.LEFT_CLICK_BLOCK && INSTANCE.signConfig.contains( key + ".up" ) ) {
				Location loc = ( Location ) INSTANCE.signConfig.get( key + ".up" );
				Bukkit.getScheduler().runTaskLater( INSTANCE, () -> player.teleport( loc ), 3L );
			} else if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && INSTANCE.signConfig.contains( key + ".down" ) ) {
				Location loc = ( Location ) INSTANCE.signConfig.get( key + ".down" );
				Bukkit.getScheduler().runTaskLater( INSTANCE, () -> player.teleport( loc ), 3L );
			}
			event.setCancelled( true );
		}
	}
	
	
	//s.setLine( 0, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.0" ) ) );
	//s.setLine( 1, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.1" ) ) );
	//s.setLine( 2, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.2" ) ) );
	//s.setLine( 3, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.3" ) ) );
	//s.update( true );
	
	private void configureSignState ( Player player, String key, Sign s ) {
		Pair < Location, Boolean > state = INSTANCE.liftLocation.get( player );
		if ( state.getKey() != null ) {
			s.setLine( 0, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.0" ) ) );
			s.setLine( 1, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.1" ) ) );
			if ( state.getValue() ) {
				INSTANCE.signConfig.set( key + ".up", state.getKey() );
				INSTANCE.sendConfigMessage( player, "plugin.message.up.set" );
				s.setLine( 2, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.2" ) ) );
			} else {
				INSTANCE.signConfig.set( key + ".down", state.getKey() );
				INSTANCE.sendConfigMessage( player, "plugin.message.down.set" );
				s.setLine( 3, ChatColor.translateAlternateColorCodes( '&', INSTANCE.CONFIG.getString( "config.sign.line.3" ) ) );
			}
			INSTANCE.liftLocation.remove( player );
		} else {
			if ( INSTANCE.signConfig.contains( key ) ) {
				INSTANCE.signConfig.set( key, null );
				s.setLine( 0, "" );
				s.setLine( 1, "" );
				s.setLine( 2, "" );
				s.setLine( 3, "" );
			}
			INSTANCE.liftLocation.remove( player );
			INSTANCE.sendConfigMessage( player, "plugin.message.deleted" );
			
		}
		try {
			INSTANCE.signConfig.save( INSTANCE.signFile );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
	}
}
