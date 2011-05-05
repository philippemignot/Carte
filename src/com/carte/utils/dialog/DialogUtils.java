package com.carte.utils.dialog;

public interface DialogUtils
{
	/**
	 * Initialise ce qui a rapport à la taille et la position de la fenêtre
	 */
	public void initDialog();

	/**
	 * Crée les boutons et leurs actions
	 */
	public void initButtons();

	/**
	 * Affiche la fenêtre
	 * 
	 * @return
	 * 		Les valeurs renvoyées par la fenêtre
	 */
	public String[] showDialog();

	/**
	 * Modifie le texte du bouton de validation. Ce bouton renvoie les valeurs
	 * rentrées dans les champs.
	 * 
	 * @param texte
	 *        Le nouveau texte du bouton
	 */
	public void setTextOkButton(String texte);
	
	/**
	 * Modifie le texte du bouton d'annulation. Ce bouton renvoie des champs
	 * vides.
	 * 
	 * @param texte
	 *        Le nouveau texte du bouton
	 */
	public void setTextCancelButton(String texte);

}
