package com.carte.utils.dialog;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;




@SuppressWarnings("serial")
public class RadioDialog extends AbstractEasyDialog
{
	private JRadioButton[] radioButtons; // Les différents radio boutons
	private boolean[] defaults;

	/**
	 * Créé une fenêtre de dialogue contenant des radio boutons avec les titres demandés, les champs vides.
	 * 
	 * @param parent
	 * 				La fenerte parente
	 * @param title
	 * 				Le titre de la fenêtre
	 * @param modal
	 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée
	 * @param titres
	 * 				Les titres de chaque radio bouton. Autant de radio boutons sont créés qu'il y a de titres.
	 */
	public RadioDialog(JFrame parent, String title, boolean modal,
			String[] titres)
	{
		super(parent, title, modal, titres);
		setElements();
	}
	
	/**
	 * Crée une fenêtre de dialogue avec les titres demandÃ©s. Permet de regrouper les radio boutons par sujet.
	 * Pas encore bien pris en compte.
	 * 
	 * @param parent
	 * 				La fenêtre parente
	 * @param title
	 * 				Le titre de la fenêtre
	 * @param modal
	 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée.
	 * @param titres
	 * 				Les titres de chaque radio boutons. Autant de radio boutons sont créés qu'il y a de titres.
	 * @param groupes
	 * 				Détermine de quel groupe font partie chaque radio bouton.
	 */
	public RadioDialog(JFrame parent, String title, boolean modal,
			String[] titres, int[] groupes)
	{
		super(parent, title, modal, titres);
		setElements();
	}
	
		@Override
	public void getData()
	{
		for (int i = 0; i < titles.length; i++)
		{
			if(radioButtons[i].isSelected())
			{
				returns[i] = radioButtons[i].getText();
			}else
			{
				returns[i] = "-1";
			}
		}
	}

	@Override
    protected void setElements()
    {
		boolean groupesSet = false;
		ButtonGroup[] bg = new ButtonGroup[1];
		if(groupes.length > 0)
		{
			groupesSet = true;
			bg = new ButtonGroup[getGroupesNumber()];
			for(int i = 0 ; i < bg.length ; i ++)
			{
				bg[i] = new ButtonGroup();
			}
		}
		radioButtons = new JRadioButton[titles.length];
		for (int i = 0; i < titles.length; i++)
		{
			radioButtons[i] = new JRadioButton(titles[i]);
			if(groupesSet)
			{
				bg[groupes[i] - 1].add(radioButtons[i]);
				
			}else
			{
				bg[0] = new ButtonGroup();
				bg[0].add(radioButtons[i]);
			}
			elementsPanel.add(radioButtons[i], new GridBagConstraints(0, i + 1, 2, 1, 1.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(3, 3, 3, 3), 0, 0));
		}
		if(titles.length > 0)
		{
			radioButtons[0].setSelected(true);
		}
    }
	
	/**
	 * Renvoie le nombre total de groupe
	 * 
	 * @return
	 * 		Le nombre total de groupe
	 */
	private int getGroupesNumber()
	{
		int nbrGrp = 0;
		for(int i = 0 ; i < groupes.length ; i++)
		{
			if(groupes[i] > nbrGrp)
			{
				nbrGrp = groupes[i];
			}
		}

		return nbrGrp;
	}
	
	/**
	 * Mets en place les groupes. Pas encore bien gÃ©rÃ©.
	 * @param groupes
	 */
	public void setGroupes(int[] groupes)
	{
		this.groupes = groupes;
	}
	
	/**
	 * Rajoute des valeurs par défaut pour les radio boutons.
	 * 
	 * @param def
	 * 			Les valeurs par défaut
	 */
	public void setDefaults(boolean[] def)
	{
		this.defaults = def;
		for (int i = 0; i < Math.min(titles.length, defaults.length); i++)
		{
			radioButtons[i].setSelected(defaults[i]);
		}
	}
}
