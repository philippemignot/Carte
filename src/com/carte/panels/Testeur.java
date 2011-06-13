package com.carte.panels;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.carte.panels.Editeur.EchapAction;
import com.carte.sprites.Sprite;

public class Testeur
{
	private JFrame fenetre; // La fenetre
	private TestEcran ecran;
	private Carte carte;
	private Personnage persoActif;
	
	private int nbrCol;
	private int nbrLignes;
	private int nbrNiveaux;
		
	public Testeur(Carte carte, Sprite sprite)
	{
		this(carte, new Personnage(sprite));
	}

	public Testeur(Carte carte, Personnage perso)
	{
		this.carte = carte;
		
		int[] tailles = carte.getTailleInfos();
		this.nbrCol = tailles[0];
		this.nbrLignes = tailles[1];
		this.nbrNiveaux = tailles[2];
		
		this.ecran = new TestEcran(carte.getSprites(), nbrCol, nbrLignes, nbrNiveaux );
		
		selectPersoActif(perso);
		
		// Initialisation de la fenêtre
		fenetre = new JFrame();
		fenetre.setTitle("Test de la carte");
//		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		initActions();
		fenetre.add(ecran);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}

	private void selectPersoActif(Personnage perso)
    {
		ecran.setPersoActif(perso);
		persoActif = perso;
		persoActif.placer(nbrCol / 2, nbrLignes / 2);
		persoActif.getSprite().addObservateur(ecran);
    }

	private void initActions()
    {
		DeplHautAction deplHautAction = new DeplHautAction(persoActif);
		DeplBasAction deplBasAction = new DeplBasAction(persoActif);
		DeplDroiteAction deplDroiteAction = new DeplDroiteAction(persoActif);
		DeplGaucheAction deplGaucheAction = new DeplGaucheAction(persoActif);

		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		        .put(KeyStroke.getKeyStroke("UP"), "action depl haut");
		fenetre.getRootPane().getActionMap().put("action depl haut", deplHautAction);
		
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("DOWN"), "action depl bas");
		fenetre.getRootPane().getActionMap().put("action depl bas", deplBasAction);
		
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("RIGHT"), "action depl droite");
		fenetre.getRootPane().getActionMap().put("action depl droite", deplDroiteAction);
		
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("LEFT"), "action depl gauche");
		fenetre.getRootPane().getActionMap().put("action depl gauche", deplGaucheAction);
    }
	
	@SuppressWarnings("serial")
	class DeplHautAction extends AbstractAction
	{
		private Personnage perso;
		
		public DeplHautAction(Personnage perso)
		{
			super();
			this.perso = perso;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (perso.getOrientation() == Personnage.HAUT)
			{
				perso.deplacer();
			} else
			{
				perso.rotation(Personnage.HAUT);
			}
			
			ecran.repaint();
		}
	}
	
	@SuppressWarnings("serial")
	class DeplDroiteAction extends AbstractAction
	{
		private Personnage perso;
		
		public DeplDroiteAction(Personnage perso)
		{
			super();
			this.perso = perso;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (perso.getOrientation() == Personnage.DROITE)
			{
				perso.deplacer();
			} else
			{
				perso.rotation(Personnage.DROITE);
			}
			ecran.repaint();
		}
	}
	
	@SuppressWarnings("serial")
	class DeplBasAction extends AbstractAction
	{
		private Personnage perso;
		
		public DeplBasAction(Personnage perso)
		{
			super();
			this.perso = perso;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (perso.getOrientation() == Personnage.BAS)
			{
				perso.deplacer();
			} else
			{
				perso.rotation(Personnage.BAS);
			}
			ecran.repaint();
		}
	}
	
	@SuppressWarnings("serial")
	class DeplGaucheAction extends AbstractAction
	{
		private Personnage perso;
		
		public DeplGaucheAction(Personnage perso)
		{
			super();
			this.perso = perso;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
			if (perso.getOrientation() == Personnage.GAUCHE)
			{
				perso.deplacer();
			} else
			{
				perso.rotation(Personnage.GAUCHE);
			}
			
			ecran.repaint();
		}
	}
}
