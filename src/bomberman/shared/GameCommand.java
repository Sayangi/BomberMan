package bomberman.shared;

import java.io.Serializable;

public class GameCommand implements Serializable
{
	private static final long serialVersionUID = 42L;

	private String command;

	public GameCommand ()
	{
	}

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
