import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PanCaseProperties extends JPanel implements Observateur
{
	private PanSpriteProperties[] panCaseProp;
	private int nbrNiveaux;
	
	public PanCaseProperties(int nbrNiv, int largeur, int hauteur)
	{
		nbrNiveaux = nbrNiv;
		panCaseProp = new PanSpriteProperties[nbrNiveaux];
		setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(largeur + 100, (nbrNiveaux * (hauteur + 30))));
		this.setPreferredSize(new Dimension(largeur + 100, (nbrNiveaux * (hauteur + 30))));
		for(int k = 0 ; k < nbrNiveaux ; k++)
		{
			panCaseProp[k] = new PanSpriteProperties(new Sprite(largeur, hauteur), k+1);
			add(panCaseProp[k],  new GridBagConstraints(0, k, 1, 1, 0.0, 0.0,
			        GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
			                5, 5, 5, 5), 0, 0));
		}
	}

	@Override
    public void update(ArrayList<Sprite> sprites)
    {	
	    for(int i = 0 ; i < sprites.size() ; i++)
	    {
	    	panCaseProp[i].setSprite(sprites.get(i));
	    	System.out.println(sprites.get(i).getCode());
	    	panCaseProp[i].revalidate();
	    }
	    
	    System.out.println(panCaseProp[0].getSprite().getCode());
//	    revalidate();
//	    repaint();
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
}
