package com.carte.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import com.carte.sprites.CaseNiveaux;
import com.carte.sprites.Sprite;
import com.carte.utils.CarteFileChooser;
import com.carte.utils.CrtEdFileFilter;
import com.carte.utils.IBinaryFileManager;
import com.carte.utils.ITextFileManager;
import com.carte.utils.OBinaryFileManager;
import com.carte.utils.OTextFileManager;
import com.carte.utils.dialog.InputDialog;
import com.carte.utils.dialog.PCheckBox;
import com.carte.utils.dialog.PComboBox;
import com.carte.utils.dialog.PTextField;
import com.carte.utils.dialog.PanelElement;
import com.carte.utils.dialog.PersoDialog;
import com.carte.utils.dialog.PersoDialogLayout;

public class Editeur
{
	// Généraux
	private Insets insetsScreen; // Insets de l'écran
	private Dimension maxSize; // Dimension maximale possible
	private boolean isFirstDialog = true; // Indique si il s'agit de la fenêtre
	// d'ouverture

	// Paramètres
	private Properties parametres; // Les paramètres nécessaires à la création
	// d'une carte
	// Clés des paramètres
	private String[] parametersKeys = {"nbrNiveaux", "nbrPixels", "nbrLignes",
	"nbrColonnes"};
	private int nbrNiveaux = 4; // nombre total de niveaux
	private int nbrPixels = 32; // nombre de pixel du coté d'une case (carrée)
	private int nbrLignes = 12; // nombre de lignes de la carte
	private int nbrColonnes = 12; // nombre de colonnes de la carte

	// Menu
	private JMenuBar menuBar = new JMenuBar();

	private JMenu menuFichier = new JMenu("Carte");
	private JMenuItem menuNew = new JMenuItem("Nouvelle carte");
	private JMenuItem menuSave = new JMenuItem("Sauvegarder");
	private JMenuItem menuLoad = new JMenuItem("Charger");
	private JMenuItem menuQuit = new JMenuItem("Quitter");

	private JMenu menuOutils = new JMenu("Outils");
	private JMenuItem menuDefaultParam = new JMenuItem("Paramètres par défaut");
	private JMenuItem menuOptions = new JMenuItem("Options");

	// Elements
	private JFrame fenetre; // La fenetre
	private JSplitPane jspV;
	private JSplitPane jspH;
	private JSplitPane jspV2;
	private JScrollPane scrollCarte;
	private JScrollPane scrollOptions;
	private JScrollPane scrollSelection;
	private JScrollPane scrollProprietes;
	private Selection selection; // Le panneau de sélection des sprites
	private Carte carte; // La carte contenant les sprites
	private Options options; // Le panneau contenant les options de la carte
	private PanCaseProperties caseProp;
	private JPanel conteneur; // Le conteneur général
	CarteFileChooser fileChooser = new CarteFileChooser("sauvegardes/");
	CrtEdFileFilter filtreCrt = new CrtEdFileFilter(".carte",
	"Fichier Editeur de carte");
	CrtEdFileFilter filtreCrtTxt = new CrtEdFileFilter(".carte.txt",
	"Fichier Carte explicite");

	// Actions
	private EchapAction echapAction; // Action de la touche Echap
	private Chiffre1Action chiffre1Action; // Action de la touche 1
	private Chiffre2Action chiffre2Action; // Action de la touche 2
	private Chiffre3Action chiffre3Action; // Action de la touche 3
	private Chiffre4Action chiffre4Action; // Action de la touche 4
	private Chiffre5Action chiffre5Action; // Action de la touche 5
	private Chiffre6Action chiffre6Action; // Action de la touche 6
	private Chiffre7Action chiffre7Action; // Action de la touche 7
	private Chiffre8Action chiffre8Action; // Action de la touche 8
	private Chiffre9Action chiffre9Action; // Action de la touche 9
	private UndoAction undoAction; // Action annuler avec ctrl + Z
	private AleaAction aleaAction; // Action pour switcher le mode aléatoire

