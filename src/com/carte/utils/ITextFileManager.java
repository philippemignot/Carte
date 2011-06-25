package com.carte.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ITextFileManager extends IOFileManager
{
	@SuppressWarnings("rawtypes")
	protected ArrayList elementsLoaded;
	int indexLoading = 0;

	private FileReader fr;

	@SuppressWarnings("rawtypes")
	public ITextFileManager(File file)
	{
		super(file);
		elementsLoaded = new ArrayList();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList load(int nbLineToLoad)
	{
		return load(nbLineToLoad, false);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList load(int nbLineToLoad, boolean openStream)
	{
		return load(nbLineToLoad, openStream, false);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList loadAll()
	{
		return load(-1, true, true);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList loadLasting()
	{
		return load(-1, false, true);
	}

	public boolean closeInputStream()
	{
		boolean closeOk = true;
		try
		{
			indexLoading = 0;
			fr.close();
		}
		catch (IOException e)
		{
			JOptionPane
			        .showMessageDialog(
			                null,
			                "IO Error",
			                "Un problème est survenu lors de la fermeture du flux de lecture du fichier !",
			                JOptionPane.ERROR_MESSAGE);

			System.err
			        .println("IO erreur pendant la fermeture du flux de lecture du fichier : "
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
			fr = new FileReader(file);
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
	protected ArrayList load(int nbLineToLoad, boolean startNewStream, boolean readingFinished)
	{
		elementsLoaded = new ArrayList();
		
		if (startNewStream)
		{
			this.openInputStream();
		}
		
		try
		{
			int i = 0;
			int nbLine = 0;
			// On remet tous les caractères lus dans un String
			String str = "";
			boolean donneesOk = true;

			// Lecture des paramètres
			while (i != -1 && (nbLine != nbLineToLoad))
			{
				str = "";
				// On lit la ligne
				while (!((i = fr.read()) == 10 || i == -1))
				{
//					System.out.println(i);
//					 10 et 13 correspondent aux fins de ligne
					if (i != 10 && i != 13)
					{
						str += (char) i;
					}
				}
//				System.out.println(str);

				String[] element = str.split(" ");
				ArrayList line = new ArrayList();
				for (int k = 0 ; k <  element.length ; k ++)
				{
					line.add(element[k]);
				}
				elementsLoaded.add(line);
				if (nbLineToLoad > 0)
				{
					nbLine ++;
				}
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

		return elementsLoaded;
	}
}
