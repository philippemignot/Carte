import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

public class Editeur
{
	// Généraux
	private Insets insetsScreen;
	private Dimension maxSize;
	private boolean isFirstDialog = true;
	// Paramètres
	private Properties proprietes;
	private String[] parametersKeys = {"nbrNiveaux", "nbrPixels", "nbrLignes",
	        "nbrColonnes"};
	private int nbrNiveaux = 4; // nombre total de niveaux
	private int nbrPixels = 32; // nombre de pixel du coté d'une case (carrée)
	private int nbrLignes = 12; // nombre de lignes de la carte
	private int nbrColonnes = 12; // nombre de colonnes de la carte

	// Menu
	private JMenuBar menuBar = new JMenuBar();

	private JMenu menuFichier = new JMenu("Fichier");
	private JMenuItem menuNew = new JMenuItem("Nouvelle carte");
	private JMenuItem menuQuit = new JMenuItem("Quitter");

	private JMenu menuOutils = new JMenu("Outils");
	private JMenuItem menuDefaultParam = new JMenuItem("Paramètres par défaut");

	// Elements
	private JFrame fenetre; // La fenetre
	private JSplitPane jspV;
	private JSplitPane jspH;
	private JScrollPane scrollCarte;
	private JScrollPane scrollOptions;
	private JScrollPane scrollSelection;
	private Selection selection; // Le panneau de sélection des sprites
	private Carte carte; // La carte contenant les sprites
	private Options options; // Le panneau contenant les options de la carte
	private JPanel conteneur; // Le conteneur général

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
	 * @param args
	 *        Les paramètres d'entrée de l'application
	 */
	public static void main(String[] args)
	{
		Editeur editeur = new Editeur(lireConfig("parametres.txt"));
	}

	/**
	 * @param prop
	 * 			Les propriétés
	 */
	public Editeur(Properties prop)
	{
		proprietes = prop;
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
			String current = proprietes.getProperty(parametersKeys[i]);
			if (current != null)
			{
				param[i] = new Integer(current);
			}
			else
			{
				proprietes.setProperty(parametersKeys[i],
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

		// Affichage de la fenêtre
		fenetre.setContentPane(conteneur);
		fenetre.setResizable(true);

		fenetre.setMinimumSize(new Dimension(150, 50));
		menuNew.doClick();

		fenetre.pack();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		insetsScreen = tk.getScreenInsets(fenetre.getGraphicsConfiguration());
		Insets insetsAppli = fenetre.getInsets();
		int width =
		        (int) (d.getWidth() - insetsScreen.left - insetsScreen.right);
		int height =
		        (int) (d.getHeight() - (insetsScreen.top + insetsScreen.bottom));
		maxSize = new Dimension(width, height);
		fenetre.setMaximumSize(maxSize);

		fenetre.setLocation(insetsScreen.left, insetsScreen.top);
		fenetre.setVisible(true);
		setSizeAgain();
		System.out.println("hauteur et largeur max calculées : " + width + "  "
		        + height + " + insets : " + "" + insetsAppli.top + " "
		        + insetsAppli.bottom);
		
	}

	private void setSizeAgain()
	{
		int widthFen = fenetre.getSize().width;
		int heightFen = fenetre.getSize().height;
		if (widthFen > maxSize.width)
			widthFen = maxSize.width;
		if (heightFen > maxSize.height)
			heightFen = maxSize.height;
		fenetre.setSize(widthFen, heightFen);
		
//		if (scrollCarte.getSize().width > scrollCarte.getMaximumSize().width)
//			scrollCarte.setSize(new Dimension(scrollCarte.getMinimumSize().width, scrollCarte.getSize().height));
		fenetre.validate();
		fenetre.repaint();
		System.out.println("taille fenetre : " + fenetre.getSize().width + "  "
		        + fenetre.getSize().height + " + insets : " + ""
		        + fenetre.getInsets().top + " " + fenetre.getInsets().bottom);
		System.out.println("taille options : " + scrollOptions.getSize().width + "  "
		        + scrollOptions.getSize().height + " min " + scrollOptions.getMinimumSize().width + "  "
		        + scrollOptions.getMinimumSize().height);
		
	}

	private void initMenu()
	{
		menuNew.addActionListener(new NewCarteListener(isFirstDialog));
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
		        KeyEvent.CTRL_MASK));
		menuNew.setMnemonic('N');
		menuFichier.add(menuNew);

		menuQuit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		menuQuit.setMnemonic('Q');
		menuFichier.add(menuQuit);
		menuFichier.setMnemonic('F');

		menuDefaultParam.addActionListener(new SetParameterListener());
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
		private boolean isFirstDialog = false;
		
		public NewCarteListener()
		{
			this.isFirstDialog = false;
		}
		
		public NewCarteListener(boolean firstDialog)
		{
			this.isFirstDialog = firstDialog;
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			String[] titles =
			        {"Nombre de niveaux", "Taille d'un sprite (en px)",
			                "Nombre de lignes", "Nombre de colonne"};
			String[] defaults =
			        getParametersFromProperties(proprietes, parametersKeys);
			InputDialog dialogNew =
			        new InputDialog(null, "Nouvelle carte", true, titles,
			                defaults);
			dialogNew.setTextOkButton("Créer");

			String[] results = (dialogNew.showDialog());
			boolean cancelled = true;

			// Test de la réponse : si tout est vide, on ne fait rien
			for (int j = 0; j < results.length; j++)
			{
				if (!results[j].isEmpty())
					cancelled = false;
			}

			if(cancelled && isFirstDialog)
			{
				System.exit(0);
			}
			
			if (!cancelled)
			{
				// Si une carte a déjà été créé
				if (carte != null)
					cleanCarte();
				if (!results[0].isEmpty())
					nbrNiveaux = new Integer(results[0]);
				if (!results[0].isEmpty())
					nbrPixels = new Integer(results[1]);
				if (!results[0].isEmpty())
					nbrLignes = new Integer(results[2]);
				if (!results[0].isEmpty())
					nbrColonnes = new Integer(results[3]);

				createCarte();
			}
			
			
		}
	}

