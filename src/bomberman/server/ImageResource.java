package bomberman.server;

import javafx.scene.image.Image;

public class ImageResource
{
	private Image image;
	private String description;

	public ImageResource (Image image, String description)
	{
		this.image = image;
		this.description = description;
	}

	public Image getImage ()
	{
		return image;
	}

	public void setImage (Image image)
	{
		this.image = image;
	}

	public String getDescription ()
	{
		return description;
	}

	public void setDescription (String description)
	{
		this.description = description;
	}
}
