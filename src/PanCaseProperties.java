import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;


@SuppressWarnings("serial")
public class PanCaseProperties extends JPanel implements Observateur, ActionListener, Observable
{
	private PanSpriteProperties[] panSpriteProp;
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
		panSpriteProp = new PanSpriteProperties[sprites.size()];
		contentPane.removeAll();
		contentPane.add(name,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
		                15, 5, 15, 5), 0, 0));
	    for(int i = 0 ; i < sprites.size() ; i++)
	    {
	    	panSpriteProp[i] = new PanSpriteProperties(new Sprite(sprites.get(i)), i+1, nbrNiveaux);
	    	
	    	setPropLayoutPosition(panSpriteProp[i], i+2);
	    	if(name.getText().toLowerCase().equals("carte"))
	    	{
	    		panSpriteProp[i].addToolbar();
	    		panSpriteProp[i].addToolbarListener(this);
	    	}
		    panSpriteProp[i].revalidate();	    	
	    }
	    if(name.getText().toLowerCase().equals("carte"))
    	{
	    	this.setMinimumSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 100))));
	    	this.setPreferredSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 100))));
    	}else
    	{
    		this.setMinimumSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 60))));
    		this.setPreferredSize(new Dimension(largeur + 150, (sprites.size() * (hauteur + 60))));
    	}
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

	@Override
    public void actionPerformed(ActionEvent e)
    {
	   if(e.getSource().getClass().getCanonicalName().equals("javax.swing.JButton"))
	   {
		   if(e.getActionCommand().matches("[a-z]+[_][1-9]"))
		   {
			   String[] infosSource = e.getActionCommand().split("_");
			   int pos = new Integer(infosSource[1]);
			   System.out.println(pos);
			   if(infosSource[0].equalsIgnoreCase("avant"))
			   {
				   modifiesPropPosition(getProp(pos), pos - 1);
			   }
			   else if(infosSource[0].equalsIgnoreCase("apres"))
			   {
				   modifiesPropPosition(getProp(pos), pos + 1);
			   }
			   else if(infosSource[0].equalsIgnoreCase("suppr"))
			   {
				   System.out.println("Suppression " + getProp(pos).getNiveau() + " !!");
				   getProp(pos).supprSprite();
			   }
		   }
		   
	   }
    }
	
	private void setPropLayoutPosition(PanSpriteProperties psp, int pos)
	{
		contentPane.add(psp,  new GridBagConstraints(0, pos, 1, 1, 1.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
		                5, 10, 5, 10), 0, 0));
	}

	private void rmPropLayoutPosition(PanSpriteProperties psp, int pos)
	{
		contentPane.remove(psp);
	}
	
	private void modifiesPropPosition(PanSpriteProperties psp, int pos)
	{
		int posActuel = psp.getNiveau();
		PanSpriteProperties psp2 = getProp(pos); // Le composant duquel on prend la place
		
		// On modifie le layout
		rmPropLayoutPosition(psp, posActuel);
		rmPropLayoutPosition(psp2, pos);
		
		setPropLayoutPosition(psp, pos + 1); // 1 est le label de titre
		setPropLayoutPosition(psp2, posActuel + 1);
		
		// On met à jour la liste des panneaux de propriété
		panSpriteProp[posActuel - 1] = psp2;
		panSpriteProp[pos - 1] = psp;
		
		// On met à jour les infos de niveau du panneau
		psp.setNiveau(pos);
		psp2.setNiveau(posActuel);
		
		revalidate();
		repaint();
	}

	private PanSpriteProperties getProp(int pos)
    {
		return panSpriteProp[pos - 1]; // commence à 0
    }

	@Override
    public void addObservateur(Observateur obs)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void rmvObservateur(Observateur obs)
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateObservateur()
    {
	    // TODO Auto-generated method stub
	    
    }
}
