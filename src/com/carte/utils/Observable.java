package com.carte.utils;


/** Interface du Design Pattern Observer
 * L'observable informe ses observateurs que les données ont été mise à jour
 * et les lui envoie.
 */
public interface Observable 
{
		/**
		 * Rajoute un observateur
		 * 
		 * @param obs
		 * 			L'observateur à rajouter
		 */
		public void addObservateur(Observateur obs);
		
		/**
		 * Supprime un observateur
		 * 
		 * @param obs
		 * 			L'observateur à enlever de la liste
		 */
		public void rmvObservateur(Observateur obs);
		
		/**
		 *  Met à jours les informations pour les observateurs
		 */
		public void updateObservateur();
}
