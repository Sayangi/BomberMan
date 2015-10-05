package bomberman.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import bomberman.shared.GameCommand;

public class ConnectionHandler
{
	private String host;
	private int port;
	private Socket socketToServer;
	private ObjectInputStream channelFromServer;
	private ObjectOutputStream channelToServer;

	public ConnectionHandler (String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void connect ()
	{
		try
		{
			socketToServer = new Socket (host, port);
			channelFromServer = new ObjectInputStream (socketToServer.getInputStream ());
			channelToServer = new ObjectOutputStream (socketToServer.getOutputStream ());

		}
		catch (UnknownHostException uhe)
		{
			uhe.printStackTrace ();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}
	}

	public void close ()
	{
		try
		{
			if (socketToServer != null)
			{
				socketToServer.close ();
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}
	}

	public void sendCommandToServer (GameCommand gc)
	{
		try
		{
			channelToServer.writeObject (gc);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}
		return;
	}
}
