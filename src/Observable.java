/* Interface from the Design Pattern Observer
 * L'observable informe ses observateurs que les données ont été mise à jour
 * et les lui envoie.
 */
public interface Observable 
{
		// Rajoute un observateur
		public void addObservateur(Observateur obs);
		// Supprime un observateur
		public void rmvObservateur(Observateur obs);
		// Met à jours les infos pour les observateurs
		public void updateObservateur();
}
