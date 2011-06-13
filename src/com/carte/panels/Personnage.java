package com.carte.panels;

import java.awt.Image;

import com.carte.sprites.Sprite;

public class Personnage
{
	private Sprite sprite;
	
	/**
	 * L'orientation du personnage
	 * 
	 * Les 4 valeurs sont :
	 * 0 - haut
	 * 1 - droite
	 * 2 - bas
	 * 3 - gauche
	 */
	private int orientation = 1;
	
	public Personnage(Sprite sprite)
	{
		this(sprite, 0);
	}
	
	public Personnage(Sprite sprite, int orientation)
	{
		this.sprite = sprite;
		this.orientation = orientation;
	}
	
	public void rotationHaut()
	{
		sprite.setImgStat(2);
	}
	
	public void rotationDroite()
	{
		sprite.setImgStat(1);
	}
	
	public void rotationBas()
	{
		sprite.setImgStat(0);
	}
	
	public void rotationGauche()
	{
		sprite.setImgStat(3);
	}

	public Image getDrawImage()
    {
	    return sprite.getDrawImage();
    }
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
