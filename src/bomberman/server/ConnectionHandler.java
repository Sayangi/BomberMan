package bomberman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler
{
	private String host;
	private int port;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectInputStream channelFromClient;
	private ObjectOutputStream channelToClient;

	public ConnectionHandler (String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void close ()
	{
		try
		{
			if (serverSocket != null)
			{
				serverSocket.close ();
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}
	}

	public void createServer ()
	{
		try
		{
			serverSocket = new ServerSocket (port);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		return;
	}

	public Socket accept ()
	{
		Socket client = null;

		try
		{
			client = serverSocket.accept ();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		return client;
	}
}
