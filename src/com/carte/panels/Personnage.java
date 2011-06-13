package com.carte.panels;

import java.awt.Image;

import com.carte.sprites.Sprite;

public class Personnage
{
	private Sprite 	sprite;
	private int		x = 0;
	private int		y = 0;
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

	public void deplacer(int direction)
    {
	    switch (direction)
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
	
	public int getX()
	{
		return x;
	}

	public int getY()
    {
	    return y;
    }
}
