package com.carte.panels;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.carte.sprites.PanSpriteProperties;
import com.carte.sprites.Sprite;
import com.carte.utils.Observable;
import com.carte.utils.Observateur;


@SuppressWarnings("serial")
public class PanCaseProperties extends JPanel implements Observateur,
        ActionListener, Observable
{
	private PanSpriteProperties[] panSpriteProp; // Les différents panneau de
												 // propriétés
	private JLabel name = new JLabel(""); // Le titre du panneau
	private int nbrNiveaux; // Le nombre de niveaux max
	private int largeur; // La largeur d'un sprite
	private int hauteur; // La hauteur d'un sprite
	private JPanel contentPane; // Le conteneur des éléments : permet de les
								// mettre en haut

	private ArrayList<Observateur> listeObservateur =
	        new ArrayList<Observateur>(); // liste des observateurs

	/**
	 * Construit un conteneur et gestionnaire de panneau de propriété avec les
	 * éléments nécessaires
	 * 
	 * @param nbrNiv
	 *        Le nombre de niveaux max
	 * @param largeur
	 *        La largeur d'un sprite
	 * @param hauteur
	 *        La hauteur d'un sprite
	 */
	public PanCaseProperties(int nbrNiv, int largeur, int hauteur)
	{
		nbrNiveaux = nbrNiv;
		this.hauteur = hauteur;
		this.largeur = largeur;

		Border b = BorderFactory.createRaisedBevelBorder();
		this.setBorder(b);

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(contentPane, BorderLayout.NORTH);

		// Initialisation de la taille pour la création de la fenêtre
		// Les valeurs sont à changer en fonction des propriétés... A voir
		this.setMinimumSize(new Dimension(largeur + 150,
		        (nbrNiveaux * (hauteur + 100))));
		this.setPreferredSize(new Dimension(largeur + 150,
		        (nbrNiveaux * (hauteur + 100))));
	}

	@Override
	public void update(ArrayList<Sprite> sprites, String source)
	{
		// Met à jour le titre
		name.setText(source);
		contentPane.removeAll();
		contentPane.add(name, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
		                15, 5, 15, 5), 0, 0));

		// On crée un nouveau tableau de propriétés à partir des nouveaux
		// sprites reçus
		panSpriteProp = new PanSpriteProperties[sprites.size()];
		for (int i = 0; i < sprites.size(); i++)
		{
			if(name.getText().toLowerCase().equals("selection"))
			{
				panSpriteProp[i] =
					new PanSpriteProperties(new Sprite(sprites.get(i)), i + 1,
							nbrNiveaux);				
			}else if(name.getText().toLowerCase().equals("carte"))
			{
				panSpriteProp[i] =
					new PanSpriteProperties(sprites.get(i), i + 1,
							nbrNiveaux);				
			}

			setPropLayoutPosition(panSpriteProp[i], i + 2); // +1 pour le titre;
															// +1 car i commence
															// à 0
			if (name.getText().toLowerCase().equals("carte"))
			{
				panSpriteProp[i].addToolbar();
				panSpriteProp[i].addToolbarListener(this);
			}
			panSpriteProp[i].getImagePanel().addObservateur(panSpriteProp[i]);
			panSpriteProp[i].revalidate();
		}
		if (name.getText().toLowerCase().equals("carte"))
		{
			this.setMinimumSize(new Dimension(largeur + 150,
			        (sprites.size() * (hauteur + 100))));
			this.setPreferredSize(new Dimension(largeur + 150,
			        (sprites.size() * (hauteur + 100))));
		}
		else
		{
			this.setMinimumSize(new Dimension(largeur + 150,
			        (sprites.size() * (hauteur + 60))));
			this.setPreferredSize(new Dimension(largeur + 150,
			        (sprites.size() * (hauteur + 60))));
		}
		// ((JPanel)
		// this.getParent().getParent().getParent().getParent()).revalidate();
		((JPanel) this.getParent().getParent().getParent().getParent())
		        .repaint();
	}

	@Override
	public void update(boolean[] bool)
	{

	}

	@Override
	public void update(int[] integer)
	{
	}

	@Override
	public void update(String[] string)
	{
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		// On récupère les différentes actions sur la toolbar
		if (e.getSource().getClass().getCanonicalName()
		        .equals("javax.swing.JButton"))
		{
			if (e.getActionCommand().matches("[a-z]+[_][1-9]+"))
			{
				System.out.println(e.getActionCommand());
				String[] infosSource = e.getActionCommand().split("_");
				int pos = new Integer(infosSource[1]);
				if (infosSource[0].equalsIgnoreCase("avant"))
				{
					modifiesPropPosition(getProp(pos), pos - 1);
				}
				else if (infosSource[0].equalsIgnoreCase("apres"))
				{
					modifiesPropPosition(getProp(pos), pos + 1);
				}
				else if (infosSource[0].equalsIgnoreCase("suppr"))
				{
					getProp(pos).supprSprite();
					updateObservateur();
				}
			}
		}
	}

	/**
	 * Positionne un panneau de propriétés à l'endroit voulu dans le layout.
	 * Attention, si il y a déjà un élément à cette place il faut l'enlever
	 * avant.
	 * 
	 * @param psp
	 *        Le panneau de propriétés à rajouter
	 * @param pos
	 *        La position où le rajouter
	 */
	private void setPropLayoutPosition(PanSpriteProperties psp, int pos)
	{
		contentPane.add(psp, new GridBagConstraints(0, pos, 1, 1, 1.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
		                5, 10, 5, 10), 0, 0));
	}

	/**
	 * Enlève un panneau de propriétés du layout
	 * 
	 * @param psp
	 *        Le panneau de propriétés à enlever
	 */
	private void rmPropLayoutPosition(PanSpriteProperties psp)
	{
		contentPane.remove(psp);
	}

	/**
	 * Modifie la position d'un panneau de propriétés.
	 * 
	 * @param psp
	 * 			Le panneau de propriétés à bouger.
	 * @param pos
	 * 			La nouvelle position désirée
	 */
	private void modifiesPropPosition(PanSpriteProperties psp, int pos)
	{
		int posActuel = psp.getNiveau();
		PanSpriteProperties psp2 = getProp(pos); // Le composant duquel on prend
												 // la place

		// On modifie le layout
		rmPropLayoutPosition(psp);
		rmPropLayoutPosition(psp2);

		setPropLayoutPosition(psp, pos + 1); // 1 est le label de titre
		setPropLayoutPosition(psp2, posActuel + 1);

		// On met à jour la liste des panneaux de propriété
		panSpriteProp[posActuel - 1] = psp2;
		panSpriteProp[pos - 1] = psp;

		// On met à jour les infos de niveau du panneau
		psp.setNiveau(pos);
		psp2.setNiveau(posActuel);

		revalidate();
		repaint();
		updateObservateur();
	}

	/**
	 * Récupère un panneau de propriétés par rapport à sa position
	 * 
	 * @param pos
	 * 			La position du panneau à récupérer
	 * @return
	 * 			Le panneau
	 */
	private PanSpriteProperties getProp(int pos)
	{
		return panSpriteProp[pos - 1]; // commence à 0
	}

	@Override
	public void addObservateur(Observateur obs)
	{
		listeObservateur.add(obs);

	}

	@Override
	public void rmvObservateur(Observateur obs)
	{
		listeObservateur.remove(obs);
	}

	@Override
	public void updateObservateur()
	{
		ArrayList<Sprite> listeSprites = new ArrayList<Sprite>();
		for (int i = 0; i < panSpriteProp.length; i++)
		{
			listeSprites.add(panSpriteProp[i].getSprite());
		}
		for (Observateur obs : listeObservateur)
		{
			obs.update(listeSprites, "PanCaseProperties");	
		}
	}

	@Override
    public void update()
    {
	    // TODO Auto-generated method stub
	    
    }

}
