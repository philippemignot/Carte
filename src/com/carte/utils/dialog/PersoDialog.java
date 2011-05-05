package com.carte.utils.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PersoDialog extends AbstractDialog
{
	private String[] returns = {"1"};
	
	private PanelElement elementsPanel;
	
	
	/**
	 * Créer une nouvelle boîte de dialog personnalisée.
	 * 
	 * @param parent
	 * 			La fenêtre parente. Peut être null.
	 * @param title
	 * 			Le titre de la boîte de dialogue.
	 * @param modal
	 * 			La modalité de la fenêtre.
	 * @param sizeX
	 * 			Le nombre d'élément maximal en horizontal.
	 * @param sizeY
	 * 			Le nombre d'éléments maximum en vertical.
	 */
	public PersoDialog(JFrame parent, String title, boolean modal, int sizeX, int sizeY)
	{
		super(parent, title, modal);
		
		elementsPanel = new PanelElement(sizeX, sizeY);
		contentPane.add(elementsPanel);
	}

	@Override
    public void initDialog()
    {
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.pack();
    }

	@Override
    public void setTextOkButton(String texte)
    {
		okButton.setText(texte);
		pack();
    }

	@Override
    public void setTextCancelButton(String texte)
    {
		cancelButton.setText(texte);
		pack();
    }
	
	public void addElement(ElementDialog<?> el)
	{
		elementsPanel.addElement(el, 1, 1);
	}

	public void addElement(ElementDialog<?> el, int width, int height)
	{
		elementsPanel.addElement(el, width, height);
	}

	public void addElement(ElementDialog<?> el, int posX, int posY, int width, int height)
	{
		elementsPanel.addElement(el, posX, posY, width, height);
	}

	@Override
    protected void setCancelButtonListener()
    {
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				returns[0] = "0";
				setVisible(false);
			}
		});
    }

	@Override
    protected void setOkButtonListener()
    {
		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}

		});
    }
}
