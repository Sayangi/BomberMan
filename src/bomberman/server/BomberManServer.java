package bomberman.server;

import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

public class BomberManServer extends Application
{
	private final int PORT_NUMBER = 2222;
	private ConnectionHandler connectionHandler;
	private ResourceHandler resourceHandler;
	private Socket toClient;

	public static void main (String [] args)
	{
		launch (args);

		BomberManServer serverApp = new BomberManServer ();
		serverApp.startServer ();

		return;
	}

	private void startServer ()
	{
		System.out.println ("*** Server starting on port " + PORT_NUMBER);
		resourceHandler = new ResourceHandler ();
		resourceHandler.loadImages ();
		connectionHandler = new ConnectionHandler (null, PORT_NUMBER);
		connectionHandler.createServer ();
		toClient = connectionHandler.accept ();
		System.out.println ("Client connected from " + toClient.getLocalAddress ());
		Thread service = new ServerService (toClient, resourceHandler);
		service.start ();
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		primaryStage.show ();
		primaryStage.close ();

		return;
	}

}
