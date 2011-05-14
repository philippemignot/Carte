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
	 * Layout Placement : L'utilisateur doit spécifier où placer chaque élément sur une grille de taille non définie. 
	 * 
	 * Il peut spécifier une taille (largeur et hauteur) que prendra l'élément dans la grille. Il s'agit d'un GridLayout simplifié.
	 */
	public static int Placement = 2;
	
	/**
	 * Layout Grid : Les éléments sont placés à la suite sur une grille de taille semi-fixe.
	 * 
	 * Il faut définir un nombre de colonnes ou de lignes. Les deux ne peuvent être définis simultanéments et seule la dernière définie est utilisée.
	 * Si le nombre de colonnes a été spécifié, les éléments sont ajoutés à la suite les uns des autres lignes par lignes sans dépasser le nombre de colonnes spécifiées.
	 * Si le nombre de lignes a été spécifié, les éléments sont ajoutés à la suite les uns des autres colonnes par colonnes sans dépasser le nombre de lignes spécifiées.
	 */
	public static int Grid = 3;
	
	private int nbrCol = 1;
	private int nbrRow = 0;
	
	public PersoDialogLayout(int layoutId)
	{
		setLayout(layoutId);
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
			this.layoutId = layoutId;
		}
	}

	@Override
    public PersoDialogLayout getPersoLayout()
    {
	    return this;
    }

	@Override
    public void setNumberRows(int nbrRows)
    {
		this.nbrRow = nbrRows;
    }

	@Override
    public void setNumberCols(int nbrCols)
    {
		this.nbrCol = nbrCols;
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
	
	
}
