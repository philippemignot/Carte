import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.Border;


public class PanSpriteProperties extends JPanel
{
	private JPanel contentPane;
	private Sprite sprite;
	private JLabel labCode;
	private ImagePanel panImage;
	private JLabel niv;
	private int niveau;
	private int nbrNiveaux;
	private JToolBar toolbar = new JToolBar();
	private JButton bAvant;
	private JButton bApres;
	private JButton bSuppr;
	private JButton bCopier;
	private JButton bColler;
	
	public PanSpriteProperties(Sprite sprite, int niv, int nbrNiv)
	{
		this.sprite = sprite;
		niveau = niv;
		nbrNiveaux = nbrNiv;
		Border b = BorderFactory.createLoweredBevelBorder();
		this.setBorder(b);
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());

		this.setLayout(new BorderLayout());
		this.add(contentPane, BorderLayout.CENTER);
		updateContent();
	}
	
	public void addToolbar()
	{
		bAvant = new JButton(new ImageIcon("fleche_haut.png"));
		bAvant.setMargin(new Insets(1, 1, 1, 1));
		bAvant.setActionCommand("avant_"+ String.valueOf(niveau));
		if(niveau == 1)
		{
			bAvant.setEnabled(false);
		}
		
		bApres = new JButton(new ImageIcon("fleche_bas.png"));
		bApres.setMargin(new Insets(1, 1, 1, 1));
		bApres.setActionCommand("apres_"+ String.valueOf(niveau));
		if(niveau == nbrNiveaux)
		{
			bApres.setEnabled(false);
		}

		bSuppr = new JButton(new ImageIcon("suppr.png"));
		bSuppr.setMargin(new Insets(1, 1, 1, 1));
		bSuppr.setActionCommand("suppr_"+ String.valueOf(niveau));

		bCopier = new JButton("C");
		bColler = new JButton("V");
		
		
		toolbar.add(bAvant);
		toolbar.add(bApres);
		toolbar.add(bSuppr);
		toolbar.addSeparator();
		toolbar.add(bCopier);
		toolbar.add(bColler);
		
		this.add(toolbar, BorderLayout.SOUTH);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}

	public void setSprite(Sprite sprite2)
    {
		sprite = new Sprite(sprite2);
		updateContent();
    }
	
	public void updateContent()
	{
		String code = (sprite.getCode().matches("[0-9]{5}") && !sprite.getCode().isEmpty()) ? sprite.getCode() : "00000";
		labCode = new JLabel(code);
		niv = new JLabel(" " +String.valueOf(niveau));
		niv.setBorder(BorderFactory.createLoweredBevelBorder());
		panImage = new ImagePanel(sprite.getImage(), new Dimension(sprite.getLargeur(), sprite.getHauteur()));
		panImage.setBorder(BorderFactory.createRaisedBevelBorder());
		
		contentPane.removeAll();
		contentPane.add(labCode, new GridBagConstraints(0, 0, 1, 1, 1.0,
		        0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		        new Insets(5, 10, 5, 5), 5, 0));
		contentPane.add(panImage, new GridBagConstraints(1, 0, 1, 1, 0.0,
		        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
		        new Insets(5, 5, 5, 10), 0, 0));
		contentPane.add(niv, new GridBagConstraints(0, 1, 2, 1, 1.0,
		        1.0, GridBagConstraints.EAST
		        , GridBagConstraints.NONE,
		        new Insets(5, 15, 0, 0), 2, 0));
	}
	
	public JToolBar getToolbar()
	{
		return toolbar;
	}
	
	public void addToolbarListener(ActionListener listener)
	{
		for(int i = 0 ; i < toolbar.getComponentCount() ; i ++)
		{
			if(toolbar.getComponent(i).getClass().getCanonicalName().equals("javax.swing.JButton"))
			{
				((JButton)toolbar.getComponent(i)).addActionListener(listener);
			}
		}
	}
}
