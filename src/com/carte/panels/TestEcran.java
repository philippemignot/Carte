package com.carte.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.carte.sprites.Sprite;

public class TestEcran extends JPanel
{
	private Sprite[][][] sprites;
	private BufferedImage ecran;
	private int nbrLignes;
	private int nbrCol;
	private int largeur;
	private int hauteur;
	private int nbrNiveaux;
	
	public TestEcran(Sprite[][][] sprites, int nbrCol, int nbrLignes, int nbrNiveaux)
	{
		this.nbrCol = nbrCol;
		this.nbrLignes = nbrLignes;
		this.nbrNiveaux = nbrNiveaux;
		
		this.sprites = sprites;
		largeur = sprites[0][0][0].getLargeur();
		hauteur = sprites[0][0][0].getHauteur();
		
		this.ecran = new BufferedImage(nbrCol*largeur, nbrLignes*hauteur, BufferedImage.TYPE_INT_ARGB);
		initEcran();
		this.setPreferredSize(new Dimension(ecran.getWidth(), ecran.getHeight()));
		this.validate();
	}
	
	private void initEcran()
    {
		Graphics g = ecran.createGraphics();
		for (int i = 0 ; i < nbrLignes ; i ++)
		{
			for (int j = 0 ; j < nbrCol ; j ++)
			{
				for (int n = 0 ; n < nbrNiveaux ; n ++)
				{
					g.drawImage(sprites[i][j][n].getDrawImage(), j * 32, i * 32, largeur, hauteur, null);
				}
			}
		}
    }

	public void paintComponent(Graphics g)
	{
		if(ecran != null)
		{
			g.drawImage(ecran, 0, 0, ecran.getWidth(), ecran.getHeight(), null);			
		}
	}
}
