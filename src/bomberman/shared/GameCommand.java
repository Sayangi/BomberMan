package bomberman.shared;

public class GameCommand
{
	public static final String NO_ACTION = "NULL";
	public static final String MOVEMENT_UP = "UP";
	public static final String MOVEMENT_DOWN = "DOWN";
	public static final String MOVEMENT_LEFT = "LEFT";
	public static final String MOVEMENT_RIGHT = "RIGHT";

	private String command;

	public GameCommand (String command)
	{
		this.command = command;
	}

	public String getCommand ()
	{
		return command;
	}

	public void setCommand (String command)
	{
		this.command = command;

		return;
	}
}
