package com.carte.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class IOBinaryFileManager extends IOFileManager
{

	@Override
	public boolean save()
	{
		ObjectOutputStream oos;
		boolean saveOk = true;
		try
		{
			oos =
			        new ObjectOutputStream(new BufferedOutputStream(
			                new FileOutputStream(file)));
			
			@SuppressWarnings("rawtypes")
            Iterator ite = elementsToSave.iterator();
			
			while (ite.hasNext())
			{
				oos.writeObject(ite.next());
			}
			
			oos.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Erreur : Fichier pour sauvegarder non trouver : " + file.getPath());
			saveOk = false;
		}
		catch (IOException e)
		{
			System.err.println("Erreur IO pendant l'écriture du fichier : " + file.getPath());
			saveOk = false;
		}

		return saveOk;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public ArrayList load()
	{
		elementsLoaded = new ArrayList();
		ObjectInputStream ois;
		try 
		{
			//On récupère maintenant les données !
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									file)));

				Iterator it = elementsStructure.iterator();
				
				while (it.hasNext())
				{
					elementsLoaded.add(it.next().getClass().cast(ois.readObject()));
				}
				
			ois.close();
		}
		catch (FileNotFoundException e1)
		{
			JOptionPane.showMessageDialog(null, "IO Error ",
					"Le fichier spécifié n'existe pas.",
					JOptionPane.ERROR_MESSAGE);

			System.err.println("Fichier de sauvegarde non trouvé : "
					+ e1.getMessage());
		}
		catch (IOException e)
		{
			JOptionPane
			.showMessageDialog(
					null,
					"IO Error",
					"Un problème est survenu lors de la lecture du fichier !",
					JOptionPane.ERROR_MESSAGE);

			System.err.println("IO erreur pendant le chargement : "
					+ e.getMessage());
		}
        catch (ClassNotFoundException e)
        {
        	JOptionPane
			.showMessageDialog(
					null,
					"Classe introuvable",
					"Un problème est survenu lors de la lecture du fichier !",
					JOptionPane.ERROR_MESSAGE);

			System.err.println("La classe est introuvable lors de la lecture du fichier : "
					+ e.getMessage());
        }  	
		
		return elementsLoaded;
	}
}
