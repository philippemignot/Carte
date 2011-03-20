import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Case extends AbstractCase 
{
	private Sprite sprite; // Image contenue dans la case, null s'il n'y a pas d'image

	/**
	 * @param l
	 *            La largeur de la case en pixels
	 * @param h
	 *            La hauteur de la case en pixels
	 */
	public Case(int l, int h) 
	{
		super(l,h);
		sprite = new Sprite(l,h);
	}

	/**
	 * @param l
	 *            La largeur de la case en pixels
	 * @param h
	 *            La hauteur de la case en pixels
	 * @param couleurB
	 *            La couleur de la bordure
	 * @param couleurF
	 *            La couleur du fond
	 */
	public Case(int l, int h, Color couleurB, Color couleurF) 
	{
		super(l, h, couleurB, couleurF);
		sprite = new Sprite(l,h);
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		// Dessine une couleur de fond si elle a été spécifiée
		if (couleurFond != null) {
			g.setColor(couleurFond);
			g.fillRect(0, 0, largeur, hauteur);
		}

		// Dessine l'image si elle a été définie
		if (sprite != null) {
			g.drawImage(sprite.getImage(), 0, 0, largeur, hauteur, this);
		}

		// Dessine une bordure
		if (bordureOn || isHovered || isChosen) 
		{
			g.setColor(couleurBordure);
			if(isHovered)
				g.setColor(Color.ORANGE);
			if(isChosen)
				g.setColor(Color.RED);
			
			g.drawRect(0, 0, largeur - 1, hauteur - 1);
		}
	}

	/**
	 * Efface l'image d'une case
	 */
	public void clear() 
	{
		sprite = null;
	}

	/**
	 * @param i
	 *            Image qui doit être affichée
	 */
	public void setImage(Image i, String codeImg) 
	{
		sprite.setImage(i, codeImg);
	}

	/**
	 * @return L'image affichée
	 */
	public Image getImage() 
	{
		return sprite.getImage();
	}
	
	public String getCodeImage()
	{
		return sprite.getCode();
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
