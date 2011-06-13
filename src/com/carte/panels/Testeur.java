package com.carte.panels;

import javax.swing.JFrame;

import com.carte.sprites.Sprite;

public class Testeur
{
	private JFrame fenetre; // La fenetre
	private TestEcran ecran;
	private Carte carte;
	private Personnage perso;
	private int[]	posPerso;
	
	public Testeur(Carte carte, Sprite sprite)
	{
		this(carte, new Personnage(sprite));
	}

	public Testeur(Carte carte, Personnage perso)
	{
		this.carte = carte;
		int[] tailles = carte.getTailleInfos();
		this.ecran = new TestEcran(carte.getSprites(), tailles[0], tailles[1], tailles[2]);
		
		// Initialisation de la fenêtre
		fenetre = new JFrame();
		fenetre.setTitle("Éditeur de carte");
//		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		fenetre.add(ecran);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}
}
