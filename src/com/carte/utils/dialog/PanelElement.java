package com.carte.utils.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelElement extends JPanel
{
	private int sizeX;
	private int sizeY;
	private Component[][] positions;
	
	/**
	 * Construit un panel contenant des éléments de dialogues de la taille sizeX*sizeY.
	 * 
	 * @param sizeX
	 * @param sizeY
	 */
	public PanelElement(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		positions = new Component[sizeY*2][sizeX*2];
		
		setLayout(new GridBagLayout());

	}
	
	public void addElement(PersoDialogElement el)
	{
		addElement(el, 1, 1);
	}

	public void addElement(PersoDialogElement el, int width, int height)
	{
		int[] nextPos = getNextPos(width, height);
		System.out.println(nextPos[0] + " " + nextPos[1]);
		addElement(el, nextPos[0], nextPos[1], width, height);
	}
	
	public int[] getNextPos(int width, int height)
    {
		int i = 0;
		int j = 0;
		int[] nextPos = {sizeX - 1, sizeY - 1};
		boolean posFounded = false;
	    while(!(i >= sizeY || posFounded))
	    {
	    	while(!(j >= sizeX || posFounded))
	    	{
	    		if(positions[i][j] == null)
	    		{
	    			posFounded = true;
	    			nextPos[0] = j;
	    			nextPos[1] = i;
	    		}
	    		j++;
	    	}
	    	i++;
	    }
	    
	    return nextPos;
    }

	public void addElement(PersoDialogElement el, int posX, int posY, int width, int height)
	{
		
		posX = (posX >= 0 || posX < sizeX) ? posX : getNextPos(width, height)[0];
		posY = (posY>= 0 || posY < sizeY) ? posY : getNextPos(width, height)[1];
		
		width = (!((width + posX) >= sizeX || width < 1)) ? width : 1;
		height = (!((height + posY) >= sizeY || height < 1)) ? height : 1;
		
		cleanArea(posX, posY, width, height);
		
		add((Component) el, new GridBagConstraints(posX, posY, width,
					height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			        new Insets(5, 5, 5, 5), 0, 0));
		
		for(int i = 0 ; i < height ; i ++)
		{
			for(int j = 0 ; j < width ; j ++)
			{
				positions[i][j] = (Component) el;
			}
		}
		
		validate();
		repaint();
	}

	private void cleanArea(int posX, int posY, int width, int height)
    {
		for(int i = posY ; i < height ; i ++)
		{
			for(int j = posX ; j < width ; j ++)
			{
				if(positions[i][j] != null)
				{
					remove(positions[i][j]);
				}
			}
		}
    }
	
	public int getSiseX()
	{
		return sizeX;
	}
	
	public int getSiseY()
	{
		return sizeY;
	}
}
