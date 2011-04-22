package com.carte.utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


@SuppressWarnings({"serial"})
public class InputDialog extends AbstractDialog
{
	private String[] defaults; // Les valeurs par défaut à afficher
	private JTextField[] textFields; // Liste des textfields

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
	 * 				Les titres de chaque TextFields. Autant de TextFieds sont créés qu'il y a de titres.
	 */
	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres)
	{
		super(parent, title, modal, titres);
		setElements();
	}
	
	/**
	 * Crée une fenêtre de dialogue avec les titres demandés, les champs vides. Permet de regrouper les TextFields par sujet.
	 * Pas encore bien pris en compte.
	 * 
	 * @param parent
	 * 				La fenêtre parente
	 * @param title
	 * 				Le titre de la fenêtre
	 * @param modal
	 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée
	 * @param titres
	 * 				Les titres de chaque TextFields. Autant de TextFieds sont créés qu'il y a de titres.
	 * @param groupes
	 * 				Détermine de quel groupe font partie chaque TextField
	 */
	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres, int[] groupes)
	{
		super(parent, title, modal, titres, groupes);
		setElements();
	}

	/**
	 * Rajoute des valeurs par défaut pour les TextFields
	 * 
	 * @param def
	 * 			es valeurs par défaut
	 */
	public void setDefaults(String[] def)
	{
		this.defaults = def;
		for (int i = 0; i < Math.min(titles.length, defaults.length); i++)
		{
			textFields[i].setText(defaults[i]);
		}
	}

	@Override
	public void getData()
	{
		for (int i = 0; i < titles.length; i++)
		{
			returns[i] = textFields[i].getText();
		}
	}

	@Override
    protected void setElements()
    {
		JLabel[] labels = new JLabel[titles.length];
		textFields = new JTextField[titles.length];
		
		for (int i = 0; i < titles.length; i++)
		{
			labels[i] = new JLabel(titles[i]);
			textFields[i] = new JTextField(8);

			this.add(labels[i], new GridBagConstraints(0, i + 1, 1, 1, 1.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 20, 5, 10), 0, 0));
			this.add(textFields[i], new GridBagConstraints(1, i + 1, 1, 1, 1.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 20), 0, 0));
		}
    }
}
