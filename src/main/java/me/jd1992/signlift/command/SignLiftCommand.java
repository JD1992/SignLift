package me.jd1992.signlift.command;

import me.jd1992.signlift.util.Constants;
import javafx.util.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
/**
 * Created by Jan on 29.01.2017
 */
public class SignLiftCommand implements CommandExecutor {
	
	private final me.jd1992.signlift.SignLift plugin;
	
	public SignLiftCommand (me.jd1992.signlift.SignLift plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand ( CommandSender sender, Command command, String label, String[] args ) {
		if ( ! ( sender instanceof Player ) ) { return true; }
		Player player = ( Player ) sender;
		if ( ! ( player.hasPermission( Constants.Permission.ADMIN ) ) ) { return true; }
		
		if ( args.length == 0 ) {
			this.plugin.getMessageHandler().sendPluginMessage(player, Constants.Commands.SignLift.Usage.GENERAL);
			this.plugin.getMessageHandler().sendPluginMessage(player, Constants.Commands.SignLift.Usage.UP);
			this.plugin.getMessageHandler().sendPluginMessage(player, Constants.Commands.SignLift.Usage.DOWN);
			this.plugin.getMessageHandler().sendPluginMessage(player, Constants.Commands.SignLift.Usage.CANCEL);
			this.plugin.getMessageHandler().sendPluginMessage(player, Constants.Commands.SignLift.Usage.DELETE);
			return true;
		}
		
		switch ( args[ 0 ] ) {
			case "cancel":
				if ( this.plugin.getLiftLocation().containsKey( player ) ) {
					this.plugin.getLiftLocation().remove( player );
					this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.CANCEL );
				}
				break;
			case "delete":
				if ( this.plugin.getLiftLocation().containsKey( player ) ) {
					this.plugin.getLiftLocation().replace( player, new Pair <>( null, true ) );
				} else {
					this.plugin.getLiftLocation().put( player, new Pair <>( null, true ) );
				}
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DELETE );
				break;
			case "up":
				this.plugin.getLiftLocation().put( player, new Pair <>( player.getLocation(), true ) );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.UP_SAVE );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DECLARATION );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.OVERRIDE );
				break;
			case "down":
				this.plugin.getLiftLocation().put( player, new Pair <>( player.getLocation(), false ) );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DOWN_SAVE );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.DECLARATION );
				this.plugin.getMessageHandler().sendConfigMessage( player, Constants.Message.OVERRIDE );
				break;
			default:
				return false;
		}
		return true;
	}
}
