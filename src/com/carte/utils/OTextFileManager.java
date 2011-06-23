package com.carte.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class OTextFileManager extends IOFileManager
{
	@SuppressWarnings("rawtypes")
	protected ArrayList<ArrayList> elementsToSave;

	@SuppressWarnings("rawtypes")
	public OTextFileManager(File file)
	{
		super(file);
		elementsToSave = new ArrayList<ArrayList>();
	}

	@SuppressWarnings("rawtypes")
	public void setElementsToSave(ArrayList<ArrayList> elements)
	{
		this.elementsToSave = elements;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addElementsToSave(ArrayList elementsSupp)
	{
		this.elementsToSave.add(new ArrayList(elementsSupp));
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addElementsToSave(ArrayList elementsSupp, boolean skipline)
	{
		this.elementsToSave.add(new ArrayList(elementsSupp));
		addSkipLineToSave();
	}

	@SuppressWarnings({"rawtypes"})
	public void addSkipLineToSave()
	{
		this.elementsToSave.add(new ArrayList());
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getElementsToSave()
	{
		return elementsToSave;
	}

	@SuppressWarnings("rawtypes")
	public boolean save()
	{
		boolean saveOk = true;
		PrintWriter pw;
		try
		{
			FileWriter fw = new FileWriter(file);
			pw = new PrintWriter(fw, true);

			Iterator ite = elementsToSave.iterator();

			while (ite.hasNext())
			{
				ArrayList line = (ArrayList) ite.next();
				for (int j = 0; j < line.size(); j++)
				{
					pw.print(line.get(j));
					if (j != line.size() - 1)
					{
						pw.print(" ");
					}
				}
				pw.println();
			}

			pw.close();
			fw.close();
		}
		catch (IOException e)
		{
			JOptionPane
			        .showMessageDialog(
			                null,
			                "IO Error ",
			                "Un problème est survenu lors de l'écriture dans le fichier !",
			                JOptionPane.ERROR_MESSAGE);

			System.err.println("IO erreur pendant l'enregistrement : "
			        + e.getMessage());
			saveOk = false;
		}

		return saveOk;
	}
}
