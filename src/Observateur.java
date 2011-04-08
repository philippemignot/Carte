import java.util.ArrayList;

/* Interface from the Design Pattern Observer
 * L'observateur reçoit les nouvelles données
 *  de l'observable quand celles-ci sont mises à jours.
 */
public interface Observateur 
{
	/**
	 * Mets à jour les observateurs
	 * 
	 * @param sprites
	 * 			La liste de sprite à mettre à jour
	 * @param source
	 * 			La source de la mise à jour
	 */
	public void update(ArrayList<Sprite> sprites, String source);
	
	/**
	 * Mets à jour les observateurs
	 * 
	 * @param bool
	 * 			La liste de boolean à mettre à jour
	 */
	public void update(boolean[] bool);
	
	/**
	 * Mets à jour les observateurs
	 * 
	 * @param integer
	 * 			La liste d'entiers à mettre à jour
	 */
	public void update(int[] integer);
	
	/**
	 * Mets à jour les observateurs
	 * 
	 * @param string
	 * 			Le liste de String à mettre à jour
	 */
	public void update(String[] string);
}
