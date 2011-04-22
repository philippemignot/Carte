package com.carte.utils;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JPanel;


public class AnimMouvement extends JPanel
{
	public LinkedList<Image> 	images;				// La liste des images à afficher
	public Image 				imageAff = null;	// L'image à afficher
	public int[] 				coord = new int[2]; // coordonnées actuelles de l'image affichée : [0] = X ; [1] = Y
	public int 					direction;			// La direction dans laquelle déplacer l'image : 1 = haut; 2 = droite ; 3 = bas ; 4 = gauche
	public int					tailleCase;			// La taille d'une case
	
	public AnimMouvement(Image[] tabImg, int[] coordDep, int dir, int tCase)
	{
		coord[0] = coordDep[0];
		coord[1] = coordDep[1];
		direction = dir;
		tailleCase = tCase;
		
		int length = tabImg.length;
		
		if(length > 0)
		{
			for(int i = 0 ; i < length ; i++)
			{
				images.add(tabImg[i]);
			}
		}
		
		imageAff = tabImg[0];
	}

	public void start() 
	{
		int length = images.size();
		int deplUnit = tailleCase/length;
		int deplSupp = tailleCase%deplUnit;
		
		while(!images.isEmpty())
		{
			imageAff = images.removeFirst();
			int depl = deplUnit;
			if(images.isEmpty())
				depl += deplSupp;
			
			switch(direction)
			{
				case 1:
					coord[1] -= depl;
					break;
				case 2:
					coord[0] += depl;
					break;
					
				case 3:
					coord[1] += depl;
					break;
					
				case 4 : 
					coord[0] -= depl;
					break;
			}
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		// Dessine l'image si elle a été définie
		if (imageAff != null) 
		{
			g.drawImage(imageAff, coord[0], coord[1], tailleCase, tailleCase, this);
		}
	}
}
