package me.jd1992.signlift.listener;

import me.jd1992.signlift.SignLift;
import me.jd1992.signlift.util.Constants;
import me.jd1992.signlift.util.SignHelper;
import javafx.util.Pair;
import org.bukkit.Bukkit;
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
	
	private final SignLift plugin;
	
	public ClickListener ( SignLift plugin) {
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
			     && this.plugin.getSignConfig().contains( key + Constants.Value.UP ) ) {
				Location loc = ( Location ) this.plugin.getSignConfig().get( key + Constants.Value.UP );
				Bukkit.getScheduler().runTaskLater( this.plugin,
						() -> player.teleport( loc ), Constants.Value.TP_WAIT );
			} else if ( event.getAction() == Action.RIGHT_CLICK_BLOCK
			            && this.plugin.getSignConfig().contains( key + Constants.Value.DOWN ) ) {
				Location loc = ( Location ) this.plugin.getSignConfig().get( key + Constants.Value.DOWN );
				Bukkit.getScheduler().runTaskLater( plugin,
						() -> player.teleport( loc ), Constants.Value.TP_WAIT );
			}
			event.setCancelled( true );
		}
	}
	
	private void configureSignState ( Player player, String key, Sign s ) {
		Pair < Location, Boolean > state = this.plugin.getLiftLocation().get( player );
		if ( state.getKey() != null ) {
			s.setLine( 0, this.plugin.getConfigHandler().getConfigStringFormatted( Constants.Plugin.Config.SIGN_LINE_0 ) );
			s.setLine( 1, this.plugin.getConfigHandler().getConfigStringFormatted( Constants.Plugin.Config.SIGN_LINE_1 ) );
			if ( state.getValue() ) {
				this.plugin.getSignConfig().set( key + Constants.Value.UP, state.getKey() );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.UP_SET );
				
				s.setLine( 2, this.plugin.getConfigHandler().getConfigStringFormatted( Constants.Plugin.Config.SIGN_LINE_2 ) );
			} else {
				this.plugin.getSignConfig().set( key + Constants.Value.DOWN, state.getKey() );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DOWN_SET );
				
				s.setLine( 3, this.plugin.getConfigHandler().getConfigStringFormatted( Constants.Plugin.Config.SIGN_LINE_3 ) );
			}
			this.plugin.getLiftLocation().remove( player );
		} else {
			if ( this.plugin.getSignConfig().contains( key ) ) {
				this.plugin.getSignConfig().set( key, null );
				String clearedText = "";
				s.setLine( 0, clearedText );
				s.setLine( 1, clearedText );
				s.setLine( 2, clearedText );
				s.setLine( 3, clearedText );
			}
			this.plugin.getLiftLocation().remove( player );
			this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DELETED );
			
		}
		try {
			this.plugin.getSignConfig().save( this.plugin.getSignFile() );
		} catch ( IOException e ) {
			this.plugin.getMessageHandler().log( e );
		}
		
	}
}