	/**
	 * Ecouteur du menu Nouvelle Carte
	 */
	public class SetParameterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String[] titles =
			        {"Nombre de niveaux", "Taille d'un sprite (en px)",
			                "Nombre de lignes", "Nombre de colonne"};

			String[] defaults =
			        getParametersFromProperties(proprietes, parametersKeys);

			InputDialog dialogParam =
			        new InputDialog(null, "Paramètres par défaut", true,
			                titles, defaults);
			dialogParam.setTextOkButton("Sauvegarder");
			dialogParam.setTextIntro("Paramètres par défaut : ");

			String[] results = (dialogParam.showDialog());
			boolean cancelled = true;

			// Test de la réponse : si tout est vide, on ne fait rien
			for (int j = 0; j < results.length; j++)
			{
				if (!results[j].isEmpty())
					cancelled = false;
			}

			if (!cancelled)
			{
				for (int i = 0; i < parametersKeys.length; i++)
				{
					proprietes.setProperty(parametersKeys[i], results[i]);
				}

				saveConfig("parametres.txt", proprietes);
			}
			verifPropietes(proprietes);
		}
	}

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

		// Ajouts des observateurs
		selection.addObservateur(carte);
		options.addObservateur(carte);

		// Ajoute une scroll bar
		scrollCarte = new JScrollPane(carte);
		scrollOptions = new JScrollPane(options);
		scrollSelection = new JScrollPane(selection);

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

		jspV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollCarte, scrollOptions);
		jspV.setResizeWeight(0.85);
		jspV.setOneTouchExpandable(true);
		
		jspH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSelection, jspV);
		jspH.setResizeWeight(0.30);
		jspH.setOneTouchExpandable(true);
		System.out.println("Split pane créés !");
		
		
		//jspH.setDividerLocation(30);
		
		conteneur.setLayout(new BorderLayout());
		conteneur.add(jspH);
//		conteneur.add(scrollSelection, new GridBagConstraints(0, 0, 2, 1, 1.0,
//		        0.30, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//		        new Insets(5, 5, 5, 5), 0, 0));
//		conteneur.add(scrollCarte, new GridBagConstraints(0, 1, 1,
//		        GridBagConstraints.REMAINDER, 0.95, 0.70,
//		        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
//		                5, 5, 5, 5), 0, 0));
//		conteneur.add(scrollOptions, new GridBagConstraints(1, 1,
//		        GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER,
//		        0.05, 0.70, GridBagConstraints.EAST, GridBagConstraints.BOTH,
//		        new Insets(5, 5, 5, 5), 0, 0));

		fenetre.pack();
		// fenetre.validate();
		if (insetsScreen != null)
			fenetre.setLocation(insetsScreen.left, insetsScreen.top);
		// else()

		if(!isFirstDialog)
		{
			setSizeAgain();
			System.out.println("Taille recalculée");
		}
			
		fenetre.repaint();
		if(isFirstDialog)
			isFirstDialog = false;
	}

	public void cleanCarte()
	{
		// Ajouts des observateurs
		selection.rmvObservateur(carte);
		options.rmvObservateur(carte);

		conteneur.remove(jspH);
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

	/*
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
