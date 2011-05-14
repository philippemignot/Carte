package com.carte.utils.dialog;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;

public class PComboBox extends JComboBox implements PersoDialogElement
{

	private Hashtable<PersoDialogElement, String> condActive = new Hashtable<PersoDialogElement, String>();
	private boolean active = true;

	public PComboBox(String[] values)
    {
		super(values);
		checkStateActive();
    }

	@Override
    public void addCondActive(PersoDialogElement parent, String cond)
    {
		// Ajoute ou écrase
		if(!condActive.contains(parent))
		{
			condActive.put(parent, cond);
		}else
		{
			condActive.put(parent, cond);
		}
		((AbstractButton) parent).addActionListener(this);
		checkStateActive();
    }

	@Override
    public boolean checkStateActive()
    {
		active = true;
		if(!condActive.isEmpty())
		{
			 Enumeration<String> eValues = condActive.elements();
			 Enumeration<PersoDialogElement> eKeys   = condActive.keys();
			 
             while(eKeys.hasMoreElements() && eValues.hasMoreElements() && active)
             {
            	 if(!eKeys.nextElement().hasValue(eValues.nextElement()))
            	 {
            		 active = false;
            	 }
             }
		}
		if(!active)
		{
			this.setEnabled(false);
		}else
		{
			this.setEnabled(true);
		}
	    return active;
    }

	@Override
    public boolean hasValue(String value)
    {
		boolean hasValue = false;
		
		if (((String) this.getSelectedItem()).equals(value))
		{
			hasValue = true;
		}
		
	    return hasValue;
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		checkStateActive();
    }

}
