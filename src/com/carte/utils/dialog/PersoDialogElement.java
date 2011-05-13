package com.carte.utils.dialog;

public interface PersoDialogElement
{	
	/**
	 * Rajoute une condition sur l'activation de cet élément par rapport à l'état d'un autre.
	 * 
	 * @param elCond
	 * 			L'élément dont un état spécifique est nécessaire pour que cet élément (this) soit actif.
	 * @param cond
	 * 			La valeur représentant l'état que doit avoir l'élément de condition pour que cet élément (this) soit actif.
	 * 			Les booleans sont représentés par "0" (false) et "1" (true).
	 */
	public void addCondActive(PersoDialogElement elCond, String cond);
	
	/**
	 * Vérifie si l'élément doit être activé ou désactivé.
	 * 
	 * @return
	 * 			true - L'élément doit être activé.
	 * 			false - L'élément doit être désactivé.
	 */
	public boolean checkStateActive();

	/**
	 * Vérifie si l'élément a l'état demandé.
	 * 
	 * @param value
	 * 			La valeur correspondant a l'état à vérifier.
	 * 			Les booleans sont représentés par "0" (false) et "1" (true).
	 * @return
	 * 			true - L'élément est dans l'état demandé.
	 * 			false - L'élément n'est pas dans l'état demandé ou la valeur soumise ne correspond à rien pour cet élément.
	 */
	public boolean hasValue(String value);

}
