package de.jand.deinlift.command;

import de.jand.deinlift.DeinLift;
import javafx.util.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Created by Jan on 29.01.2017
 */
public class CommandDeinLift implements CommandExecutor {
	
	private final DeinLift plugin;
	
	public CommandDeinLift ( DeinLift plugin ) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		if ( ! ( sender instanceof Player ) ) { return true; }
		Player player = ( Player ) sender;
		if ( ! ( player.hasPermission( "deinlift.admin" ) ) ) { return true; }
		
		if ( args.length == 0 ) {
			this.plugin.sendPluginMessage( player, "&cBenutzung: &adeinlift <up/down/cancel/delete>" );
			this.plugin.sendPluginMessage( player, "&aup &c- &eAn der Position die für eine Lift-Hoch Position gesetzt werden soll" );
			this.plugin.sendPluginMessage( player, "&adown &c- &eAn der Position die für eine Lift-Runter Position gesetzt werden soll" );
			this.plugin.sendPluginMessage( player, "&acancel &c- &eup/down Position verwerfen" );
			this.plugin.sendPluginMessage( player, "&adelete &c- &eSchild löschen" );
			return true;
		}
		
		switch ( args[ 0 ] ) {
			case "cancel":
				if ( this.plugin.getLiftLocation().containsKey( player ) ) {
					this.plugin.getLiftLocation().remove( player );
					this.plugin.sendConfigMessage( player, "plugin.message.cancel" );
				}
				break;
			case "delete":
				if ( this.plugin.getLiftLocation().containsKey( player ) ) {
					this.plugin.getLiftLocation().replace( player, new Pair <>( null, true ) );
				} else {
					this.plugin.getLiftLocation().put( player, new Pair <>( null, true ) );
				}
				this.plugin.sendConfigMessage( player, "plugin.message.delete" );
				break;
			case "up":
				this.plugin.getLiftLocation().put( player, new Pair <>( player.getLocation(), true ) );
				this.plugin.sendConfigMessage( player, "plugin.message.up.save" );
				this.plugin.sendConfigMessage( player, "plugin.message.declaration" );
				this.plugin.sendConfigMessage( player, "plugin.message.override" );
				break;
			case "down":
				this.plugin.getLiftLocation().put( player, new Pair <>( player.getLocation(), false ) );
				this.plugin.sendConfigMessage( player, "plugin.message.down.save" );
				this.plugin.sendConfigMessage( player, "plugin.message.declaration" );
				this.plugin.sendConfigMessage( player, "plugin.message.override" );
				break;
			default:
				return false;
		}
		return true;
	}
}
