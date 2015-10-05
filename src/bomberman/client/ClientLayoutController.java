package bomberman.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientLayoutController implements Initializable
{
	@FXML
	private ImageView gameImage;

	private BomberManClient mainApp;

	public ClientLayoutController ()
	{
	}

	public void setMainApp (BomberManClient mainApp)
	{
		this.mainApp = mainApp;
	}

	public BomberManClient getMainApp ()
	{
		return mainApp;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources)
	{
		return;
	}
}
