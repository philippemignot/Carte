package com.carte.utils.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class PCheckBox extends JCheckBox implements PersoDialogElement
{
	private Hashtable<PersoDialogElement, String> condActive = new Hashtable<PersoDialogElement, String>();
	private boolean active = true;

	public PCheckBox(String title)
    {
		super(title);
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
		
		if ((value.equals("0") && !this.isSelected()) || (value.equals("1") && this.isSelected()))
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
