package com.carte.sprites;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.ArrayList;

import com.carte.utils.Observable;
import com.carte.utils.Observateur;

@SuppressWarnings("serial")
public class Sprite implements Serializable, Observable
{
	private Image image; // L'image du sprite
	private String codeImage = ""; // Le code image correspondant au sprite
	private int defImage; // L'image par défaut à afficher du sprite
	private int largeur; // La largeur du sprite
	private int hauteur; // La hauteur du sprite
	
	// Pour dessiner
	private BufferedImage imgDraw; // L'image à dessiner
	private Graphics2D g2D; // Le graphics de imgDraw
	private int imgStatActive = 0;
	private int imgAnimX = 0; // 0 : statique ; 2 et + : animation
	private boolean	animation = false;
	private int[]	typeAnim; // 32 : red / 10 : type d'animation :
							  // 0 = cyclique continue; 1 = statique; 4 = depl haut; 3 = depl droite; 2 = depl bas; 5 = depl gauche
	public static int ANIM_CYCLE = 0;
	public static int ANIM_STATIQUE = 1;
	public static int ANIM_DEPL_BAS = 2;
	public static int ANIM_DEPL_DROITE = 3;
	public static int ANIM_DEPL_HAUT = 4;
	public static int ANIM_DEPL_GAUCHE = 5;
	
	private int[]	nbrAnim;  // 32 : green / 5 : nombre d'images composant l'animation
	private int[]	suiteAnim;// 32 : blue / 5 : image statique à afficher ensuite
	private int[]	intervalleTpsAnim; // 33 : red + green + blue : Intervalle de temps entre chaque image de l'animation ; 
	private int		translateX = 0; // Translation des coordonnées du graphique en abscisse
	private int		translateY = 0; // Translation des coordonnées du graphique en ordonnées
	private int 	typeAnimationAff = 0; // Type d'animation : 2 => Fixe (pas de déplacement ; 1 => Boucle (déplacement répété pour simuler 2 cases) ; 0 => Normal (joue l'animation prévue)
	
	public static int ANIM_PLAY_FIXE = 2;
	public static int ANIM_PLAY_BOUCLE = 1;
	public static int ANIM_PLAY_NORMAL = 0;

	// Liste des observateurs
	private ArrayList<Observateur> listeObservateur =
	        new ArrayList<Observateur>();
	private boolean isAnimated = false;

	
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
		defImage = 0;
		
		// Si l'image est non nulle, on créé l'image à afficher
		initAnim();
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
	    defImage = sprite.defImage;
	    
