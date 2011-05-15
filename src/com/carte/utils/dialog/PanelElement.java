package com.carte.utils.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelElement extends JPanel implements PersoLayoutUtils
{
	/**
	 * Le layout actuellement utilisé.
	 */
	private PersoDialogLayout layout;
	
	/**
	 * Construit un panel contenant des éléments de dialogues de la taille sizeX*sizeY.
	 * 
	 * @param layoutId
	 * 				L'id du layout utilisé pour ce panneau.
	 */
	public PanelElement(int layoutId)
	{
		setLayout(new GridBagLayout());
		this.layout = new PersoDialogLayout(layoutId);
	}
	
	public void addElement(PersoDialogElement el)
	{
		addElement(el, false);
	}

	public void addElement(PersoDialogElement el, boolean fill)
	{
		int[] nextPos = layout.addNextPosition();
		int[] size = {1, 1};
		if(fill)
		{
			int[] filled = layout.fill();
			size[0] += filled[0];
			size[1] += filled[1];
		}

		showElement(el, nextPos[0], nextPos[1], size[0], size[1]);
	}

	public void addElement(PersoDialogElement el, int posX, int posY, int width, int height)
	{		
		posX = (posX >= 0 ) ? posX : 0;
		posY = (posY >= 0 ) ? posY : 0;
		
		width = (width >= 1) ? width : 1;
		height = (height >= 1) ? height : 1;
				
		layout.addElement();
		
		showElement(el, posX, posY, width, height);
	}
	
	private void showElement(PersoDialogElement el, int posX, int posY, int width, int height)
	{
		add((Component) el, new GridBagConstraints(posX, posY, width,
				height, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		validate();
		repaint();
	}

	/**
	 * Utiliser un layout parmis les 4 proposés : Horizontal, Vertical, Placement et Grid.
	 * 
	 * @param layout
	 * 			Prend l'une des 4 valeurs : Horizontal, Vertical, Placement et Grid.
	 */
	public void setLayout(int layoutId)
	{
		if(layoutId >= 0 && layoutId < 4)
		{
			this.layout = new PersoDialogLayout(layoutId);
		}
	}
	
	public PersoDialogLayout getPersoLayout()
	{
		return this.layout;
	}

	@Override
    public void setNumberRows(int nbrRows)
    {
		layout.setNumberRows(nbrRows);
    }

	@Override
    public void setNumberCols(int nbrCols)
    {
		layout.setNumberCols(nbrCols);
    }

	@Override
    public int getNulberRows()
    {
	    return layout.getNulberRows();
    }

	@Override
    public int getNulberCols()
    {
	    return layout.getNulberCols();
    }
}
