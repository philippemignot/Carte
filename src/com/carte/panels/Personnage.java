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
				sprite.startAnimation(Sprite.ANIM_PLAY_NORMAL);
				
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
	    deplX -= integer[0];
	    deplY += integer[1];
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
}
