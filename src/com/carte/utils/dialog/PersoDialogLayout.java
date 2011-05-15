package com.carte.utils.dialog;

public class PersoDialogLayout implements PersoLayoutUtils
{

	/**
	 * Le layout actuellement utilisé.
	 */
	private int layoutId = 1;
	
	/**
	 * Layout horizontal : les éléments sont ajoutés à la suite sur une seule ligne.
	 */
	public static int Horizontal = 0;
	
	/**
	 * Layout vertical : les éléments sont ajoutés à la suite sur une seule colonne.
	 */
	public static int Vertical = 1;
	
	/**
	 * Layout Grid : Les éléments sont placés à la suite sur une grille de taille semi-fixe.
	 * 
	 * Il faut définir un nombre de colonnes ou de lignes. Les deux ne peuvent être définis simultanéments et seule la dernière définie est utilisée.
	 * Si le nombre de colonnes a été spécifié, les éléments sont ajoutés à la suite les uns des autres lignes par lignes sans dépasser le nombre de colonnes spécifiées.
	 * Si le nombre de lignes a été spécifié, les éléments sont ajoutés à la suite les uns des autres colonnes par colonnes sans dépasser le nombre de lignes spécifiées.
	 */
	public static int Grid = 3;
	
	/**
	 * Layout Placement : L'utilisateur doit spécifier où placer chaque élément sur une grille de taille non définie. 
	 * 
	 * Il peut spécifier une taille (largeur et hauteur) que prendra l'élément dans la grille. Il s'agit d'un GridLayout simplifié.
	 */
	public static int Placement = 2;
	
	private int nbrCol = 1;
	private int nbrRow = 0;
	
	private int lastElementNbr = 0;
	
	public PersoDialogLayout(int layoutId)
	{
		setLayout(layoutId);
	}
	
	/**
	 * Utiliser un layout parmis les 4 proposés : Horizontal, Vertical, Placement et Grid.
	 * 
	 * @param layout
	 * 			Prend l'une des 4 valeurs : Horizontal, Vertical, Grid et Placement.
	 */
	public void setLayout(int layoutId)
	{
		if(layoutId >= 0 && layoutId < 4)
		{
			this.layoutId = layoutId;
		}
	}

	public int getLayoutId()
	{
		return this.layoutId;
	}
	
	@Override
    public PersoDialogLayout getPersoLayout()
    {
	    return this;
    }

	@Override
    public void setNumberRows(int nbrRows)
    {
		if(nbrRows >= 0)
		{
			this.nbrRow = nbrRows;
			this.nbrCol = 0;
		}
    }

	@Override
    public void setNumberCols(int nbrCols)
    {
		if(nbrCols >= 0)
		{
			this.nbrCol = nbrCols;
			this.nbrRow = 0;
		}
    }

	@Override
    public int getNulberRows()
    {
	    return nbrRow;
    }

	@Override
    public int getNulberCols()
    {
	    return nbrCol;
    }
	
	/**
	 * Renvoie la prochaine position vide selon le layout et ajoute un élément au compteur du layout.
	 * 
	 * @return
	 * 		Les coordonnées de la prochaine position à occuper.
	 */
	public int[] addNextPosition()
	{
		lastElementNbr ++;
		
		int[] pos = getPos(lastElementNbr);
		
		return pos;
	}
	
	/**
	 * Renvoie la position correspondant à un numéro d'élément.
	 * 
	 * @param nbrElement
	 * 			Un numéro d'élément.
	 * @return
	 * 			La position qu'occuperait un élément avec ce numéro.
	 */
	private int[] getPos(int nbrElement)
	{
		int[] pos = {0, 0};
		
		int fixed = (nbrRow > 0) ? nbrRow : nbrCol;

		int posFixed = (nbrElement) % fixed;
		posFixed = (posFixed == 0) ? fixed -1 : posFixed - 1;
		int posVariable = (nbrElement) / fixed;
		posVariable = (posFixed == fixed -1) ? posVariable -1 : posVariable;

		if(nbrRow != 0)
		{
			pos[0] = posVariable;
			pos[1] = posFixed;
		} else if(nbrCol != 0)
		{
			pos[0] = posFixed;
			pos[1] = posVariable;
		}
		return pos;
	}
	
	/**
	 * Demande de considérer la ligne ou colonne comme remplie pour que le dernier élément ajouté soit le dernier de la ligne ou colonne.
	 * 
	 * @return
	 * 		Le nombre de position qu'il restait à remplir.
	 */
	public int[] fill()
	{
		int[] pos = getPos(lastElementNbr);
		
		int[] nbrToFill = {0, 0};
		if(nbrRow != 0 && pos[1] != (nbrRow - 1))
		{
			nbrToFill[1] = ((nbrRow - 1) - pos[1]);
		} else if(nbrCol != 0 && pos[0] != (nbrCol - 1))
		{
			nbrToFill[0] = ((nbrCol - 1) - pos[0]);
		}
		lastElementNbr += (nbrToFill[0] + nbrToFill[1]);
		
		return nbrToFill;
	}
	
	/**
	 * Permet d'informer le layout que l'on a rajouté un élément. Utilisé avec le layout Placement.
	 */
	public void addElement()
	{
		lastElementNbr++;
	}
}
