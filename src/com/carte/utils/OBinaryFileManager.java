package com.carte.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class OBinaryFileManager extends IOFileManager
{
	@SuppressWarnings("rawtypes")
	protected ArrayList elementsToSave;
	
	@SuppressWarnings("rawtypes")
    public OBinaryFileManager(File file)
	{
		super(file);
		elementsToSave = new ArrayList();
	}
	
	@SuppressWarnings("rawtypes")
    public void setElementsToSave(ArrayList elements)
	{
		this.elementsToSave = elements;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addElementsToSave(ArrayList elementsSupp)
	{
		for (int i = 0; i < elementsSupp.size(); i++)
		{
			this.elementsToSave.add(elementsSupp);
		}
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList getElementsToSave()
	{
		return elementsToSave;
	}

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
			System.err
			        .println("Erreur : Fichier pour sauvegarder non trouver : "
			                + file.getPath());
			saveOk = false;
		}
		catch (IOException e)
		{
			System.err.println("Erreur IO pendant l'écriture du fichier : "
			        + file.getPath());
			saveOk = false;
		}

		return saveOk;
	}
}