	// avec ctrl + R

	/**
	 * Main
	 * 
	 * @param args
	 *        Les paramètres d'entrée de l'application
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Editeur editeur = new Editeur(lireConfig("parametres.txt"));
	}

	/**
	 * Crée une nouvelle application éditeur
	 * 
	 * @param prop
	 *        Les propriétés
	 */
	public Editeur(Properties prop)
	{
		parametres = prop;
		int[] param = new int[4]; // Les paramètres envoyés à l'éditeur

		verifPropietes(prop);
		// Valeurs par défauts
		param[0] = 4; // nombre total de niveaux
		param[1] = 32; // nombre de pixel du coté d'une case (carrée)
		param[2] = 12; // nombre de lignes de la carte
		param[3] = 12; // nombre de colonnes de la carte

		// Lecture de la config (prioritaire)

		for (int i = 0; i < parametersKeys.length; i++)
		{
			String current = parametres.getProperty(parametersKeys[i]);
			if (current != null)
			{
				param[i] = new Integer(current);
			}
			else
			{
				parametres.setProperty(parametersKeys[i],
						String.valueOf(param[i]));
			}
		}

		verifPropietes(prop);

		nbrNiveaux = new Integer(param[0]); // nombre total de niveaux
		nbrPixels = new Integer(param[1]); // nombre de pixel du coté d'une case
		// (carrée)
		nbrLignes = new Integer(param[2]); // nombre de lignes de la carte
		nbrColonnes = new Integer(param[3]); // nombre de colonnes de la carte

		// Initialisation de la fenêtre
		fenetre = new JFrame();
		fenetre.setTitle("Éditeur de carte");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialisation du menu
		initMenu();

		// Panneau global
		conteneur = new JPanel();
		conteneur.setLayout(new GridBagLayout());

		// Gestion du clavier
		initActions();
		fileChooser.removeChoosableFileFilter(fileChooser
				.getAcceptAllFileFilter());
		fileChooser.addChoosableFileFilter(filtreCrtTxt);
		fileChooser.addChoosableFileFilter(filtreCrt);

		// Affichage de la fenêtre
		fenetre.setContentPane(conteneur);
		fenetre.setResizable(true);

		fenetre.setMinimumSize(new Dimension(150, 50));
		menuNew.doClick();

		fenetre.pack();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		insetsScreen = tk.getScreenInsets(fenetre.getGraphicsConfiguration());
		int width =
			(int) (d.getWidth() - insetsScreen.left - insetsScreen.right);
		int height =
			(int) (d.getHeight() - (insetsScreen.top + insetsScreen.bottom));
		maxSize = new Dimension(width, height);
		fenetre.setMaximumSize(maxSize);

		fenetre.setLocation(insetsScreen.left, insetsScreen.top);
		fenetre.setVisible(true);
		setSizeAgain();
	}

	/**
	 * Recalcule les taille pour ne pas que l'éditeur soit plus grand que
	 * l'espace disponible sur l'écran
	 */
	private void setSizeAgain()
	{
		int widthFen = fenetre.getSize().width;
		int heightFen = fenetre.getSize().height;
		if (widthFen > maxSize.width)
			widthFen = maxSize.width;
		if (heightFen > maxSize.height)
			heightFen = maxSize.height;
		fenetre.setSize(widthFen, heightFen);

		// if (scrollCarte.getSize().width > scrollCarte.getMaximumSize().width)
		// scrollCarte.setSize(new Dimension(scrollCarte.getMinimumSize().width,
		// scrollCarte.getSize().height));
		fenetre.validate();
		fenetre.repaint();
	}

