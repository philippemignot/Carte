import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Options extends JPanel implements Observable
{
	private ArrayList<Observateur> listeObservateur =
	        new ArrayList<Observateur>(); // liste des observateurs
	private JCheckBox quadrillage; // checked pour afficher le quadrillage
	private JCheckBox aleatoire; // checked pour afficher le quadrillage
	private JCheckBox[] nivAff; // checked pour afficher le niveau correspondant
	private boolean[] infosBool; // informations sur l'état des check-box ; pour
	                             // le DP observer : [0] => showQuadrill; [1 ->
	                             // nbrNiv] => showNiv ; [nbrNiv + 1] => useAlea
	private int[] infoInt; // information sur le niveau sélectionné ; pour le DP
	                       // observer : [0] => selectedNiv ; [1] => perctAlea
	private int nbrNiv; // nombre total de niveaux, commence à 1
	private JRadioButton[] nivChoix; // permet de changer le niveau sélectionné,
	                                 // commence à 0 pour niv 1
	private JSlider slideAlea;
	private JLabel textAlea;

	public Options(int nbrNiveau)
	{

		nbrNiv = nbrNiveau;
		infosBool = new boolean[nbrNiv + 2];
		for (int k = 0; k < nbrNiv + 2; k++)
		{
			infosBool[k] = true;
		}
		infoInt = new int[2];
		infoInt[0] = 1;
		infoInt[1] = 100;

		JLabel titre = new JLabel("Options");
		titre.setFont(new Font("Tahoma", Font.BOLD, 14));

		quadrillage = new JCheckBox("Quadrillage", true);
		quadrillage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				infosBool[0] = quadrillage.isSelected();
				updateObservateur();
			}
		});
		this.setMinimumSize(new Dimension((230 + nbrNiv * 30), 250));
		this.setLayout(new GridBagLayout());
		setNiveauOptions();
		setAleaOptions();

		this.add(titre, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
		        GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(
		                5, 10, 25, 10), 0, 0));
		this.add(quadrillage, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
		        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
		                5, 10, 5, 10), 0, 0));
	}

	private void setAleaOptions()
	{
		JPanel pAlea = new JPanel();
		aleatoire = new JCheckBox("Activer", true);
		aleatoire.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				infosBool[nbrNiv + 1] = aleatoire.isSelected();

				// Active ou non la slide barre en fonction de l'activation de
				// l'aléatoire
				togglePerctAlea();
				updateObservateur();
			}
		});
		slideAlea = new JSlider(0, 100, 100);
		textAlea = new JLabel("Remplir : 100 %");
		slideAlea.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent event)
			{
				textAlea.setText("Remplir : "
				        + new String(String.valueOf(((JSlider) event
				                .getSource()).getValue())) + " %");
				infoInt[1] = ((JSlider) event.getSource()).getValue();
				updateObservateur();
			}
		});
		this.add(pAlea, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
		        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
		                5, 10, 5, 10), 0, 0));

		Border b = BorderFactory.createRaisedBevelBorder();
		pAlea.setBorder(new TitledBorder(b, "Aléatoire"));
		pAlea.setLayout(new GridBagLayout());
		pAlea.add(aleatoire, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
		        new Insets(5, 10, 5, 10), 0, 0));
		pAlea.add(slideAlea, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
		        GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
		        new Insets(5, 10, 5, 10), 0, 0));
		pAlea.add(textAlea, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
		        new Insets(5, 10, 5, 10), 0, 0));
	}

	private void setNiveauOptions()
	{
		JPanel pNiveaux = new JPanel();
		nivAff = new JCheckBox[nbrNiv];
		JPanel pNivSelect = new JPanel();
		nivChoix = new JRadioButton[nbrNiv];
		ButtonGroup bg = new ButtonGroup();

		this.add(pNiveaux, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
		        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
		        new Insets(5, 10, 5, 10), 0, 0));
		this.add(pNivSelect, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
		        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
		        new Insets(5, 10, 5, 10), 0, 0));

		Border b = BorderFactory.createRaisedBevelBorder();
		this.setBorder(b);
		pNiveaux.setLayout(new GridLayout(nbrNiv, 1, 5, 2));
		// pNiveaux.add(titreNiv);

		pNiveaux.setBorder(new TitledBorder(b, "Afficher"));
		pNivSelect.setLayout(new GridLayout(nbrNiv, 1, 5, 2));
		pNivSelect.setBorder(new TitledBorder(b, "Poser au"));
		// pNivSelect.add(titreNivSel);

		for (int i = 0; i < nbrNiv; i++)
		{
			nivAff[i] = new JCheckBox("Niveau " + (i + 1), true);
			pNiveaux.add(nivAff[i]);
			nivAff[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					for (int j = 0; j < nbrNiv; j++)
					{
						infosBool[j + 1] = nivAff[j].isSelected();
					}

					updateObservateur();
				}
			});

			nivChoix[i] = new JRadioButton("Niveau " + String.valueOf(i + 1));
			nivChoix[0].setSelected(true);
			bg.add(nivChoix[i]);
			pNivSelect.add(nivChoix[i]);
			nivChoix[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					int numNiv = 1;
					for (int j = 0; j < nbrNiv; j++)
					{
						if (nivChoix[j].isSelected())
							numNiv = j + 1;
					}
					infoInt[0] = numNiv;

					updateObservateur();
				}
			});
		}
	}

	// fonction du DP observer
	@Override
	public void addObservateur(Observateur obs)
	{
		listeObservateur.add(obs);

	}

	@Override
	public void rmvObservateur(Observateur obs)
	{
		listeObservateur.remove(obs);
	}

	@Override
	public void updateObservateur()
	{
		for (Observateur obs : listeObservateur)
		{
			obs.update(infosBool);
			obs.update(infoInt);
		}
	}

	public void selectNiveau(int niv)
	{
		if (niv <= nbrNiv)
		{
			nivChoix[niv - 1].setSelected(true);
			infoInt[0] = niv;
			updateObservateur();
		}
	}

	public JCheckBox getAleatoire()
	{
		return aleatoire;
	}

	public void changeAleatoire()
	{
		aleatoire.doClick();
	}

	public void togglePerctAlea()
	{
		if (!aleatoire.isSelected())
		{
			slideAlea.setEnabled(false);
			textAlea.setEnabled(false);
		}
		else
		{
			slideAlea.setEnabled(true);
			textAlea.setEnabled(true);
		}
	}
	/*
	 * @Override public void keyTyped(KeyEvent arg0) { int num =
	 * Character.getNumericValue(arg0.getKeyChar()); System.out.println(num);
	 * for(int i = 0 ; i < nbrNiv ; i++) { if(num == i+1) {
	 * nivChoix[i].setSelected(true); infoNiv = i+1; updateObservateur(); } } }
	 */
}
