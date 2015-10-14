package bomberman.shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameIOHandler
{
	public static void main (String [] args)
	{
		String filename = "actiontest.ser";
		GameCommand oldCommand = new GameCommand (GameActions.MOVEMENT_RIGHT);
		System.out.println ("Old Command: " + oldCommand.getCommand ());
		GameIOHandler gameIOHandler = new GameIOHandler ();
		gameIOHandler.serializeObject (filename, oldCommand);
		GameCommand newCommand = gameIOHandler.deserializeObject (filename);
		System.out.println ("New Command: "+ newCommand.getCommand ());

		return;
	}

	public void serializeObject (String filename, GameCommand gameCommand)
	{
		try
		{
			System.out.println ("Serializing object...");
			FileOutputStream fileOut = new FileOutputStream (filename);
			ObjectOutputStream objectOut = new ObjectOutputStream (fileOut);
			objectOut.writeObject (gameCommand);
			objectOut.close ();
			fileOut.close ();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace ();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		return;
	}

	public GameCommand deserializeObject (String filename)
	{
		GameCommand gameCommand = null;
		try
		{
			System.out.println ("Deserializing object...");
			FileInputStream fileIn = new FileInputStream (filename);
			ObjectInputStream objectIn = new ObjectInputStream (fileIn);
			gameCommand = (GameCommand) objectIn.readObject ();
			objectIn.close ();
			fileIn.close ();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace ();
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace ();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace ();
		}

		return gameCommand;
	}
}
