package de.jand.deinlift.util;

import org.bukkit.Location;
/**
 * Created by Jan on 29.01.2017
 */
public class SignHelper {
	
	private SignHelper () {
		throw new IllegalStateException( Constants.STATE_WARNING );
	}
	
	public static String convertLocationToKey ( Location location ) {
		String key = "world=" + location.getWorld().getName();
		key += ",x=" + location.getBlockX();
		key += ",y=" + location.getBlockY();
		key += ",z=" + location.getBlockZ();
		return key;
	}
	
}
