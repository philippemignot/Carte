package com.carte.utils;
import java.io.File;

import javax.swing.filechooser.FileFilter;


public class CrtEdFileFilter extends FileFilter
{

private String extension = ".crt", description = "Fichier Editeur de Carte";
	
	public CrtEdFileFilter(String ext, String descrip)
	{
		this.extension = ext;
		this.description = descrip;
	}
	
	public boolean accept(File file){
		return (file.isDirectory() || file.getName().endsWith(this.extension));
	}
	
	public String getDescription(){
		return this.extension + " - " + this.description;
	}	


}
