package bz.dcr.deinlift.Commands;

import bz.dcr.deinlift.DeinLift;
import javafx.util.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Created by Jan on 29.01.2017
 */
public class CommandDeinLift implements CommandExecutor {
	
	private final DeinLift INSTANCE;
	
	public CommandDeinLift ( DeinLift instance ) {
		this.INSTANCE = instance;
	}
	
	@Override
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		if ( ! ( sender instanceof Player ) ) { return true; }
		Player player = ( Player ) sender;
		if ( ! ( player.hasPermission( "deinlift.admin" ) ) ) { return true; }
		
		if ( args.length == 0 ) {
			INSTANCE.sendPluginMessage( player, "&cBenutzung: &adeinlift <up/down/cancel/delete>" );
			INSTANCE.sendPluginMessage( player, "&aup &c- &eAn der Position die für eine Lift-Hoch Position gesetzt werden soll" );
			INSTANCE.sendPluginMessage( player, "&adown &c- &eAn der Position die für eine Lift-Runter Position gesetzt werden soll" );
			INSTANCE.sendPluginMessage( player, "&acancel &c- &eup/down Position verwerfen" );
			INSTANCE.sendPluginMessage( player, "&adelete &c- &eSchild löschen" );
			return true;
		}
		
		switch ( args[ 0 ] ) {
			case "cancel":
				if ( INSTANCE.liftLocation.containsKey( player ) ) {
					INSTANCE.liftLocation.remove( player );
					INSTANCE.sendConfigMessage( player, "plugin.message.cancel" );
				}
				break;
			case "delete":
				if ( INSTANCE.liftLocation.containsKey( player ) ) {
					INSTANCE.liftLocation.replace( player, new Pair <>( null, true ) );
				} else {
					INSTANCE.liftLocation.put( player, new Pair <>( null, true ) );
				}
				INSTANCE.sendConfigMessage( player, "plugin.message.delete" );
				break;
			case "up":
				INSTANCE.liftLocation.put( player, new Pair <>( player.getLocation(), true ) );
				INSTANCE.sendConfigMessage( player, "plugin.message.up.save" );
				INSTANCE.sendConfigMessage( player, "plugin.message.declaration" );
				INSTANCE.sendConfigMessage( player, "plugin.message.override" );
				break;
			case "down":
				INSTANCE.liftLocation.put( player, new Pair <>( player.getLocation(), false ) );
				INSTANCE.sendConfigMessage( player, "plugin.message.down.save" );
				INSTANCE.sendConfigMessage( player, "plugin.message.declaration" );
				INSTANCE.sendConfigMessage( player, "plugin.message.override" );
				break;
			default:
				return false;
		}
		return true;
	}
}
