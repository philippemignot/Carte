import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Sprite implements ImageObserver, Serializable
{
	private Image image; // L'image du sprite
	private String codeImage = ""; // Le code image correspondant au sprite
	private int largeur; // La largeur du sprite
	private int hauteur; // La hauteur du sprite
	
	/**
	 * Crée un nouveau sprite en indiquant sa taille et son image associée
	 * 
	 * @param img
	 * 			L'image du sprite
	 * @param code
	 * 			Le code associé à cette image
	 * @param l
	 * 			La largeur du sprite
	 * @param h
	 * 			La hauteur du sprite
	 */
	public Sprite(Image img, String code, int l, int h)
	{
		image = img;
		hauteur = h;
		largeur = l;
	}

	/**
	 * Crée un nouveau sprite en indiquant sa taille
	 * 
	 * @param l
	 * 			La largeur du sprite
	 * @param h
	 * 			La hauteur du sprite
	 */
	public Sprite(int l, int h) 
	{
		hauteur = h;
		largeur = l;
	}

	/**
	 * Crée un nouveau spriet à partir d'un autre
	 * 
	 * @param sprite
	 * 			Le sprite à copier
	 */
	public Sprite(Sprite sprite)
    {
	    image = sprite.image;
	    largeur = sprite.largeur;
	    hauteur = sprite.hauteur;
	    codeImage = new String(sprite.codeImage);
    }

	/**
	 * Dessine le sprite
	 * 
	 * @param g
	 * 		Graphics (doit être donné par la machine)
	 */
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

	/**
	 * Modifie l'image associée à ce sprite
	 * 
	 * @param img
	 * 			La nouvelle image
	 * @param codeImg
	 * 			Le code image associé à la nouvelle image
	 */
	public void setImage(Image img, String codeImg) 
	{
		this.image = img;
		this.codeImage = codeImg;		
	}

	/**
	 * Renvoie l'image associée à ce sprite
	 * 
	 * @return
	 * 		L'image associée au sprite
	 */
	public Image getImage() 
	{
		return image;
	}
	
	/**
	 * Renvoie le code image de l'image du sprite
	 * 
	 * @return
	 * 		Le code image associé à ce sprite
	 */
	public String getCode()
	{
		return codeImage;
	}
	
	/**
	 * Renvoie la hauteur du sprite
	 * 
	 * @return
	 * 		La hauteur du sprite
	 */
	public int getHauteur()
	{
		return this.hauteur;
	}
	
	/**
	 * Renvoie la largeur du sprite
	 * 
	 * @return
	 * 		La largeur du sprite
	 */
	public int getLargeur()
	{
		return this.largeur;
	}
}
