import java.awt.Image;
import java.util.ArrayList;

/* Interface from the Design Pattern Observer
 * L'observateur reçoit les nouvelles données
 *  de l'observable quand celles-ci sont mises à jours.
 */
public interface Observateur 
{
	public void update(ArrayList<Sprite> sprites);
	public void update(boolean[] bool);
	public void update(int[] integer);
	public void update(String[] string);
}
