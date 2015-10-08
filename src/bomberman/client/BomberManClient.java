package bomberman.client;

import java.io.IOException;
import bomberman.shared.GameCommand;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BomberManClient extends Application
{
	private final String HOST_NAME = "localhost";
	private final int PORT_NUMBER = 2222;

	private AnchorPane root;
	private Stage mainStage;
	private Scene mainScene;
	private ClientLayoutController clientLayoutController;
	private GameCommand gameCommand;
	private ConnectionHandler connectionHandler;

	public static void main (String [] args)
	{
		// GUI version
		// FIXME Change Thread to Task
		// launch (args);

		// CLI Version
		BomberManClient cliClient = new BomberManClient ();
		cliClient.cliStart ();


		return;
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		mainStage = primaryStage;

		initClientLayout ();
		setGameMovementKeys ();
		setupData ();
		// setupConnection (HOST_NAME, PORT_NUMBER); // FIXME Thread-problems
		// setupStreams (); // FIXME Thread-problems

		mainStage.setTitle ("BomberMan Client");
		mainStage.show ();



		/*Thread netSetup = new Thread (new Runnable ()
		{
			@Override
			public void run ()
			{
				try
				{
					setupConnection (HOST_NAME, PORT_NAME);
					wait ();
				}
				catch (InterruptedException ie)
				{
					ie.printStackTrace ();
				}
			}
		});
		netSetup.start ();

		Thread streamSetup = new Thread (new Runnable ()
		{
			@Override
			public void run ()
			{
				setupStreams ();
				notify ();
			}
		});
		streamSetup.start ();*/

		return;
	}

	@Override
	public void stop () throws Exception
	{
		connectionHandler.close ();
	}

	private void initClientLayout ()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader ();
			loader.setLocation (BomberManClient.class.getResource ("ClientLayout.fxml"));
			root = (AnchorPane) loader.load ();

			clientLayoutController = loader.getController ();
			clientLayoutController.setMainApp (this);

			mainScene = new Scene (root);
			mainStage.setScene (mainScene);

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		return;
	}

	private void setGameMovementKeys ()
	{
		final EventHandler <KeyEvent> movementEventHandler = new EventHandler <KeyEvent> ()
		{
			@Override
			public void handle (KeyEvent event)
			{
				KeyCode pressedButton = event.getCode ();

				switch (pressedButton)
				{
					case W:
						gameCommand.setCommand (GameCommand.MOVEMENT_UP);
						System.out.println ("UP");
						break;

					case A:
						gameCommand.setCommand (GameCommand.MOVEMENT_LEFT);
						System.out.println ("LEFT");
						break;

					case S:
						gameCommand.setCommand (GameCommand.MOVEMENT_DOWN);
						System.out.println ("DOWN");
						break;

					case D:
						gameCommand.setCommand (GameCommand.MOVEMENT_RIGHT);
						System.out.println ("RIGHT");
						break;

					default:
						break;
				}
				connectionHandler.sendCommandToServer (gameCommand);
				// System.out.println (pressedButton.toString () + " was pressed");
				// event.consume ();

				return;
			}
		};
		mainScene.setOnKeyPressed (movementEventHandler);

		return;
	}

	private void setupData ()
	{
		gameCommand = new GameCommand (GameCommand.NO_ACTION);

		return;
	}

	private void setupConnection (String host, int port)
	{
		connectionHandler = new ConnectionHandler (host, port);
		connectionHandler.connect ();

		return;
	}

	private void setupStreams ()
	{
		connectionHandler.setStreams ();

		return;
	}

	private void cliStart ()
	{
		System.out.println ("Connecting to " + HOST_NAME + " at " + PORT_NUMBER);
		setupData ();
		setupConnection (HOST_NAME, PORT_NUMBER);
		System.out.println ("***"); // XXX Debug
		setupStreams ();
		String test = "BomberMan";
		connectionHandler.sendCommandToServer (test);

		return;
	}
}
