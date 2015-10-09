package bomberman.client;

import java.io.IOException;

import bomberman.shared.GameActions;
import bomberman.shared.GameCommand;
import javafx.application.Application;
import javafx.concurrent.Task;
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
		launch (args);

		// CLI Version
		// BomberManClient cliClient = new BomberManClient ();
		// cliClient.cliStart ();

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

		Task <Void> socketConnectTask = new Task <Void> ()
		{
			@Override
			protected Void call () throws Exception
			{
				setupConnection (HOST_NAME, PORT_NUMBER);
				setupStreams ();

				return null;
			}
		};
		Thread socketConnectThread = new Thread (socketConnectTask);
		socketConnectThread.start ();

		mainStage.setTitle ("BomberMan Client");
		mainStage.show ();

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
				String remark = "Local: ";

				switch (pressedButton)
				{
					case W:
						gameCommand.setCommand (GameActions.MOVEMENT_UP);
						System.out.println (remark + "UP");
						break;

					case A:
						gameCommand.setCommand (GameActions.MOVEMENT_LEFT);
						System.out.println (remark + "LEFT");
						break;

					case S:
						gameCommand.setCommand (GameActions.MOVEMENT_DOWN);
						System.out.println (remark + "DOWN");
						break;

					case D:
						gameCommand.setCommand (GameActions.MOVEMENT_RIGHT);
						System.out.println (remark + "RIGHT");
						break;

					default:
						break;
				}
				System.out.println ("Sent command is: " + gameCommand.getCommand ());
				connectionHandler.sendCommandToServer (gameCommand.getCommand ());
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
		gameCommand = new GameCommand (GameActions.NO_ACTION);

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
