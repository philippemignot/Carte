import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;


@SuppressWarnings("serial")
public abstract class AbstractCase extends JButton implements MouseListener, Serializable
{

	protected int 		largeur; // Nombres de pixels de la case en largeur
	protected int 		hauteur; // Nombres de pixels de la case en hauteur

	protected boolean 	isChosen 	= false;	// Indique si la case est survolée
	protected boolean 	isHovered 	= false;	// Indique si la case est sélectionnée
	
	// Par défaut, la bordure est affichée de couleur rouge et le fond est noir.
	protected boolean bordureOn 	= true; 		// Affiche ou non une bordure
	protected Color couleurFond 	= Color.black; 	// Fond de la case
	protected Color couleurBordure 	= Color.RED; 	// Bordure de la case
	
	/**
	 * Crée une nouvelle case avec des couleurs par défaut
	 * 
	 * @param l
	 *            La largeur de la case en pixels
	 * @param h
	 *            La hauteur de la case en pixels
	 */
	public AbstractCase(int l, int h) 
	{
		largeur = l;
		hauteur = h;
		setPreferredSize(new Dimension(l, h));
		setMinimumSize(new Dimension(l, h));
		setMaximumSize(new Dimension(l, h));
		
		this.addMouseListener(this);
		this.setBorderPainted(false);
	}
	
	/**
	 * Crée une nouvelle case en spécifiant ses couleurs
	 * 
	 * @param l
	 *            La largeur de la case en pixels
	 * @param h
	 *            La hauteur de la case en pixels
	 * @param couleurB
	 *            La couleur de la bordure
	 * @param couleurF
	 *            La couleur du fond
	 */
	public AbstractCase(int l, int h, Color couleurB, Color couleurF) 
	{
		largeur = l;
		hauteur = h;
		setPreferredSize(new Dimension(l, h));
		setMinimumSize(new Dimension(l, h));
		setMaximumSize(new Dimension(l, h));
		
		couleurBordure = couleurB;
		couleurFond = couleurF;
		
		this.addMouseListener(this);
		this.setBorderPainted(false);
	}
	
	/**
	 * Modifie l'affichage de la bordure
	 * 
	 * @param bordure
	 *            Affiche ou désaffiche la bordure
	 */
	public void setBordure(boolean bordure) 
	{
		bordureOn = bordure;
	}

	/**
	 * Permet de savoir si la bordure de la case est affichée
	 * 
	 * @return true si la bordure est affichée, false sinon
	 */
	public boolean getBordure() 
	{
		return bordureOn;
	}

	/**
	 * Modifie la couleur de fond de la case
	 * 
	 * @param couleur
	 *            La couleur de fond à afficher
	 */
	public void setCouleurFond(Color couleur) 
	{
		couleurFond = couleur;
	}

	/**
	 * Modifie la couleur de la bordure de la case
	 * 
	 * @param couleur
	 *            La couleur de la bordure à afficher
	 */
	public void setCouleurBordure(Color couleur) 
	{
		couleurBordure = couleur;
	}
	
	/**
	 * Encadre la case pour signifier qu'elle est survolée
	 * 
	 * @param hovered si la case est survolée
	 */
	public void setHovered(boolean hovered)
	{
		isHovered = hovered;
	}
	
	/**
	 * Permet de savoir si une case est visuellement encadrée parce qu'elle est survolée
	 * 
	 * @return true si isHovered is set to true
	 */
	public boolean isHovered()
	{
		return isHovered;
	}
	
	/**
	 * Permet de rendre visible la sélection d'une case
	 * 
	 * @param selected si la case est sélectionnée
	 */
	public void choose(boolean selected)
	{
		isChosen = selected;
	}
	
	/**
	 * Permet de savoir si une case est sélectionnée visuellement
	 * 
	 * @return true si isChosen is set to true
	 */
	public boolean isChosen()
	{
		return isChosen;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{

	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		isHovered = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		isHovered = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{

	}

}
