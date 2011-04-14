import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements MouseListener
{
	private Image img; // L'image du panneau
	private Dimension dimImage; // La dimension de l'image
	private int imgStatActive = 0;
	private int imgAnimX = 0; // 0 : statique ; 2 et + : animation
	
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
		if(img != null)
		{
			g.drawImage(img, -imgAnimX*32, -imgStatActive*32, img.getWidth(this), img.getHeight(this), this);
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
	    if(e.getButton() == MouseEvent.BUTTON1)
	    {
    		new Thread(new Runnable() 
    		{  
    			public void run() 
    			{ 
    				for(int i = 0 ; i < getMaxAnimImg() ; i++)
    				{
	    				imgAnimX = i + 2;
	    				
	    				
	    				try
	    				{
	    					Thread.sleep(100);
	    				}catch(InterruptedException e2)
	    				{
	    					System.err.println("erreur");
	    				}
	    				ImagePanel.this.repaint();
    				}
    				imgAnimX = 0;
    				
    				if(imgStatActive < getMaxStatImg() - 1)
    				{
    					imgStatActive++;
    				}else
    				{
    					imgStatActive = 0;
    				}
    				ImagePanel.this.repaint();
    			}
    			
    		}).start();  
	    }  
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
