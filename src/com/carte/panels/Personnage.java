package com.carte.panels;

import java.awt.Image;
import java.util.ArrayList;

import com.carte.sprites.Sprite;
import com.carte.utils.Observateur;

public class Personnage implements Observateur
{
	private Sprite 	sprite;
	private int		x = 0;
	private int		y = 0;
	private int 	deplX = 0;
	private int		deplY = 0;
	private boolean anime = false;
	private int		statutAnim = -1; // -1 - immobile
	private ArrayList<Integer>	nextStatut = new ArrayList<Integer>();
	private static Integer STATUT_NULL = new Integer(-1);

	/**
	 * L'orientation du personnage
	 * 
	 * Les 4 valeurs sont :
	 * 0 - haut
	 * 1 - droite
	 * 2 - bas
	 * 3 - gauche
	 */
	private int orientation = HAUT;
	
	public static int HAUT = 2;
	public static int DROITE = 1;
	public static int BAS = 0;
	public static int GAUCHE = 3;
	
	public Personnage(Sprite sprite)
	{
		this(sprite, 0);
	}
	
	public Personnage(Sprite sprite, int orientation)
	{
		this.sprite = sprite;
		this.orientation = orientation;
		this.sprite.addObservateur(this);
		this.nextStatut.add(-1);
	}
	
	public void placer(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void rotation(int direction)
	{
		if (direction >= 0 && direction <= 4)
		{
			sprite.setImgStat(direction);
			orientation = direction;
		}
	}

	public Image getDrawImage()
    {
	    return sprite.getDrawImage();
    }
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public int getOrientation()
	{
		return this.orientation;
	}

	public void deplacer()
    {
		anime = true;
		
		new Thread(new Runnable() 
		{  
			public void run() 
			{ 
				statutAnim = orientation;
				sprite.startAnimation(Sprite.ANIM_PLAY_NORMAL);
				
				deplX = 0;
				deplY = 0;
				switch (orientation)
				{
					// bas
					case 0 :
						y ++;
						break;
						
						// droite
					case 1 :
						x ++;
						break;
						
						// haut
					case 2 :
						y --;
						break;
						
						// gauche
					case 3 :
						x --;
						break;
				}
				if (!nextStatut.contains(STATUT_NULL))
				{
					try
					{
						Thread.sleep(sprite.getIntervalleTpsAnim());
					}catch(InterruptedException e2)
					{
						System.err.println("Pause du thread interrompue dans ImagePanel.startAnimation()");
					}
				}
				sprite.refreshImg();
				statutAnim = -1;
//				sprite.refreshImg();
			}
			
		}).start(); 
		
    }
	
	public int getX()
	{
		return x;
	}

	public int getY()
    {
	    return y;
    }
	
	public int getDeplX()
	{
		return deplX;
	}
	
	public int getDeplY()
	{
		return deplY;
	}

	@Override
    public void update(ArrayList<Sprite> sprites, String source)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(boolean[] bool)
    {
    }

	@Override
    public void update(int[] integer)
    {
	    deplX = integer[0];
	    deplY = integer[1];
    }

	@Override
    public void update()
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(String[] string)
    {
	    // TODO Auto-generated method stub
	    
    }
	
	public void setNextStatut(int keyCode, boolean pressed)
	{
		switch(keyCode)
		{
			case 83:
				updateNextStatut(0, pressed);
			break;
			case 68:
				updateNextStatut(1, pressed);
				break;
			case 90:
				updateNextStatut(2, pressed);
				break;
			case 81:
				updateNextStatut(3, pressed);
				break;
		}
		
		if (nextStatut.isEmpty())
		{
			nextStatut.add(STATUT_NULL);
		}
	}

	private void updateNextStatut(Integer statutCode, boolean pressed)
    {
	    if (pressed)
	    {
	    	if (nextStatut.contains(STATUT_NULL))
	    	{
	    		nextStatut.remove(STATUT_NULL);
	    	}
	    	nextStatut.add(statutCode);
	    } else
	    {
	    	if (nextStatut.contains(statutCode))
	    	{
	    		nextStatut.remove(statutCode);
	    	}
	    }
    }
}
