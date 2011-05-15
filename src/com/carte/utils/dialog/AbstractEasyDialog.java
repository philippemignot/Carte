package com.carte.utils.dialog;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public abstract class AbstractEasyDialog extends AbstractDialog
{
	protected JPanel elementsPanel;
	protected String[] returns;
	
	/**
	 * Crée une fenêtre de dialogue avec les titres demandés, les champs vides
	 * 
	 * @param parent
	 * 				La fenêtre parente
	 * @param title
	 * 				Le titre de la fenêtre
	 * @param modal
	 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée
	 * @param titres
	 * 				Les titres de chaque éléments. Autant d'éléments sont créés qu'il y a de titres.
	 */
	public AbstractEasyDialog(JFrame parent, String title, boolean modal,
	        String[] titres)
	{
		super(parent, title, modal);
		init(titres);
	}

	/**
	 * Permet de placer les éléments dans la fenêtre
	 */
	protected abstract void setElements();
	
	/**
	 * Initialise les titres des éléments
	 * 
	 * @param titres
	 * 			Les différents titres à placer
	 */
	protected void init(String[] titres)
	{
		elementsPanel = new JPanel();
		elementsPanel.setLayout(new GridBagLayout());
		add(elementsPanel, BorderLayout.CENTER);
		this.titles = titres;
		returns = new String[titles.length];
	}

	public abstract void getData();
	
	/**
	 * Modifie le texte d'introduction. Ce texte est affiché comme titre du panneau d'éléments.
	 * 
	 * @param texte
	 *        Le nouveau texte d'introduction
	 */
	public void setTextIntro(String texte)
	{
		textIntro = texte;
		Border b = BorderFactory.createLoweredBevelBorder();
		elementsPanel.setBorder(new TitledBorder(b, textIntro));
		pack();
	}
	
	/**
	 * Affiche la fenêtre
	 * 
	 * @return
	 * 		Les valeurs renvoyées par la fenêtre
	 */
	public boolean showDialog(String[] returns)
	{
		this.returns = returns;
		this.setVisible(true);

		return validated;
	}
	
	protected void setCancelButtonListener()
	{
		cancelButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (int i = 0; i < titles.length; i++)
				{
					returns[i] = "";
				}
				validated = false;
				setVisible(false);
			}
		});		
	}

	protected void setOkButtonListener()
	{
		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getData();
				validated = true;
				setVisible(false);
			}

		});
	}
}
