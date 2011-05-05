package com.carte.utils.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class PersoDialog extends JDialog implements DialogUtils
{
	private Container contentPane;
	
	private JPanel buttonsPanel;
	private JButton okButton = new JButton("ok");
	private JButton cancelButton = new JButton("cancel");
	private String[] returns = {"1"};
	
	private JPanel elementsPanel;
	private int sizeX;
	private int sizeY;
	private Component[][] positions;
	
	public PersoDialog(JFrame parent, String title, boolean modal, int sizeX, int sizeY)
	{
		super(parent, title, modal);
		this.contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		initDialog();
		initButtons();
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		positions = new Component[sizeY][sizeX];
		
		elementsPanel = new JPanel();
		elementsPanel.setLayout(new GridBagLayout());
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
    public void initButtons()
    {
		buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		this.getRootPane()
		.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"action entree");
		this.getRootPane().getActionMap()
		.put("action entree", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				okButton.doClick();
			}
		});
		this.getRootPane()
		.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
		"action echap");
		this.getRootPane().getActionMap()
		.put("action echap", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				cancelButton.doClick();
			}
		});

		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}

		});

		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				returns[0] = "0";
				setVisible(false);
			}
		});

		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
    }

	@Override
    public String[] showDialog()
    {
		this.setVisible(true);

		return returns;
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
		int[] nextPos = getNextPos();
		System.out.println(nextPos[0] + " " + nextPos[1]);
		addElement(el, nextPos[0], nextPos[1], 1, 1);
	}
	
	private int[] getNextPos()
    {
		int i = 0;
		int j = 0;
		int[] nextPos = {sizeX - 1, sizeY - 1};
		boolean posFounded = false;
	    while(!(i >= sizeY || posFounded))
	    {
	    	while(!(j >= sizeX || posFounded))
	    	{
	    		if(positions[i][j] == null)
	    		{
	    			posFounded = true;
	    			nextPos[0] = j;
	    			nextPos[1] = i;
	    		}
	    		j++;
	    	}
	    	i++;
	    }
	    
	    return nextPos;
    }

	public void addElement(ElementDialog<?> el, int posX, int posY, int width, int height)
	{
		posX = (posX >= 0 || posX < sizeX) ? posX : getNextPos()[0];
		posY = (posY>= 0 || posY < sizeY) ? posY : getNextPos()[1];
		
		width = (!((width + posX) > sizeX || width < 1)) ? width : 1;
		height = (!((height + posY) > sizeY || height < 1)) ? height : 1;
		
		cleanArea(posX, posY, width, height);
		
		elementsPanel.add((Component) el.getValeur(), new GridBagConstraints(posX, posY, width,
					height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			        new Insets(5, 5, 5, 5), 0, 0));
		
		for(int i = 0 ; i < height ; i ++)
		{
			for(int j = 0 ; j < width ; j ++)
			{
				positions[i][j] = (Component) el.getValeur();
			}
		}
		
		validate();
		repaint();
	}

	private void cleanArea(int posX, int posY, int width, int height)
    {
		for(int i = posY ; i < height ; i ++)
		{
			for(int j = posX ; j < width ; j ++)
			{
				if(positions[i][j] != null)
				{
					elementsPanel.remove(positions[i][j]);
				}
			}
		}
    }
}
