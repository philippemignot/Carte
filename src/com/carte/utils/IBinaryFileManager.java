package com.carte.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class IBinaryFileManager extends IOFileManager
{
	@SuppressWarnings("rawtypes")
    protected ArrayList elementsLoaded;
	@SuppressWarnings("rawtypes")
	protected ArrayList elementsStructure;
	int indexLoading = 0;
	
	
	private ObjectInputStream ois;
	
	@SuppressWarnings("rawtypes")
    public IBinaryFileManager(File file)
	{
		super(file);
		elementsStructure = new ArrayList();
		elementsLoaded = new ArrayList();
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList load(ArrayList elementsToLoad)
	{
		elementsStructure = elementsToLoad;
		indexLoading = 0;
		return load(indexLoading, elementsStructure.size(), false, false);
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList load(ArrayList elementsToLoad, boolean openStream)
	{
		elementsStructure = elementsToLoad;
		indexLoading = 0;
		return load(indexLoading, elementsStructure.size(), openStream, false);
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList load(int nbToLoad)
	{
		return load(indexLoading, nbToLoad, false, false);
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList load(int nbToLoad, boolean openStream)
	{
		return load(indexLoading, nbToLoad, openStream, false);
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList loadAll(boolean openStream, boolean closeStream)
	{
		return load(0, elementsStructure.size(), openStream, closeStream);
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList loadLasting()
	{
		return load(indexLoading, elementsStructure.size() - indexLoading, false, true);
	}
	
	public boolean closeInputStream()
	{
		boolean closeOk = true;
		try
        {
			indexLoading = 0;
	        ois.close();
        }
        catch (IOException e)
        {
        	JOptionPane.showMessageDialog(null, "IO Error",
			        "Un problème est survenu lors de la fermeture du flux de lecture du fichier !",
			        JOptionPane.ERROR_MESSAGE);

			System.err.println("IO erreur pendant la fermeture du flux de lecture du fichier : "
			        + e.getMessage());
			closeOk = false;
        }
        
        return closeOk;
	}
	
	public boolean openInputStream()
	{
		boolean openOk = true;
			// On récupère maintenant les données !
			try
            {
	            ois =
	                    new ObjectInputStream(new BufferedInputStream(
	                            new FileInputStream(file)));
            }
            catch (FileNotFoundException e)
            {
	            e.printStackTrace();
	            openOk = false;
            }
            catch (IOException e)
            {
	            e.printStackTrace();
	            openOk = false;
            }
		return openOk;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected ArrayList load(int startLoadingIndex, int nbToLoad, boolean startNewStream, boolean readingFinished)
	{
		if (startNewStream)
		{
			this.openInputStream();
		}
		elementsLoaded = new ArrayList();
		
		try {

			for (int i = startLoadingIndex ; i < nbToLoad + startLoadingIndex ; i ++)
			{
				elementsLoaded.add(elementsStructure.get(i).getClass().cast(ois.readObject()));
			}


			if (readingFinished)
			{
				closeInputStream();
			}
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
			JOptionPane.showMessageDialog(null, "IO Error",
			        "Un problème est survenu lors de la lecture du fichier !",
			        JOptionPane.ERROR_MESSAGE);

			System.err.println("IO erreur pendant le chargement : "
			        + e.getMessage());
		}
		catch (ClassNotFoundException e2)
		{
			JOptionPane.showMessageDialog(null, "Classe introuvable",
			        "Un problème est survenu lors de la lecture du fichier !",
			        JOptionPane.ERROR_MESSAGE);

			System.err
			        .println("La classe est introuvable lors de la lecture du fichier : "
			                + e2.getMessage());
		}
		catch (ClassCastException e3)
		{
			JOptionPane.showMessageDialog(null, "Cast impossible",
			        "Un problème est survenu lors de la lecture du fichier !",
			        JOptionPane.ERROR_MESSAGE);

			System.err
			        .println("L'objet lu n'a pas pu êter casté : "
			                + e3.getMessage());
		} 

		return elementsLoaded;
	}

	@SuppressWarnings("rawtypes")
	public void setElementsStructure(ArrayList elementsStructure)
	{
		this.elementsStructure = elementsStructure;
	}
	
	@SuppressWarnings("rawtypes")
    public void addNewElementsStructure(ArrayList elementsType, int[] nbOfEach)
	{
		elementsStructure.clear();
		addElementsStructure(elementsType, nbOfEach);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addElementsStructure(ArrayList elementsType, int[] nbOfEach)
	{
		if (elementsType.size() == nbOfEach.length)
		{
			for (int i = 0 ; i < elementsType.size() ; i ++)
			{
				for (int n = 0 ; n < nbOfEach[i] ; n++)
				{
					elementsStructure.add(elementsType.get(i));
				}
			}
		}
		else
		{
			System.err
			        .println("Erreur dans addElementsStructure : Les listes elementsType et nbOfEach doivent avoir la même taille.");
		}
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList getElementsStructure()
	{
		return this.elementsStructure;
	}
}