	/**
	 * Initialise le menu
	 */
	private void initMenu()
	{
		menuNew.addActionListener(new NewCarteListener(isFirstDialog));
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));
		menuNew.setMnemonic('N');
		menuFichier.add(menuNew);

		menuSave.addActionListener(new SauvegarderListener());
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_MASK));
		menuSave.setMnemonic('S');
		menuFichier.add(menuSave);

		menuLoad.addActionListener(new ChargerListener());
		menuLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				KeyEvent.CTRL_MASK));
		menuLoad.setMnemonic('L');
		menuFichier.add(menuLoad);

		menuQuit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		menuQuit.setMnemonic('Q');
		menuFichier.add(menuQuit);
		menuFichier.setMnemonic('C');

		menuOptions.addActionListener(new MenuOptionsListener());
		menuOptions.setMnemonic('O');
		menuOutils.add(menuOptions);

		menuDefaultParam.addActionListener(new MenuDefaultParameterListener());
		menuDefaultParam.setMnemonic('P');
		menuOutils.add(menuDefaultParam);

		menuOutils.setMnemonic('O');

		menuBar.add(menuFichier);
		menuBar.add(menuOutils);

		fenetre.setJMenuBar(menuBar);
	}

	/**
	 * Ecouteur du menu Nouvelle Carte
	 */
	public class NewCarteListener implements ActionListener
	{
		/**
		 * Crée un nouvea listener
		 */
		public NewCarteListener()
		{
			isFirstDialog = false;
		}

		/**
		 * Crée un nouveau listener en spécifiant s'il s'agit de la fenêtre de
		 * démarrage
		 * 
		 * @param firstDialog
		 *        Spécifie si il s'agit de la fenêtre de démarrage
		 */
		public NewCarteListener(boolean firstDialog)
		{
			isFirstDialog = firstDialog;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String[] titles =
			{"Nombre de niveaux", "Taille d'un sprite (en px)",
					"Nombre de lignes", "Nombre de colonne"};
			String[] defaults =
				getParametersFromProperties(parametres, parametersKeys);
			InputDialog dialogNew =
				new InputDialog(null, "Nouvelle carte", true, titles);
			dialogNew.setTextIntro("Paramètres de la nouvelle carte");
			dialogNew.setDefaults(defaults);
			dialogNew.setTextOkButton("Créer");

			String[] results = new String[titles.length];
			boolean validated = dialogNew.showDialog(results);

			if (!validated && isFirstDialog)
			{
				System.exit(0);
			}

			if (validated)
			{
				// Si une carte a déjà été créé
				if (carte != null)
					cleanCarte();
				if (!results[0].isEmpty())
					nbrNiveaux = stringToInteger(results[0], defaults[0]);
				if (!results[1].isEmpty())
					nbrPixels = stringToInteger(results[1], defaults[1]);
				if (!results[2].isEmpty())
					nbrLignes = stringToInteger(results[2], defaults[2]);
				if (!results[3].isEmpty())
					nbrColonnes = stringToInteger(results[3], defaults[3]);

				createCarte();
			}
		}
	}

	/**
	 * Convertie un String en entier
	 * 
	 * @param StringInt
	 *        Le String contenant un entier
	 * @param defaut
	 *        La valeur par défaut à renvoyer si echec
	 * @return L'entier sous la forme entier
	 */
	private int stringToInteger(String StringInt, String defaut)
	{
		int storeInt;
		try
		{
			storeInt = new Integer(StringInt);
		}
		catch (NumberFormatException e)
		{
			storeInt = new Integer(defaut);
		}

		return storeInt;
	}

	/**
	 * Ecouteur du menu Sauvegarder
	 */
	public class SauvegarderListener implements ActionListener
	{
		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				File fichier = fileChooser.getSelectedFile();
				if (fileChooser.getFileFilter() == filtreCrt
						&& filtreCrt.accept(fichier))
				{
					System.out.println("Sauvegarde carte binaire en cours...");
					ArrayList elementsToSave = new ArrayList();
					elementsToSave.add(new Integer(nbrNiveaux));
					elementsToSave.add(new Integer(nbrPixels));
					elementsToSave.add(new Integer(nbrLignes));
					elementsToSave.add(new Integer(nbrColonnes));

					for (int i = 0; i < nbrLignes; i++)
					{
						for (int j = 0; j < nbrColonnes; j++)
						{
							for (int k = 0; k < nbrNiveaux; k++)
							{
								elementsToSave.add(carte.getCases()[i][j]
								                                       .getSprite(k + 1).getCode());
							}

						}
					}

					OBinaryFileManager iobfm = new OBinaryFileManager(fichier);
					iobfm.setElementsToSave(elementsToSave);
					iobfm.save();
				}
				else if (fileChooser.getFileFilter() == filtreCrtTxt
						&& filtreCrtTxt.accept(fichier))
				{
					System.out
					.println("Sauvegarde carte explicite en cours...");
					ArrayList elementsToSave = new ArrayList();
					elementsToSave.add(nbrPixels);
					elementsToSave.add(nbrLignes);
					elementsToSave.add(nbrColonnes);
					elementsToSave.add(nbrNiveaux);

					OTextFileManager otfm = new OTextFileManager(fichier);
					otfm.addElementsToSave(elementsToSave, true);
					elementsToSave.clear();

					elementsToSave.add("codeImage");
					otfm.addElementsToSave(elementsToSave, true);
					elementsToSave.clear();

					CaseNiveaux[][] cases = carte.getCases();

					for (int i = 0; i < nbrLignes; i++)
					{
						for (int j = 0; j < nbrColonnes; j++)
						{
							for (int k = 1; k <= nbrNiveaux; k++)
							{
								if (!cases[i][j].getSprite(k).getCode()
										.isEmpty())
								{
									elementsToSave.add(j);
									elementsToSave.add(i);
									elementsToSave.add(k);
									elementsToSave.add(cases[i][j].getSprite(k)
											.getCode());
									otfm.addElementsToSave(elementsToSave);
									elementsToSave.clear();
								}

							}
						}
					}
					otfm.save();
				}
				else
				{
					// Si vous n'avez pas spécifié une extension valide !
					JOptionPane
					.showMessageDialog(
							null,
							"Erreur d'extension de fichier ! \nLa sauvegarde a échoué !",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Ecouteur du menu Charger
	 */
	public class ChargerListener implements ActionListener
	{
		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				String[][][] codesCases = null;
				boolean donneesOk = true;
				String fileName = "";
				File fichier = fileChooser.getSelectedFile();
				if (fileChooser.getFileFilter() == filtreCrtTxt
						&& filtreCrtTxt.accept(fichier))
				{
					ITextFileManager itfm = new ITextFileManager(fichier);
					ArrayList donneesLues = itfm.load(1, true);

					if (((ArrayList) donneesLues.get(0)).size() >= 4)
					{
						nbrPixels = new Integer(stringToInteger( (String)((ArrayList) donneesLues.get(0)).get(0), ""));
						nbrLignes = new Integer(stringToInteger( (String) ((ArrayList) donneesLues.get(0)).get(1), ""));
						nbrColonnes = new Integer(stringToInteger( (String) ((ArrayList) donneesLues.get(0)).get(2), ""));
						nbrNiveaux = new Integer(stringToInteger( (String) ((ArrayList) donneesLues.get(0)).get(3), ""));
					} else
					{
						donneesOk = false;
					}

					if (donneesOk)
					{
						codesCases =
							new String[nbrLignes][nbrColonnes][nbrNiveaux];

						// Initialisation
						for (int i = 0; i < nbrLignes; i++)
						{
							for (int j = 0; j < nbrColonnes; j++)
							{
								for (int k = 0; k < nbrNiveaux; k++)
								{

									codesCases[i][j][k] = "";
								}

							}
						}

						donneesLues = itfm.load(3);
						donneesLues = itfm.loadLasting();

						Iterator ite = donneesLues.iterator();
						while (ite.hasNext())
						{
							ArrayList line = (ArrayList) ite.next();
							if (line.size() >= 4)
							{
								int colonne = stringToInteger( (String)line.get(0), "");
								int ligne = stringToInteger( (String)line.get(1), "");
								int niveau = stringToInteger( (String)line.get(2), "") - 1;
								codesCases[ligne][colonne][niveau] = (String) line.get(3);
//								System.out.println(ligne + " " + colonne + " " + niveau + " : " + (String) line.get(3));
							}
							else if (!(line.size() == 1 && line.get(0) == ""))
							{
								donneesOk = false;
							}
						}
					}
					fileName = itfm.getFileName();
				}
				else
				{
					IBinaryFileManager iobfm = new IBinaryFileManager(fichier);

					ArrayList elementsType = new ArrayList();
					elementsType.add(new Integer(0));
					int[] nbOfElements = {4};
					iobfm.addNewElementsStructure(elementsType, nbOfElements);

					iobfm.openInputStream();
					ArrayList carteLoaded = iobfm.loadAll(true, false);

					nbrNiveaux = (Integer) carteLoaded.get(0);
					nbrPixels = (Integer) carteLoaded.get(1);
					nbrLignes = (Integer) carteLoaded.get(2);
					nbrColonnes = (Integer) carteLoaded.get(3);

					elementsType.clear();
					elementsType.add(new String(""));
					nbOfElements[0] = nbrNiveaux * nbrLignes * nbrColonnes;
					iobfm.addNewElementsStructure(elementsType, nbOfElements);

					carteLoaded.clear();
					carteLoaded = iobfm.loadAll(false, true);
					codesCases =
						new String[nbrLignes][nbrColonnes][nbrNiveaux];

					// Initialisation
					codesCases = new String[nbrLignes][nbrColonnes][nbrNiveaux];
					int nb = 0;
					for (int i = 0; i < nbrLignes; i++)
					{
						for (int j = 0; j < nbrColonnes; j++)
						{
							for (int k = 0; k < nbrNiveaux; k++)
							{

								codesCases[i][j][k] =
									(String) carteLoaded.get(nb);
								nb++;
							}
						}
					}
					fileName = iobfm.getFileName();
				}
				if (donneesOk)
				{
					if (carte != null)
						cleanCarte();

					createCarte();
					
					for (int a = 0; a < nbrLignes; a++)
					{
						for (int b = 0; b < nbrColonnes; b++)
						{
							// On lit les codes de chaque case

							// Si il y a moins de niveaux, on peut quand
							// même charger
							for (int k = 0; k < nbrNiveaux; k++)
							{
								Sprite spr = new Sprite(nbrPixels, nbrPixels);
								if (codesCases[a][b][k].matches("[0-9]{5}")
										&& !codesCases[a][b][k].equals("00000"))
								{
									Image img =
										chargerImage(codesCases[a][b][k]);
									spr.setImage(img, new String(
											codesCases[a][b][k]), 0);
									if (img == null)
										System.err
										.println("Image chargée nulle");
								}
								else
								{
									spr.setImage(null, "", 0);

								}
								carte.getCases()[a][b].setSprite(spr, k + 1);
							}
						}
					}

					fenetre.setTitle("Editeur de carte - "
							+ fileName);
				} else
				{
					JOptionPane
					.showMessageDialog(
							null,
							"Mauvaises donnees : parametrage impossible",
							"Problème lors du chargement", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				// Si vous n'avez pas spécifié une extension valide !
				JOptionPane
				.showMessageDialog(
						null,
						"Erreur d'extension de fichier ! \nLe chargement a échoué !",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}

	}


	/**
	 * Ecouteur du menu Default parameters
	 */
	public class MenuDefaultParameterListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String[] titles =
			{"Nombre de niveaux", "Taille d'un sprite (en px)",
					"Nombre de lignes", "Nombre de colonne"};

			String[] defaults =
				getParametersFromProperties(parametres, parametersKeys);

			InputDialog dialogParam =
				new InputDialog(null, "Paramètres par défaut", true, titles);
			dialogParam.setDefaults(defaults);
			dialogParam.setTextOkButton("Sauvegarder");
			dialogParam.setTextIntro("Paramètres par défaut");

			String[] results = new String[titles.length];
			boolean validated = dialogParam.showDialog(results);

			if (validated)
			{
				for (int i = 0; i < parametersKeys.length; i++)
				{
					parametres.setProperty(parametersKeys[i], results[i]);
				}

				saveConfig("parametres.txt", parametres);
			}
			verifPropietes(parametres);
		}
	}

	/**
	 * Ecouteur du menu Options
	 */
	public class MenuOptionsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// Création des éléments
			PCheckBox nivPersoActive =
				new PCheckBox("Niveau fixe pour les personnages");

			String[] niveaux = new String[nbrNiveaux];
			for (int i = 1; i <= nbrNiveaux; i++)
			{
				niveaux[i - 1] = "Niveau " + i;
			}
			PComboBox nivPerso = new PComboBox(niveaux);

			PCheckBox quadriStart =
				new PCheckBox("Quadrillage affiché au démarrage");
			PTextField testTextField =
				new PTextField("1", "Entrez un nombre :");

			PersoDialog dialogOptions =
				new PersoDialog(null, "Options", true,
						PersoDialogLayout.Vertical);
			PanelElement groupNiv =
				dialogOptions.createGroup(PersoDialogLayout.Horizontal);
			groupNiv.addTitle("Niveaux fixes");
			groupNiv.addElement(nivPersoActive);
			groupNiv.addElement(nivPerso, true);
			dialogOptions.addGroup(groupNiv);
			dialogOptions.addElement(quadriStart);
			dialogOptions.addElement(testTextField);

			dialogOptions.setTextOkButton("Sauvegarder");
			dialogOptions.setTextIntro("Options de l'éditeur de carte");

			dialogOptions.addCondActive(nivPerso, nivPersoActive, "1");
			boolean validated = dialogOptions.showDialog();

			if (validated)
			{
				if (nivPersoActive.isSelected())
				{
					System.out.println("Oui : "
							+ (String) nivPerso.getSelectedItem());
				}
				else
				{
					System.out.println("Non");
				}
				System.out.println("Quaddrillage "
						+ (quadriStart.isSelected() ? "affiché" : "masqué")
						+ " au démarrage");
				System.out.println("Nombre " + testTextField.getText());
			}
		}
	}

	/**
	 * Crée une nouvelle carte
	 */
	public void createCarte()
	{
		// Création de la carte :
		// tableau taille×taille de cases nbrPixels×nbrPixels
		// possédant un nombre de niveaux nbrNiveaux
		carte =
			new Carte(nbrColonnes, nbrLignes, nbrPixels, nbrPixels,
					nbrNiveaux);

		// Création du panneau d'option : il possède nbrNiveaux niveaux
		options = new Options(nbrNiveaux);

		// Création de la partie sélection :
		// taille colonnes de cases nbrPixels×nbrPixels
		selection = new Selection(nbrPixels, nbrPixels, nbrColonnes);

		// Création du panneau propriété : il affiche les propriétés de chaque
		// case
		caseProp = new PanCaseProperties(nbrNiveaux, nbrPixels, nbrPixels);

		// Ajouts des observateurs
		selection.addObservateur(carte);
		options.addObservateur(carte);
		carte.addObservateur(caseProp);
		selection.addObservateur(caseProp);
		caseProp.addObservateur(carte);

		// Ajoute une scroll bar
		scrollCarte = new JScrollPane(carte);
		scrollOptions = new JScrollPane(options);
		scrollSelection = new JScrollPane(selection);
		scrollProprietes = new JScrollPane(caseProp);

		scrollCarte
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollCarte
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollOptions
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollOptions
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollSelection
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollSelection
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollProprietes
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollProprietes
		.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		jspV =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollCarte,
					scrollOptions);
		jspV.setResizeWeight(0.85);
		jspV.setOneTouchExpandable(true);

		jspH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSelection, jspV);
		jspH.setResizeWeight(0.30);
		jspH.setOneTouchExpandable(true);

		jspV2 =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollProprietes,
					jspH);
		jspV2.setResizeWeight(0.85);
		jspV2.setOneTouchExpandable(true);

		conteneur.setLayout(new BorderLayout());
		conteneur.add(jspV2);

		fenetre.pack();
		if (insetsScreen != null)
			fenetre.setLocation(insetsScreen.left, insetsScreen.top);

		if (!isFirstDialog)
		{
			setSizeAgain();
		}

		fenetre.repaint();
		if (isFirstDialog)
			isFirstDialog = false;
	}

	/**
	 * Récupère une image à partir de son code image
	 * 
	 * @param code
	 *        Le code image de l'image à récupérer
	 * @return L'image correspondant au code image
	 */
	public Image chargerImage(String code)
	{
		Image img = null;

		File racine = new File("images");

		for (String s : racine.list())
		{
			File sFile = new File("images/" + s);
			if (sFile.isDirectory())
			{
				String[] split = s.split("_");
				// Si le nom de dossier comporte au moins deux parties
				if (split.length >= 2)
				{
					// Si on a exactement 2 nombres
					if (split[0].matches("[0-9]{2}"))
					{
						for (String f : sFile.list())
						{
							if (f.startsWith(code))
							{
								try
								{
									img =
										ImageIO.read(new File("images/" + s
												+ "/" + f));
								}
								catch (IOException e)
								{
									System.err
									.println("IO erreur pendant le chargement de l'image : "
											+ e.getMessage());
								}
							}
						}
					}
				}
			}
		}

		return img;
	}

	/**
	 * Efface proprement une carte
	 */
	public void cleanCarte()
	{
		// Suppression des observateurs
		selection.rmvObservateur(carte);
		options.rmvObservateur(carte);
		carte.rmvObservateur(caseProp);
		selection.rmvObservateur(caseProp);
		caseProp.rmvObservateur(carte);

		conteneur.remove(jspV2);
	}

	/**
	 * Sauvegarde et/ou met à jour des valeurs dans un fichier de configuration.
	 * 
	 * @param fileName
	 *        Le nom du fichier de configuration
	 * @param prop
	 *        Les propriétes à enregistrer
	 */
	public boolean saveConfig(String fileName, Properties prop)
	{
		boolean saveOk = true;
		File config = new File(fileName); // Fichier de configuration
		DataOutputStream dos;
		try
		{
			dos =
				new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(config)));
			try
			{
				prop.store(dos, null); // Le second argument permet
				// d'enregistrer des commentaires
			}
			catch (IOException e)
			{
				saveOk = false;
				System.err.println("IO erreur pendant l'enregistrement : "
						+ e.getMessage());
			}
			try
			{
				dos.close();
			}
			catch (IOException e)
			{
				System.err.println("IO erreur pendant la fermeture du fux : "
						+ e.getMessage());
			}
		}
		catch (FileNotFoundException e1)
		{
			saveOk = false;
			System.err.println("Fichier de configuration non trouvé : "
					+ e1.getMessage());
		}

		return saveOk;
	}

	/**
	 * Récupère des valeurs depuis un fichier de configuration.
	 * 
	 * @param fileName
	 *        Le nom du fichier de configuration
	 * @return Les paramètres lus (venant de la config si le paramètre a pu être
	 *         lu, sinon -1)
	 */
	public static Properties lireConfig(String fileName)
	{
		File config = new File(fileName); // Fichier de configuration

		// Lecture de la config

		FileReader fr;
		Properties prop = new Properties();

		try
		{
			// création de l'objet de lecture
			fr = new FileReader(config);

			// Lecture des données
			prop.load(fr);

			fr.close();

		}
		catch (FileNotFoundException e)
		{
			System.err.println("Fichier de configuration non trouvé : "
					+ e.getMessage());
		}
		catch (IOException e)
		{
			System.err.println("IO erreur : " + e.getMessage());
		}

		return prop;
	}

	public String[] getParametersFromProperties(Properties prop, String[] keys)
	{
		String[] results = new String[keys.length];

		for (int i = 0; i < keys.length; i++)
		{
			results[i] = prop.getProperty(keys[i], "");
		}

		return results;
	}

	/**
	 * Ecris dans le fichier de log le contenu d'un objet Properties
	 * 
	 * @param prop
	 *        L'objet properties à vérifier
	 */
	public void verifPropietes(Properties prop)
	{
		PrintStream ps;
		try
		{
			ps = new PrintStream("log.txt");
			prop.list(ps);
			ps.close();
			System.out.println("Fichier log updated");
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Fichier de log non trouvé : " + e.getMessage());
		}
	}

	/**
	 * Initialise et lie les différentes actions aux touches
	 */
	public void initActions()
	{
		// action touche Escape
		echapAction = new EchapAction();
		// options.getDeselecButton().addActionListener(echapAction);
		// options.getDeselecButton().setMnemonic(new Integer(KeyEvent.VK_L));

		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("ESCAPE"), "action escape");
		fenetre.getRootPane().getActionMap().put("action escape", echapAction);

		// actions des chiffres 1-9
		chiffre1Action = new Chiffre1Action();
		chiffre2Action = new Chiffre2Action();
		chiffre3Action = new Chiffre3Action();
		chiffre4Action = new Chiffre4Action();
		chiffre5Action = new Chiffre5Action();
		chiffre6Action = new Chiffre6Action();
		chiffre7Action = new Chiffre7Action();
		chiffre8Action = new Chiffre8Action();
		chiffre9Action = new Chiffre9Action();

		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('1'), "action chiffre 1");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 1", chiffre1Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('2'), "action chiffre 2");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 2", chiffre2Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('3'), "action chiffre 3");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 3", chiffre3Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('4'), "action chiffre 4");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 4", chiffre4Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('5'), "action chiffre 5");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 5", chiffre5Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('6'), "action chiffre 6");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 6", chiffre6Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('7'), "action chiffre 7");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 7", chiffre7Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('8'), "action chiffre 8");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 8", chiffre8Action);
		fenetre.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke('9'), "action chiffre 9");
		fenetre.getRootPane().getActionMap()
		.put("action chiffre 9", chiffre9Action);

		// Actions du menu
		undoAction = new UndoAction();

		fenetre.getRootPane()
		.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke
				.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK),
		"action undo");
		fenetre.getRootPane().getActionMap().put("action undo", undoAction);

		// Actions du mode aleatoire
		aleaAction = new AleaAction();

		fenetre.getRootPane()
		.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke
				.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK),
		"action alea");
		fenetre.getRootPane().getActionMap().put("action alea", aleaAction);
	}

	@SuppressWarnings("serial")
	class EchapAction extends AbstractAction
	{
		public EchapAction()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			carte.deselectionneCurseur();
			selection.clearSelection();
		}
	}

	@SuppressWarnings("serial")
	class Chiffre1Action extends AbstractAction
	{
		public Chiffre1Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(1);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre2Action extends AbstractAction
	{
		public Chiffre2Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(2);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre3Action extends AbstractAction
	{
		public Chiffre3Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(3);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre4Action extends AbstractAction
	{
		public Chiffre4Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(4);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre5Action extends AbstractAction
	{
		public Chiffre5Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(5);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre6Action extends AbstractAction
	{
		public Chiffre6Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(6);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre7Action extends AbstractAction
	{
		public Chiffre7Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(7);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre8Action extends AbstractAction
	{
		public Chiffre8Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(8);
		}
	}

	@SuppressWarnings("serial")
	class Chiffre9Action extends AbstractAction
	{
		public Chiffre9Action()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.selectNiveau(9);
		}
	}

	@SuppressWarnings("serial")
	class UndoAction extends AbstractAction
	{
		public UndoAction()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			carte.undoCases();
		}
	}

	@SuppressWarnings("serial")
	class AleaAction extends AbstractAction
	{
		public AleaAction()
		{
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			options.changeAleatoire();
		}
	}
}
