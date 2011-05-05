package com.carte.utils.dialog;

import java.util.Hashtable;

import javax.swing.JComponent;

public class ElementDialog<T>
{
	private T valeur = null;
	//@Todo : implémenter les titres : permet de ajouter un titre devant un élément. A implémenter dans PanelElement.
	private String titre = null;
	private static String[] acceptedTypes = {"JRadioButton", "JCheckBox", "JTextField", "JComboBox", "JTree"};
	private Hashtable<JComponent, String> condActive;
	
	public ElementDialog(T val)
	{
		if(checkType((JComponent) val))
		{
			valeur = val;
		}else
		{
			System.err.println("Type d'élément non accepté : " + val.getClass().getCanonicalName());
		}
		
	}
	
	private boolean checkType(JComponent val)
    {
		boolean valid = false;
		for(int i = 0 ; i < acceptedTypes.length ; i++)
		{
			if(val.getClass().getCanonicalName().endsWith(acceptedTypes[i]))
			{
				valid = true;
			}
		}
		
		return valid;
    }

	public ElementDialog(T val, String titre)
	{
		if(checkType((JComponent) val))
		{
			valeur = val;
		}else
		{
			System.err.println("Type d'élément non accepté : " + val.getClass().getCanonicalName());
		}
		
		this.titre = titre;
	}
	
	public T getValeur()
	{
		return valeur;
	}
	
	public void setValeur(T val)
	{
		if(checkType((JComponent) val))
		{
			valeur = val;
		}else
		{
			System.err.println("Type d'élément non accepté : " + val.getClass().getCanonicalName());
		}
	}
	
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	
	public void addCondActive(JComponent parent, String cond)
	{
		if(checkType((JComponent) parent))
		{
			// Ajoute ou écrase
			if(!condActive.contains(parent))
			{
				condActive.put(parent, cond);
			}else
			{
				condActive.put(parent, cond);
			}
		}else
		{
			System.err.println("Type d'élément non accepté : " + parent.getClass().getCanonicalName());
		}
	}
}
