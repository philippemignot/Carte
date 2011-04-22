package com.carte.utils;

import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class CarteFileChooser extends JFileChooser
{

	public CarteFileChooser(String currentDirectoryPath)
    {
	   	super(currentDirectoryPath);
    }

	@Override
    public void approveSelection() 
    {
		String absolutetPath = super.getSelectedFile().getAbsolutePath();
		String[] fileNameParts = super.getSelectedFile().getName().split("\\.");
		if(fileNameParts.length == 1)
		{
			absolutetPath += ".carte";
		}
    	super.setSelectedFile(new File(absolutetPath));
    	super.approveSelection();
    }
}
