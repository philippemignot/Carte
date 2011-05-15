package com.carte.utils.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PanelElement extends JPanel implements PersoLayoutUtils
{
	/**
	 * Le layout actuellement utilisé.
	 */
	private PersoDialogLayout layout;
	
	private Border b = BorderFactory.createLoweredBevelBorder();
	
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
		this.setBorder(b);
	}
	
	public void addElement(PersoDialogElement el)
	{
		addElement(el, false);
	}

	public void addElement(PersoDialogElement el, boolean fill)
	{
		int[] nextPos = addToLayout();
		int[] size = {1, 1};
		if(fill)
		{
			size = fillLayout();
		}

		showElement((Component) el, nextPos[0], nextPos[1], size[0], size[1]);
	}

	public void addElement(PersoDialogElement el, int posX, int posY, int width, int height)
	{	
		addPlacementLayout((Component) el, posX, posY, width, height);
	}
	
	private void showElement(Component comp, int posX, int posY, int width, int height)
	{
		add(comp, new GridBagConstraints(posX, posY, width,
				height, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));
		validate();
		repaint();
	}
	
	private int[] addToLayout()
	{
		int[] nextPos = layout.addNextPosition();
		
		return nextPos;
	}
	
	private int[] fillLayout()
	{
		int[] size = {1, 1};
		
		int[] filled = layout.fill();
		size[0] += filled[0];
		size[1] += filled[1];
		
		return size;
	}
	
	public void addPlacementLayout(Component comp, int posX, int posY, int width, int height)
	{
		
		posX = (posX >= 0 ) ? posX : 0;
		posY = (posY >= 0 ) ? posY : 0;
		
		width = (width >= 1) ? width : 1;
		height = (height >= 1) ? height : 1;
				
		layout.addElement();
		
		showElement(comp, posX, posY, width, height);
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

	public void addGroup(PanelElement group)
    {
	    addGroup(group, false);
    }

	public void addGroup(PanelElement group, boolean fill)
    {
		int[] nextPos = addToLayout();
		int[] size = {1, 1};
		if(fill)
		{
			size = fillLayout();
		}

		showElement(group, nextPos[0], nextPos[1], size[0], size[1]);
    }

	public void addGroup(PanelElement group, int posX, int posY, int width,
            int height)
    {
		addPlacementLayout(group, posX, posY, width, height);
    }

	public void addTitle(String texte)
    {
		this.setBorder(new TitledBorder(b, texte));
    }
}
