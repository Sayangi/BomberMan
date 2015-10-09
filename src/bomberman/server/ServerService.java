package bomberman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bomberman.shared.GameCommand;
import javafx.scene.image.Image;

public class ServerService extends Thread
{
	private Socket client;
	private boolean isLocked;
	private ResourceHandler resourceHandler;

	public ServerService (Socket client, ResourceHandler resourceHandler)
	{
		this.client = client;
		this.isLocked = true;
		this.resourceHandler = resourceHandler;
	}

	public boolean isLocked ()
	{
		return isLocked;
	}

	public void setLocked (boolean isLocked)
	{
		this.isLocked = isLocked;

		return;
	}

	@Override
	public void run ()
	{
		System.out.println ("Thread ID: " + this.toString ());
		service ();

		return;
	}

	private void service ()
	{
		ObjectInputStream channelFromClient = null;
		ObjectOutputStream channelToClient = null;
		Object inResult = null;

		try
		{
			channelToClient = new ObjectOutputStream (client.getOutputStream ());
			channelFromClient = new ObjectInputStream (client.getInputStream ());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		while (isLocked)
		{
			try
			{
				inResult = channelFromClient.readObject ();
				if (inResult instanceof GameCommand)
				{
					GameCommand gc = (GameCommand) inResult;
					// Image outResult = process (gc);
					String response = gc.getCommand ();
					System.out.println ("Msg. from client: " + response);
					channelToClient.writeObject (response);

					/*if (outResult != null)
					{
						// channelToClient.writeObject (outResult); // XXX
					}*/
				}
				if (inResult instanceof String)
				{
					String msg = (String) inResult;
					System.out.println ("Msg. from client: " + msg);
					channelToClient.writeObject (msg);
				}
			}
			catch (ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace ();
			}
			catch (IOException ioe)
			{
				try
				{
					isLocked = false;
					client.close ();
				}
				catch (IOException e)
				{
					e.printStackTrace ();
				}
				ioe.printStackTrace ();
			}
		}

		return;
	}

	private Image process (GameCommand gameCommand)
	{
		String commandString = gameCommand.getCommand ();
		Image result = resourceHandler.searchImage (commandString);

		return result;
	}
}
