package com.carte.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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

		FileFilter actualFileFilter = super.getFileFilter();
		if(actualFileFilter.getClass().getCanonicalName().endsWith("CrtEdFileFilter") && fileNameParts.length == 1)
		{
			absolutetPath += ((CrtEdFileFilter) actualFileFilter).getExtension();
		}
    	super.setSelectedFile(new File(absolutetPath));
    	super.approveSelection();
    }
}
