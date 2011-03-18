import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Selection extends JPanel implements ActionListener, Observable,
        MouseListener
{
	private int largeurCase;
	private int hauteurCase; // Nombres de pixels par case
	private int nbrColonnes; // Nombre de colonnes de sprites
	private int nbrColonnesAff;
	private int nbrLignesAff = 3; // Nombre de lignes de sprites affichées
	private ArrayList<Case> caseSelected = new ArrayList<Case>(); // Case
	                                                              // actuellement
	                                                              // sélectionnée,
	                                                              // peut être
	                                                              // vide
	private int[] indiceShiftSelection = new int[2]; // coordonnées d'une
	                                                 // sélection en i,j
	private boolean premierShiftOk = false; // indique si le prochain clic avec
	                                        // shift cloturera une sélection

	// Liste des observateurs
	private ArrayList<Observateur> listeObservateur =
	        new ArrayList<Observateur>();

	// Catégories de sprite
	private JPanel categories = new JPanel();
	private ButtonGroup groupe = new ButtonGroup();

	// Affichage des sprites de la catégorie choisie
	private JPanel sprites = new JPanel();
	private JPanel panneauSprites = new JPanel();
	private GridLayout layoutSprites;
	private ArrayList<Case> cases = new ArrayList<Case>();
	private JScrollPane scrollSpr;
	private JScrollPane scrollSel;

	/**
	 * @param lc
	 *        Largeur d'une case en pixels
	 * @param hc
	 *        Hauteur d'une case en pixels
	 * @param nbC
	 *        Nombre de colonnes
	 */
	public Selection(int lc, int hc, int nbC)
	{
		// Initialisation
		largeurCase = lc;
		hauteurCase = hc;
		nbrColonnes = nbC;
		nbrColonnesAff = nbrColonnes + 8; // Le 8 correspond à  peu près à  la
		                                  // place prise par le panneau option

		// Bordure du panneau Selection
		this.setBorder(BorderFactory.createRaisedBevelBorder());

		// Bordure des panneaux internes
		Border b = BorderFactory.createLoweredBevelBorder();

		// Ajout de scrollbar
		scrollSel = new JScrollPane(createCategoriesPanel());
		scrollSel
		        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollSel
		        .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollSel.setBorder(new TitledBorder(b, "Catégories"));
		scrollSel.setMinimumSize(new Dimension(nbrColonnesAff * lc, 120));
		scrollSel.setMaximumSize(new Dimension(nbrColonnesAff * lc, 120));

		scrollSpr = new JScrollPane(createSpritesPanel());

		// avec un peu d'espace pour les scroll bar
		scrollSpr.setMinimumSize(new Dimension(nbrColonnesAff * lc + 30,
		        nbrLignesAff * hc));
		scrollSpr.setMaximumSize(new Dimension(nbrColonnesAff * lc + 30,
		        nbrLignesAff * hc + 60));
		scrollSpr
		        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollSpr.setBorder(b);
		int widthMin =
		        scrollSpr.getMinimumSize().width
		                + scrollSpr.getMinimumSize().width + 30;
		int heightMin =
		        scrollSpr.getMinimumSize().height
		                + scrollSpr.getMinimumSize().height + 30;
		this.setMinimumSize(new Dimension(widthMin, heightMin));

		// Layout
		setLayout(new GridBagLayout());
		add(scrollSel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
		        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5,
		                5, 5, 5), 0, 0));
		add(scrollSpr, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
		        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5,
		                5, 10, 5), 0, 0));
	}

	/**
	 * @param dossier
	 *        Dossier contenant des sprites
	 */
	public void afficherSprites(File dossier)
	{
		// Initialisation
		int nbrImages = 0;
		int nbrLignesReel = nbrLignesAff;

		ArrayList<File> fichiers = getImageFiles(dossier.listFiles());
		nbrImages = fichiers.size();
		if (nbrImages == 0)
		{
			System.err.println("Erreur dans "
			        + "Selection.afficherSprites(File dossier) : "
			        + "pas de fichiers.");
		}

		// Nettoyage des anciennes images
		sprites.removeAll();
		cases.clear();

		// Calcul de la taille du gridLayout
		if (nbrImages > (nbrLignesAff * nbrColonnesAff))
		{
			nbrLignesReel = nbrImages / nbrColonnesAff;
			if (nbrImages % (nbrColonnesAff) != 0)
				nbrLignesReel++;
		}
		System.out.println("Nombre de lignes : " + nbrLignesReel
		        + " et nombre de colonnes " + nbrColonnesAff);
		layoutSprites.setRows(nbrLignesReel);

		// on met toutes les images contenues dans le dossier
		for (int i = 0; i < nbrImages; i++)
		{
			try
			{
				cases.add(new Case(largeurCase, hauteurCase, Color.darkGray,
				        Color.black));
				sprites.add(cases.get(i));
				cases.get(i).addActionListener(this);
				cases.get(i).addMouseListener(this);
				cases.get(i).setImage(ImageIO.read(fichiers.get(i)));
			}
			catch (IOException err)
			{
				System.err.println("Erreur dans "
				        + "Selection.afficherSprites(File dossier) : "
				        + "le fichier lu n'est pas une image.");
				err.printStackTrace();
			}
		}

		// On rajoute des panels factices pour que le grid layout mette le bon
		// nombre de colonnes
		int nbrImagesFactices = (nbrLignesReel * nbrColonnesAff) - nbrImages;

		// Si on a moins de nbrLignesAff lignes, on en rajoute une pour le
		// layout
		if (nbrImages < nbrColonnesAff)
			nbrImagesFactices +=
			        (nbrLignesAff - nbrLignesReel) * nbrColonnesAff;

		System.out.println(">> Nombre de sprites : " + nbrImages
		        + " + panel factices " + nbrImagesFactices);
		for (int i = 0; i < nbrImagesFactices; i++)
		{
			sprites.add(new JPanel());
		}
		repaint();
	}

	private ArrayList<File> getImageFiles(File[] listFiles)
	{
		ArrayList<File> imageFiles = new ArrayList<File>();

		for (int i = 0; i < listFiles.length; i++)
		{
			String[] nomSplit = listFiles[i].getName().split("\\.");
			if (nomSplit.length == 2)
			{
				if (nomSplit[1].equals("png") || nomSplit[1].equals("bmp")
				        || nomSplit[1].equals("jpg"))
				{
					imageFiles.add(listFiles[i]);
				}
			}
		}
		return imageFiles;
	}

	/**
	 * @param caseSel
	 *        Case à  sélectionner
	 */
	public void selectCase(Case caseSel)
	{
		caseSelected.add(caseSel);
		caseSel.setBordure(true);
		caseSel.setCouleurBordure(Color.RED);
		caseSel.setHovered(false);
	}

	public ArrayList<Case> getCaseSelected()
	{
		return caseSelected;
	}

	public void clearSelection()
	{
		if (caseSelected.size() > 1)
		{
			for (Case caseSel : caseSelected)
			{
				caseSel.setCouleurBordure(Color.darkGray);
			}
		}
		else if (!caseSelected.isEmpty())
		{
			caseSelected.get(0).setCouleurBordure(Color.darkGray);
		}
		caseSelected.clear();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		String classe = source.getClass().getName();

		// Gestion du clic sur un bouton radio : changer les sprites affichés
		if (classe.equals("javax.swing.JRadioButton"))
		{
			File dossier =
			        new File("images/" + ((JRadioButton) source).getText());
			afficherSprites(dossier);
			premierShiftOk = false;
			scrollSpr.revalidate();
			scrollSpr.repaint();
		}
	}

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
			ArrayList<Image> listImage = new ArrayList<Image>();
			for (Case caseSel : caseSelected)
			{
				listImage.add(caseSel.getImage());
			}
			obs.update(listImage);
		}
	}

	/**
	 * @return Le panel contenant les catégories
	 */
	public JPanel createCategoriesPanel()
	{
		// Création des boutons radio
		int nbrCat = 0;
		try
		{
			File racine = new File("images");

			for (String s : racine.list())
			{
				if (new File("images/" + s).isDirectory())
				{
					nbrCat++;
					JRadioButton bouton = new JRadioButton(s);
					categories.add(bouton);
					groupe.add(bouton);
					bouton.addActionListener(this);
				}
			}
		}
		catch (NullPointerException e)
		{
			System.err.println("Erreur dans "
			        + "Selection.createCategoriesPanel() : " + e.getMessage());
		}

		categories.setLayout(new GridLayout(3, nbrCat / 3));

		return categories;
	}

	/**
	 * @return Le panel contenant les sprites
	 */
	public JPanel createSpritesPanel()
	{
		panneauSprites.setLayout(new GridBagLayout());
		panneauSprites.add(sprites, new GridBagConstraints(0, 0, 1, 1, 0.0,
		        0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
		        new Insets(0, 0, 0, 0), 0, 0));
		// Création de l'affichage des sprites

		// sprites.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		layoutSprites = new GridLayout();
		layoutSprites.setHgap(0);
		layoutSprites.setVgap(0);
		sprites.setLayout(layoutSprites);
		if (groupe.getButtonCount() <= 0)
		{
			System.err.println("Erreur dans Selection.Selection(int lc, "
			        + "int hc) : pas de dossiers dans le dossier images.");
		}
		else
		{
			((JRadioButton) categories.getComponent(0)).setSelected(true);
			this.afficherSprites(new File("images/"
			        + ((JRadioButton) categories.getComponent(0)).getText()));
		}

		// sprites.setLayout(new GridLayout(nbrSprites/nbrColonnes, nbrColonnes,
		// 0, 0));
		repaint();
		return panneauSprites;
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{

	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		Object source = arg0.getSource();
		String classe = source.getClass().getName();
		if (classe.equals("Case"))
		{
			// Désélection de(s) la case(s) précédente(s)
			if (!arg0.isControlDown() && !arg0.isShiftDown())
			{
				if (!caseSelected.isEmpty())
					clearSelection();
				// Sélection de la nouvelle
				selectCase((Case) source);
				indiceShiftSelection[0] = cases.indexOf(((Case) source));
				premierShiftOk = true;
			}
			else if (arg0.isControlDown())
			{
				if (caseSelected.contains((Case) source))
				{
					caseSelected.remove((Case) source);
					((Case) source).setCouleurBordure(Color.darkGray);
				}
				else
				{
					// Sélection de la nouvelle
					selectCase((Case) source);
				}
			}
			else if (arg0.isShiftDown())
			{
				if (premierShiftOk)
				{
					indiceShiftSelection[1] = cases.indexOf(((Case) source));
					if (indiceShiftSelection[0] != indiceShiftSelection[1])
					{
						if (!caseSelected.isEmpty())
							clearSelection();
						// Sélection de la nouvelle
						for (int i =
						        Math.min(indiceShiftSelection[0],
						                indiceShiftSelection[1]); i <= Math
						        .max(indiceShiftSelection[0],
						                indiceShiftSelection[1]); i++)
							selectCase(cases.get(i));
					}
				}
				else
				{
					if (!caseSelected.isEmpty())
						clearSelection();
					// Sélection de la nouvelle
					selectCase((Case) source);
					indiceShiftSelection[0] =
					        caseSelected.indexOf(((Case) source));
					premierShiftOk = true;
				}
			}

		}
		// Mise à  jour des observateurs
		this.updateObservateur();

	}
}
