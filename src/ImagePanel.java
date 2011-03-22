import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ImagePanel extends JPanel
{
	private Image img;
	private Dimension dimImage;
	
	public ImagePanel(Image img)
	{
		this.img = img;
	}
	
	/**
	 * A utiliser si l'image peut être nulle
	 * @param img
	 * @param dim
	 */
	public ImagePanel(Image img, Dimension dim)
	{
		this.img = img;
		this.dimImage = dim;
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
	}
	
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public Image getImage()
	{
		return this.img;
	}
	
	public void setDimension(Dimension dim)
	{
		dimImage = dim;
	}
	
	public Dimension getDimension()
	{
		return dimImage;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		int height = (dimImage != null) ? dimImage.height : img.getHeight(this);
		int width = (dimImage != null) ? dimImage.width : img.getWidth(this);
		//System.out.println(width);
		g.fillRect(0, 0, width, height);
		if(img != null)
		{
			g.drawImage(img, 0, 0, width, height, this);
		}
	}
}
