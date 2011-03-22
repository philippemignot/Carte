import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class PanSpriteProperties extends JPanel
{
	private Sprite sprite;
	private JLabel labCode;
	private ImagePanel panImage;
	private JLabel niv;
	private int niveau;
	
	public PanSpriteProperties(Sprite sprite, int niv)
	{
		this.sprite = sprite;
		niveau = niv;
		this.setLayout(new GridBagLayout());
		Border b = BorderFactory.createRaisedBevelBorder();
		this.setBorder(b);
		updateContent();
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
		niv = new JLabel(String.valueOf(niveau));
		panImage = new ImagePanel(sprite.getImage(), new Dimension(sprite.getLargeur(), sprite.getHauteur()));
		
		this.removeAll();
		this.add(labCode, new GridBagConstraints(0, 0, 1, 1, 0.0,
		        0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		        new Insets(5, 5, 5, 5), 0, 0));
		this.add(panImage, new GridBagConstraints(1, 0, 1, 1, 0.0,
		        0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		        new Insets(5, 5, 5, 5), 0, 0));
		this.add(niv, new GridBagConstraints(0, 1, 1, 1, 0.0,
		        0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		        new Insets(5, 5, 5, 5), 0, 0));
		System.out.println();
	}
}
