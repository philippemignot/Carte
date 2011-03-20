import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.Serializable;


public class Sprite implements ImageObserver, Serializable
{
	private Image image;
	private String codeImage = "";
	private int largeur;
	private int hauteur;
	
	public Sprite(Image img, String code, int l, int h)
	{
		image = img;
		hauteur = h;
		largeur = l;
	}

	public Sprite(int l, int h) 
	{
		hauteur = h;
		largeur = l;
	}

	public Sprite(Sprite sprite)
    {
	    image = sprite.image;
	    largeur = sprite.largeur;
	    hauteur = sprite.hauteur;
	    codeImage = new String(sprite.codeImage);
    }

	public void draw(Graphics g) 
	{
		if (image != null) 
		{
			g.drawImage(image, 0, 0, largeur, hauteur, this);
		}		
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setImage(Image img, String codeImg) 
	{
		this.image = img;
		this.codeImage = codeImg;		
	}

	public Image getImage() 
	{
		return image;
	}
	
	public String getCode()
	{
		return codeImage;
	}
}
