package com.carte.utils;

import java.io.File;
import java.util.ArrayList;

public abstract class IOFileManager
{
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

	public void selectFile(String dirPath, String fileName, String fileExtension)
	{
		this.dirPath = dirPath;
		this.fileName = fileName;
		this.fileExtension = fileExtension;

		this.file = new File(dirPath + "/" + fileName + "." + fileExtension);
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
}