	 // Si l'image est non nulle, on créé l'image à afficher
	    initAnim();
    }
	
	private void initAnim()
	{
		if(image != null)
		{
			imgDraw = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
			g2D = imgDraw.createGraphics();
			g2D.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);

			// Si on a plusieurs lignes, alors on set les infos d'animation
			int nbLignes = getMaxStatImg();
			animation = (nbLignes > 1 ) ? true : false;
			if(animation)
			{
				typeAnim =  new int[nbLignes];
				nbrAnim =  new int[nbLignes];
				suiteAnim =  new int[nbLignes];
				intervalleTpsAnim =  new int[nbLignes];
				imgStatActive = defImage;
				readInfosAnim();
			}
		}
	}
	
	/**
	 * Lis les informations d'animations contenues dans l'image pour chaque image statique contenue :
	 * Le type d'animation, le nombre d'images conposant l'animation, l'image statique à afficher après l'animation
	 * et le nombre de ms séparant chaque image de l'animation.
	 */
	private void readInfosAnim()
    {
		BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		if(bufImage.getGraphics().drawImage(image, 0, 0, null))
		{
			
			if(image.getWidth(null) > 32)
			{
				for(int i = 0 ; i < getMaxStatImg() ; i++)
				{
					int pixel = bufImage.getRGB(32, 32*i);
					typeAnim[i] = ((pixel >> 16) & 0xff) / 10;
					nbrAnim[i] = ((pixel >> 8) & 0xff) / 5;
					suiteAnim[i] = (pixel & 0xff) / 5;
					int pixel2 = bufImage.getRGB(33, 32*i);
					intervalleTpsAnim[i] = ((pixel2 >> 16) & 0xff);
					intervalleTpsAnim[i] += ((pixel2 >> 8) & 0xff);
					intervalleTpsAnim[i] += (pixel2 & 0xff);
					if(intervalleTpsAnim[i] == 0)
					{
						intervalleTpsAnim[i] = 100;
					}
//					System.out.println(">>> " + i*32 + " : " + typeAnim[i] + " " + nbrAnim[i] + " " + suiteAnim[i] + " " + intervalleTpsAnim[i]);
							
				}
			}
		}
    }

	/**
	 * Dessine le sprite
	 * 
	 * @param g
	 * 		Graphics (doit être donné par la machine)
	 */
	public void draw(Graphics g) 
	{
		// Avec déplacement
		if(typeAnimationAff < 2)
		{
			g.translate(translateX, translateY);
		}
		
		if(image != null)
		{	
			g.drawImage(imgDraw, 0, 0, largeur, hauteur, null);
			
			// pour un effet de boucle
			if(typeAnimationAff == 1)
			{
				if(translateX > 0)
				{
					deplImg(-32, 0, g);
				}
				else if(translateY > 0)
				{
					deplImg(0, -32, g);
				}
				else if(translateX < 0)
				{
					deplImg(32, 0, g);
				}
				else if(translateY < 0)
				{
					deplImg(0,32, g);
				}				
			}
		}
		if(typeAnimationAff < 2)
		{
			g.translate(-translateX, -translateY);
		}
	}
	
	private void deplImg(int x, int y, Graphics g)
    {
		g.translate(x, y);
		g.drawImage(imgDraw, 0, 0, largeur, hauteur, null);
		g.translate(-x, -y);
    }

	/**
	 * Lance l'animation correspondant à l'image statique demandée
	 * 
	 * @param 
	 * 		Le type d'animation : fixe, boucle et normal
	 * 
	 */
	public void startAnimation(int typeAnimAff)
	{
		typeAnimationAff = (typeAnimAff < 3 && typeAnimAff >= 0) ? typeAnimAff : 0;
		if(animation)
		{
			if (typeAnimationAff == 0)
			{
				animer();
			} else
			{
				new Thread(new Runnable() 
				{  
					public void run() 
					{ 
						animer();
					}
					
				}).start(); 
			}
		}
		
	}
	
	private void animer()
    {
		isAnimated = true;
		translateX = 0;
		translateY = 0;
		int[] translate  = {largeur / (nbrAnim[imgStatActive] + 1), hauteur / (nbrAnim[imgStatActive] + 1)};
		int[] diffToAdd = getDiffToAdd();
		
		for(int i = 0 ; i < nbrAnim[imgStatActive] ; i++)
		{
			imgAnimX = i + 2;
			
			// Pour un effet de déplacement en boucle
			switch(typeAnim[imgStatActive])
			{
				case 2:
					translateY += translate[1] + diffToAdd[i];
					break;
				case 3:
					translateX += translate[0] + diffToAdd[i];
					break;
				case 4:
					translateY -= translate[1] + diffToAdd[i];
					break;
				case 5:
					translateX -= translate[0] + diffToAdd[i];
					break;
			}
			
			try
			{
				Thread.sleep(intervalleTpsAnim[imgStatActive]);
			}catch(InterruptedException e2)
			{
				System.err.println("Pause du thread interrompue dans ImagePanel.startAnimation()");
			}
			refreshImg();
		}
		
		// Fin de l'animation
		imgAnimX = 0;
		
		imgStatActive = suiteAnim[imgStatActive];
		translateX = 0;
		translateY = 0;
		isAnimated = false;
    }

	private int[] getDiffToAdd()
    {
		int[] diffToAdd = new int [nbrAnim[imgStatActive]];
		
		for (int i = 0 ; i < largeur % nbrAnim[imgStatActive] ; i ++)
		{
			diffToAdd[i] = 1;
		}
		
		for (int i = largeur % nbrAnim[imgStatActive] ; i < nbrAnim[imgStatActive] ; i ++)
		{
			diffToAdd[i] = 0;
		}
		
	    return diffToAdd;
    }

	/**
	 * Modifie l'image statique par défaut et active.
	 * 
	 * @param imgStat
	 * 		Le numéro de l'image statique à afficher. Commence à 0.
	 * @return
	 * 		true - Le numéro est valide et l'image a été modifiée.
	 * 		false - Le numéro n'est pas valide. L'image par défaut n'a pas été modifiée.
	 */
	private boolean setDefImage(int imgStat)
    {
	    boolean valueOk = (imgStat < getMaxStatImg() && imgStat >= 0) ? true : false; 
	    
	    if(valueOk)
	    {
	    	imgStatActive = imgStat;
	    }
	    
	    return valueOk;
    }
	
	/**
	 * Modifie l'image par défaut en passant à la valeur suivante.
	 */
	public void changerDefImg()
	{
		int numActive = imgStatActive;
		if(numActive < getMaxStatImg() - 1)
		{
			numActive++;
		}else
		{
			numActive = 0;
		}
		setDefImage(numActive);
		refreshImg();
	}
	
	/**
	 * Mets à jour l'image à afficher
	 */
	public void refreshImg()
    {
//		g2D.clearRect(0, 0, largeur, hauteur);
		imgDraw = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
		g2D = imgDraw.createGraphics();
		g2D.drawImage(image, -imgAnimX*32, -imgStatActive*32, image.getWidth(null), image.getHeight(null), null);
		updateObservateur();
    }

	/**
	 * Renvoie le nombre maximal d'images statiques contenues dans l'image
	 * @return
	 * 		Le nombre maximal d'images statiques
	 */
	private int getMaxStatImg()
    {
	    return (image.getHeight(null) / hauteur);
    }
	
	/**
	 * Modifie l'image associée à ce sprite
	 * 
	 * @param img
	 * 			La nouvelle image
	 * @param codeImg
	 * 			Le code image associé à la nouvelle image
	 */
	public void setImage(Image img, String codeImg, int defImg) 
	{
		this.image = img;
		this.codeImage = codeImg;	
		this.defImage = defImg;
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
	 * Renvoie l'image à dessiner associée à ce sprite
	 * 
	 * @return
	 * 		L'image associée au sprite
	 */
	public Image getDrawImage() 
	{
		return imgDraw;
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
	 * Renvoie l'image par défaut du sprite
	 * 
	 * @return
	 * 		L'image par défaut du sprite
	 */
	public int getDefautImage()
	{
		return defImage;
	}
	
	/**
	 * Modifie l'image par défaut
	 * 
	 * @param defImg
	 * 			Le numéro de l'image par défaut. Commence à 0.
	 */
	public void setDefautImage(int defImg)
	{
		defImage = defImg;
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

	@Override
	public void addObservateur(Observateur obs)
	{
		listeObservateur.add(obs);
	}

	@Override
	public void rmvObservateur(Observateur obs)
	{
		listeObservateur.remove(obs);
	}

	@Override
	public void updateObservateur()
	{
		for (Observateur obs : listeObservateur)
		{
			obs.update();
//			if (translateX != 0 || translateY != 0)
//			{
				int[] translate = {translateX, translateY};
				obs.update(translate);
//			}
		}
	}

	/**
	 * Indique si le sprite est en train de s'animer.
	 * 
	 * @return
	 * 		true - le sprite est en train de s'animer
	 */
	public boolean isAnimated()
    {
		return isAnimated;
    }
	
	public void setImgStat(int img)
	{
		img = (img >= getMaxStatImg()) ? imgStatActive : img;
		imgStatActive = img;
		refreshImg();
	}
	
	public boolean hasAnimation()
	{
		return animation;
	}

	public int getIntervalleTpsAnim()
    {
	    return intervalleTpsAnim[imgStatActive];
    }
}
