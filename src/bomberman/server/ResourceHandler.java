package bomberman.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.image.Image;

public class ResourceHandler
{
	private List <ImageResource> images;

	public ResourceHandler ()
	{
		images = new ArrayList <ImageResource> ();
	}

	public void loadImages ()
	{
		Image imageA = new Image (this.getClass ().getResource ("/bomberman/resources/A.png").toExternalForm ());
		Image imageD = new Image (this.getClass ().getResource ("/bomberman/resources/D.png").toExternalForm ());
		Image imageS = new Image (this.getClass ().getResource ("/bomberman/resources/S.png").toExternalForm ());
		Image imageW = new Image (this.getClass ().getResource ("/bomberman/resources/W.png").toExternalForm ());

		images.add (new ImageResource (imageA, "LEFT"));
		images.add (new ImageResource (imageD, "RIGHT"));
		images.add (new ImageResource (imageS, "DOWN"));
		images.add (new ImageResource (imageW, "UP"));

		return;
	}

	public Image searchImage (String description)
	{
		Image result = null;

		Iterator <ImageResource> imageIterator = images.iterator ();
		while (imageIterator.hasNext ())
		{
			ImageResource current = imageIterator.next ();
			if (description.equals (current.getDescription ()))
			{
				result = current.getImage ();
				break;
			}
		}

		return result;
	}
}
