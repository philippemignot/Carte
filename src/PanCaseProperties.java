import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;


@SuppressWarnings("serial")
public class PanCaseProperties extends JPanel implements Observateur
{
	private ArrayList<PanSpriteProperties> panSpriteProp = new ArrayList<PanSpriteProperties>();
	private JLabel name = new JLabel("");
	private int nbrNiveaux;
	private int largeur;
	private int hauteur;
	private JPanel contentPane;
	
	public PanCaseProperties(int nbrNiv, int largeur, int hauteur)
	{
		nbrNiveaux = nbrNiv;
		this.hauteur = hauteur;
		this.largeur = largeur;
		Border b = BorderFactory.createRaisedBevelBorder();
		this.setBorder(b);
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(contentPane, BorderLayout.NORTH);
		this.setMinimumSize(new Dimension(largeur + 150, (nbrNiveaux * (hauteur + 100))));
		this.setPreferredSize(new Dimension(largeur + 150, (nbrNiveaux * (hauteur + 100))));
	}

	@Override
    public void update(ArrayList<Sprite> sprites)
    {	
		panSpriteProp.clear();
		contentPane.removeAll();
		contentPane.add(name,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
		                15, 5, 15, 5), 0, 0));
	    for(int i = 0 ; i < sprites.size() ; i++)
	    {
	    	panSpriteProp.add(new PanSpriteProperties(new Sprite(sprites.get(i)), i+1));
	    	
	    	contentPane.add(panSpriteProp.get(i),  new GridBagConstraints(0, i+2, 1, 1, 1.0, 0.0,
			        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
			                5, 10, 5, 10), 0, 0));
		    panSpriteProp.get(i).revalidate();	    	
	    }
	    this.setMinimumSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 100))));
	    this.setPreferredSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 100))));
//	    ((JPanel) this.getParent().getParent().getParent().getParent()).revalidate();
	    ((JPanel) this.getParent().getParent().getParent().getParent()).repaint();
    }

	@Override
    public void update(boolean[] bool)
    {
		

    }

	@Override
    public void update(int[] integer)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(String[] string)
    {
		name.setText(string[0]);	    
    }
}
