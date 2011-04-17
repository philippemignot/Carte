import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements MouseListener
{
	private Image img; // L'image globale
	private BufferedImage imgDraw; // L'image à dessiner
	private Dimension dimImage; // La dimension de l'image
	private int imgStatActive = 0;
	private int imgAnimX = 0; // 0 : statique ; 2 et + : animation
	private boolean	animation = false;
	private int[]	typeAnim; // 32 : red / 10 : type d'animation :
							  // 0 = cyclique continue; 1 = statique; 2 = depl haut; 3 = depl droite; 4 = depl bas; 5 = depl gauche
	private int[]	nbrAnim;  // 32 : green / 5 : nombre d'images composant l'animation
	private int[]	suiteAnim;// 32 : blue / 5 : image statique à afficher ensuite
	private int[]	intervalleTpsAnim; // 33 : red + green + blue : Intervalle de temps entre chaque image de l'animation ; 
	private int		translateX = 0; // Translation des coordonnées du graphique en abscisse
	private int		translateY = 0; // Translation des coordonnées du graphique en ordonnées
	
	/**
	 * Crée un nouveau panneau d'image à partir d'une image avec les dimensions voulues.
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
		this.addMouseListener(this);
		if(img != null)
		{
			imgDraw = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
			imgDraw.getGraphics().clearRect(0, 0, dimImage.width, dimImage.height);
			imgDraw.getGraphics().drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
			int nbLignes = getMaxAnimImg();
			animation = (nbLignes > 0 ) ? true : false;
			if(animation)
			{
				typeAnim =  new int[nbLignes];
				nbrAnim =  new int[nbLignes];
				suiteAnim =  new int[nbLignes];
				intervalleTpsAnim =  new int[nbLignes];
				readInfosAnim();
			}
		}
	}
	
	private void readInfosAnim()
    {
		BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		if(bufImage.getGraphics().drawImage(img, 0, 0, null))
		{
			
			if(img.getWidth(null) > 32)
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
							System.out.println(">>> " + i*32 + " : " + typeAnim[i] + " " + nbrAnim[i] + " " + suiteAnim[i] + " " + intervalleTpsAnim[i]);
							
				}
			}
		}
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

		g.fillRect(0, 0, dimImage.width, dimImage.height);
		g.translate(translateX, translateY);
		if(img != null)
		{
			
			g.drawImage(imgDraw, 0, 0, dimImage.width, dimImage.height, this);
			
			if(translateX > 0)
			{
				g.translate(-32, 0);
				g.drawImage(imgDraw, 0, 0, dimImage.width, dimImage.height, this);
			}
			else if(translateY > 0)
			{
				g.translate(0, -32);
				g.drawImage(imgDraw, 0, 0, dimImage.width, dimImage.height, this);
			}
			else if(translateX < 0)
			{
				g.translate(32, 0);
				g.drawImage(imgDraw, 0, 0, dimImage.width, dimImage.height, this);			}
			else if(translateY < 0)
			{
				g.translate(0, 32);
				g.drawImage(imgDraw, 0, 0, dimImage.width, dimImage.height, this);			}
		}
	}

	@Override
    public void mouseClicked(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void mousePressed(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void mouseReleased(MouseEvent e)
    {
	    if(e.getButton() == MouseEvent.BUTTON1 && animation)
	    {
    		new Thread(new Runnable() 
    		{  
    			public void run() 
    			{ 
    				translateX = 0;
    				translateY = 0;
    				
    				for(int i = 0 ; i < nbrAnim[imgStatActive] ; i++)
    				{
	    				imgAnimX = i + 2;
	    				
	    				
	    				switch(typeAnim[imgStatActive])
	    				{
	    					case 2:
	    						translateY -= dimImage.height / nbrAnim[imgStatActive];
	    					break;
	    					case 3:
	    						translateX += dimImage.width / nbrAnim[imgStatActive];
	    						break;
	    					case 4:
	    						translateY += dimImage.height / nbrAnim[imgStatActive];
	    						break;
	    					case 5:
	    						translateX -= dimImage.width / nbrAnim[imgStatActive];
	    						break;
	    				}
	    				
	    				try
	    				{
	    					Thread.sleep(intervalleTpsAnim[imgStatActive]);
	    				}catch(InterruptedException e2)
	    				{
	    					System.err.println("erreur");
	    				}
	    				refreshImg();
    				}
    				imgAnimX = 0;
    				
    				imgStatActive = suiteAnim[imgStatActive];
    				translateX = 0;
    				translateY = 0;
    				refreshImg();
    			}

				
    			
    		}).start(); 
	    }else if(e.getButton() == MouseEvent.BUTTON3 && animation)  
	    {
	    	if(imgStatActive < getMaxStatImg() - 1)
			{
				imgStatActive++;
			}else
			{
				imgStatActive = 0;
			}
	    	refreshImg();
	    }
    }
	
	private void refreshImg()
    {
		imgDraw.getGraphics().clearRect(0, 0, dimImage.width, dimImage.height);
		imgDraw.getGraphics().drawImage(img, -imgAnimX*32, -imgStatActive*32, img.getWidth(null), img.getHeight(null), null);
		this.repaint();
    }

	/**
	 * Renvoie le nombre maximal d'images statiques contenues dans l'image
	 * @return
	 * 		Le nombre maximal d'images statiques
	 */
	private int getMaxStatImg()
    {
	    return (img.getHeight(this) / dimImage.height);
    }
	
	/**
	 * Renvoie le nombre maximal d'animations contenues dans l'image
	 * @return
	 * 		Le nombre maximal d'animations
	 */
	private int getMaxAnimImg()
	{
		return ((img.getWidth(this) / dimImage.width) - 2);
	}

	@Override
    public void mouseEntered(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void mouseExited(MouseEvent e)
    {
	    // TODO Auto-generated method stub
	    
    }
}
