package me.jd1992.signlift.util;

/**
 * This class contains every constant value, config nodes and permissions
 *
 * @author Jan Dietze
 * @version 1.0
 */

public final class Constants {
	
	public static final String STATE_WARNING = "Utility class!";
	
	public final class Permission {
		
		private Permission () {
			throw new IllegalStateException( STATE_WARNING );
		}
		
		public static final String ADMIN = "permission.admin";
		public static final String TEAM = "permission.team";
		public static final String CREATE = "permission.create";
		public static final String USE = "permission.use";
		
	}
	
	public final class Plugin {
		
		private Plugin () {
			throw new IllegalStateException( STATE_WARNING );
		}
		
		public final class Prefix {
			
			private Prefix () {
				throw new IllegalStateException( STATE_WARNING );
			}
			
			public static final String CONSOLE = "plugin.consolePrefix";
			public static final String INGAME = "plugin.userPrefix";
			
		}
		
		public final class Config {
			
			private Config () {
				throw new IllegalStateException( STATE_WARNING );
			}
			
			public static final String SIGN_LINE_0 = "config.sign.line.0";
			public static final String SIGN_LINE_1 = "config.sign.line.1";
			public static final String SIGN_LINE_2 = "config.sign.line.2";
			public static final String SIGN_LINE_3 = "config.sign.line.3";
			
		}
		
	}
	
	public final class Message {
		
		private Message () {
			throw new IllegalStateException( STATE_WARNING );
		}
		
		public static final String CANCEL = "plugin.message.cancel";
		public static final String DELETE = "plugin.message.delete";
		public static final String DELETED = "plugin.message.deleted";
		public static final String UP_SAVE = "plugin.message.up.save";
		public static final String UP_SET = "plugin.message.up.set";
		public static final String DOWN_SAVE = "plugin.message.down.save";
		public static final String DOWN_SET = "plugin.message.down.set";
		public static final String DECLARATION = "plugin.message.declaration";
		public static final String OVERRIDE = "plugin.message.override";
		
	}
	
	public final class Value {
		
		private Value () {
			throw new IllegalStateException( STATE_WARNING );
		}
		
		public static final String UP = ".up";
		public static final String DOWN = ".down";
		public static final long TP_WAIT = 3L;
		
	}
	
	public final class Commands {
		
		private Commands () {
			throw new IllegalStateException( STATE_WARNING );
		}
		
		
		public final class SignLift {
			
			private SignLift () {
				throw new IllegalStateException( STATE_WARNING );
			}
			
			public final class Usage {
				
				private Usage () {
					throw new IllegalStateException( STATE_WARNING );
				}
				
				public static final String GENERAL = "&cBenutzung: &asignlift <up/down/cancel/delete>";
				public static final String UP = "&aup &c- &eAn der Position die für eine Lift-Hoch Position gesetzt werden soll";
				public static final String DOWN = "&adown &c- &eAn der Position die für eine Lift-Runter Position gesetzt werden soll";
				public static final String CANCEL = "&acancel &c- &eup/down Position verwerfen";
				public static final String DELETE = "&adelete &c- &eSchild löschen";
				
			}
		}
		
		
		
	}
	
}

