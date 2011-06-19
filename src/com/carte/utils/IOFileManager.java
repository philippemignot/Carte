package com.carte.utils;

import java.io.File;
import java.util.ArrayList;

public abstract class IOFileManager
{
	@SuppressWarnings("rawtypes")
	protected ArrayList elementsToSave;
	@SuppressWarnings("rawtypes")
    protected ArrayList elementsLoaded;
	@SuppressWarnings("rawtypes")
	protected ArrayList elementsStructure;
	protected String dirPath;
	protected String fileName;
	protected String fileExtension;
	protected File file;

	public IOFileManager(File file)
	{
		this.file = file;

		dirPath = file.getParent();
		fileName = file.getName().split("\\.", 1)[0];
		fileExtension = file.getName().split("\\.", 1)[1];
	}

	public IOFileManager()
	{
		this(new File(""));
	}

	public abstract boolean save();

	public abstract ArrayList load();

	public void selectFile(String dirPath, String fileName, String fileExtension)
	{
		this.dirPath = dirPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;

		this.file = new File(dirPath + "/" + fileName + "." + fileExtension);
	}

	@SuppressWarnings("rawtypes")
    public void setGivenElements(ArrayList elements)
	{
		this.elementsToSave = elements;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addGivenElements(ArrayList elementsSupp)
	{
		for (int i = 0; i < elementsSupp.size(); i++)
		{
			this.elementsToSave.add(elementsSupp);
		}
	}
	
	public String getDirPath()
	{
		return this.dirPath;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public String getFileExtension()
	{
		return this.fileExtension;
	}
	
	public File getFile()
	{
		return file;
	}
	
	@SuppressWarnings("rawtypes")
    public ArrayList getGivenElements()
	{
		return elementsToSave;
	}
	
	public void setElementsStructure(ArrayList elementsStructure)
	{
		this.elementsStructure = elementsStructure;
	}
}
