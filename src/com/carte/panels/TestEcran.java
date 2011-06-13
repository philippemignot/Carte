package com.carte.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.carte.sprites.Sprite;
import com.carte.utils.Observateur;

public class TestEcran extends JPanel implements Observateur
{
	private BufferedImage ecran;
	private int nbrLignes;
	private int nbrCol;
	private int largeur;
	private int hauteur;
	private int nbrNiveaux;
	
	private Sprite[][][] sprites;
	private Personnage perso;
	
	public TestEcran(Sprite[][][] sprites, int nbrCol, int nbrLignes, int nbrNiveaux)
	{
		this.nbrCol = nbrCol;
		this.nbrLignes = nbrLignes;
		this.nbrNiveaux = nbrNiveaux;
		
		this.sprites = sprites;
		largeur = sprites[0][0][0].getLargeur();
		hauteur = sprites[0][0][0].getHauteur();
		
		this.ecran = new BufferedImage(nbrCol*largeur, nbrLignes*hauteur, BufferedImage.TYPE_INT_ARGB);
//		paintDecor(ecran.createGraphics());
//		paintPerso(ecran.createGraphics());
		this.setPreferredSize(new Dimension(ecran.getWidth(), ecran.getHeight()));
		this.validate();
	}
	
	private void paintDecor(Graphics g)
	{
//		Graphics g = ecran.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ecran.getWidth(), ecran.getHeight());
		
		for (int i = 0 ; i < nbrLignes ; i ++)
		{
			for (int j = 0 ; j < nbrCol ; j ++)
			{
				for (int n = 0 ; n < 4 ; n ++)
				{
					g.drawImage(sprites[i][j][n].getDrawImage(), j * 32, i * 32, largeur, hauteur, null);
				}
			}
		}
	}

	private void paintPerso(Graphics g)
    {
//		Graphics g = ecran.createGraphics();
		int x = perso.getX();
		int y = perso.getY();
		
		// Si un problème de coordonnées, on remet au centre. Ne devrait pas arriver.
		if (x < 0 || x >= nbrCol * largeur)
		{
			x = (nbrCol / 2);
			perso.placer(x, y);
		}

		if (y < 0 || y >= nbrLignes * hauteur)
		{
			y = (nbrLignes * hauteur);
			perso.placer(x, y);
		}
		
	    g.drawImage(perso.getDrawImage(), x * largeur, y * hauteur, largeur, hauteur, null);
	    
	    // Dessine ce qui se trouve au-dessus des personnages.
	    for (int i = 0 ; i < nbrLignes ; i ++)
		{
			for (int j = 0 ; j < nbrCol ; j ++)
			{
				for (int n = 4 ; n < nbrNiveaux ; n ++)
				{
					g.drawImage(sprites[i][j][n].getDrawImage(), j * 32, i * 32, largeur, hauteur, null);
				}
			}
		}
    }

	public void paintComponent(Graphics g)
	{
		paintDecor(g);
		paintPerso(g);
		
		if(ecran != null)
		{
			g.drawImage(ecran, 0, 0, ecran.getWidth(), ecran.getHeight(), null);			
		}
	}

	public void setPersoActif(Personnage perso)
    {
	    this.perso = perso;
	    repaint();
    }

	@Override
    public void update(ArrayList<Sprite> sprites, String source)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(boolean[] bool)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(int[] integer)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update()
    {
//		paintPerso(ecran.createGraphics());
	    this.repaint();
    }

	@Override
    public void update(String[] string)
    {
	    // TODO Auto-generated method stub
	    
    }
}
