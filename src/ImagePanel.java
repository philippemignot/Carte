import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ImagePanel extends JPanel
{
	private Image img; // L'image du panneau
	private Dimension dimImage; // La dimension de l'image
	
	/**
	 * Crée un nouveau panneau d'image à partir de l'image
	 * 
	 * @param img
	 * 			L'image du panneau
	 */
	public ImagePanel(Image img)
	{
		this.img = img;
	}
	
	/**
	 * Crée un nouveau panneau d'image à partir d'une image avec les dimensions voulues.
	 * A utiliser si l'image peut être nulle
	 * 
	 * @param img
	 * 			L'image du panneau
	 * @param dim
	 * 			Les dimensions souhaitées
	 */
	public ImagePanel(Image img, Dimension dim)
	{
		this.img = img;
		this.dimImage = dim;
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
	}
	
	/**
	 * Mets une nouvelle image dans le panneau
	 * 
	 * @param img
	 * 			Le nouvelle image
	 */
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	/**
	 * Renvoie l'image contenue dans le panneau
	 * 
	 * @return
	 * 		L'image du panneau
	 */
	public Image getImage()
	{
		return this.img;
	}
	
	/**
	 * Modifie les dimensions du panneau
	 * 
	 * @param dim
	 * 			Les nouvelles dimensions du panneau
	 */
	public void setDimension(Dimension dim)
	{
		dimImage = dim;
	}
	
	/**
	 * Renvoie les dimensions du panneau
	 * 
	 * @return
	 * 		Les dimensions du panneau
	 */
	public Dimension getDimension()
	{
		return dimImage;
	}
	
	@Override
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
