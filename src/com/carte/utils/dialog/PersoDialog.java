package com.carte.utils.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PersoDialog extends AbstractDialog implements PersoLayoutUtils
{
	private String[] returns = {"1"};
	
	/**
	 * Le conteneur des différents éléments.
	 */
	private PanelElement elementsPanel;
		
	/**
	 * Créer une nouvelle boîte de dialog personnalisée.
	 * 
	 * @param parent
	 * 			La fenêtre parente. Peut être null.
	 * @param title
	 * 			Le titre de la boîte de dialogue.
	 * @param modal
	 * 			La modalité de la fenêtre.
	 * @param sizeX
	 * 			Le nombre d'élément * leur largeur spécifiée maximal maximal en horizontal : de 1 à 15.
	 * @param sizeY
	 * 			Le nombre d'éléments * leur hauteur spécifiée maximal en vertical : de 1 à 15.
	 */
	public PersoDialog(JFrame parent, String title, boolean modal, int layoutId)
	{
		super(parent, title, modal);
		
		elementsPanel = new PanelElement(layoutId);
		Border b = BorderFactory.createLoweredBevelBorder();
		elementsPanel.setBorder(b);
		contentPane.add(elementsPanel);
	}
	
	/**
	 * Modifie le texte d'introduction. Ce texte est affiché comme titre du panneau d'éléments.
	 * 
	 * @param texte
	 *        Le nouveau texte d'introduction
	 */
	public void setTextIntro(String texte)
	{
		textIntro = texte;
		elementsPanel.addTitle(texte);
		pack();	
	}
	
	/**
	 * Ajoute un élément à la boite de dialogue.
	 * 
	 * @param el
	 */
	public void addElement(PersoDialogElement el)
	{
		elementsPanel.addElement(el);
	}

	public void addElement(PersoDialogElement el, boolean fill)
	{
		elementsPanel.addElement(el, fill);
	}

	public void addElement(PersoDialogElement el, int posX, int posY, int width, int height)
	{
		elementsPanel.addElement(el, posX, posY, width, height);
	}

	@Override
    protected void setCancelButtonListener()
    {
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				returns[0] = "0";
				setVisible(false);
			}
		});
    }

	@Override
    protected void setOkButtonListener()
    {
		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}

		});
    }
	
	/**
	 * Ajoute une condition pour rendre sélectionnable ou non un élément en fonction de la valeur d'un autre.
	 * 
	 * Un même élément peut dépendre de plusieurs conditions pour être actif. Il ne sera alors actif que si toutes ces conditions sont réunies.
	 * 
	 * @param el
	 * 			L'élément auquel on veut rajouter une condition.
	 * @param elCond
	 * 			L'élément qui contient la condition.
	 * @param cond
	 * 			La valeur de la condition : String - "0"/"1" pour des booleans.
	 */
	public void addCondActive(PersoDialogElement el, PersoDialogElement elCond, String cond)
	{
		el.addCondActive(elCond, cond);
		
	}
	
	/**
	 * Ajoute une condition pour rendre sélectionnable ou non plusieurs éléments en fonction de la valeur d'un autre.
	 * 
	 * Un même élément peut dépendre de plusieurs conditions pour être acitf. Il ne sera alors actif que si toutes ces conditions sont réunies.
	 * 
	 * @param el
	 * 			Les éléments auxquels on veut rajouter une condition.
	 * @param elCond
	 * 			L'élément qui contient la condition.
	 * @param cond
	 * 			La valeur de la condition : String - "0"/"1" pour des booleans.
	 */
	public void addCondActive(ArrayList<PersoDialogElement> el, PersoDialogElement elCond, String cond)
	{
		int nbrEl = el.size();
		for(int i = 0 ; i < nbrEl ; i ++)
		{
			el.get(i).addCondActive(elCond, cond);
		}
	}
	
	/**
	 * Utiliser un layout parmis les 4 proposés : Horizontal, Vertical, Placement et Grid.
	 * 
	 * @param layout
	 * 			Prend l'une des 4 valeurs : Horizontal, Vertical, Placement et Grid.
	 */
	public void setLayout(int layout)
	{
		if(layout >= 0 && layout < 4)
		{
			elementsPanel.setLayout(layout);
		}
	}
	
	/**
	 * Renvoie le layout actuellement utilisé pour la boîte de dialogue personnalisée.
	 * 
	 * @return
	 * 		Le layout actuellement utilisé.
	 */
	public PersoDialogLayout getPersoLayout()
	{
		return elementsPanel.getPersoLayout();
	}

	@Override
    public void setNumberRows(int nbrRows)
    {
		elementsPanel.setNumberRows(nbrRows);
    }

	@Override
    public void setNumberCols(int nbrCols)
    {
		elementsPanel.setNumberCols(nbrCols);
    }

	@Override
    public int getNulberRows()
    {
	    return elementsPanel.getNulberRows();
    }

	@Override
    public int getNulberCols()
    {
	    return elementsPanel.getNulberCols();
    }
	
	public PanelElement createGroup(int layoutId)
	{
		return new PanelElement(layoutId);
	}
	
	/**
	 * Ajoute un groupe à la boite de dialogue.
	 * 
	 * @param el
	 */
	public void addGroup(PanelElement group)
	{
		elementsPanel.addGroup(group);
	}

	public void addGroup(PanelElement group, boolean fill)
	{
		elementsPanel.addGroup(group, fill);
	}

	public void addGroup(PanelElement group, int posX, int posY, int width, int height)
	{
		elementsPanel.addGroup(group, posX, posY, width, height);
	}
}
