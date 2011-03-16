import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;


public class Sprite implements ImageObserver
{
	private Image image;
	private int largeur;
	private int hauteur;
	
	public Sprite(Image img, int l, int h)
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

	public void setImage(Image img) 
	{
		this.image = img;
		
	}

	public Image getImage() 
	{
		return image;
	}
}
