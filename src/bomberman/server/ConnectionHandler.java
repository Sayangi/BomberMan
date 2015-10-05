package bomberman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionHandler {

	private String host;
	private int port;
	private ServerSocket mySocket;
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
			if (mySocket != null)
			{
				mySocket.close ();
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}
	}

}
