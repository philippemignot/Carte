package com.carte.utils;
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
import javax.swing.JLabel;
import javax.swing.KeyStroke;


@SuppressWarnings("serial")
public abstract class AbstractDialog extends JDialog
{
		protected String[] titles; // Les titre des éléments
		protected int[] groupes = new int[0]; // Les différents groupes
		protected String[] returns; // Les valeurs à retourner
		protected JButton okButton = new JButton("Ok"); // Le bouton de validation
		protected JButton cancelButton = new JButton("Annuler"); // Le bouton d'annulation
		protected JLabel textIntro = new JLabel("Veuillez entrer les paramètres :"); // Le texte d'introduction

		/**
		 * Crée une fenêtre de dialogue avec les titres demandés, les champs vides
		 * 
		 * @param parent
		 * 				La fenêtre parente
		 * @param title
		 * 				Le titre de la fenêtre
		 * @param modal
		 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée
		 * @param titres
		 * 				Les titres de chaque éléments. Autant d'éléments sont créés qu'il y a de titres.
		 */
		public AbstractDialog(JFrame parent, String title, boolean modal,
		        String[] titres)
		{
			super(parent, title, modal);
			init(titres);
			setButtons();
			initSize();
		}
		
		/**
		 * Crée une fenêtre de dialogue avec les titres demandés. Permet de regrouper les éléments par sujet.
		 * Pas encore bien pris en compte.
		 * 
		 * @param parent
		 * 				La fenêtre parente
		 * @param title
		 * 				Le titre de la fenêtre
		 * @param modal
		 * 				La modalité de la fenêtre : true - bloque l'application tant qu'elle n'est pas fermée
		 * @param titres
		 * 				Les titres de chaque éléments. Autant de éléments sont créés qu'il y a de titres.
		 * @param groupes
		 * 				Détermine de quel groupe font partie chaque TextField
		 */
		public AbstractDialog(JFrame parent, String title, boolean modal,
		        String[] titres, int[] groupes)
		{
			super(parent, title, modal);
			this.groupes = groupes;
			init(titres);
			setButtons();
			initSize();
		}

		/**
		 * Initialise ce qui a rapport à la taille et la position de la fenêtre
		 */
		protected void initSize()
		{
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			this.pack();
		}

		/**
		 * Permet de placer les éléments dans la fenêtre
		 */
		protected abstract void setElements();

		/**
		 * Initialise les titres des éléments
		 * 
		 * @param titres
		 * 			Les différents titres à placer
		 */
		protected void init(String[] titres)
		{
			this.setLayout(new GridBagLayout());
			this.add(textIntro, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
			        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
			                15, 20, 15, 20), 0, 0));
			this.titles = titres;
			returns = new String[titles.length];
		}

		/**
		 * Crée les boutons et leurs actions
		 */
		protected void setButtons()
		{
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
					getData();
					setVisible(false);
				}

			});

			cancelButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					for (int i = 0; i < titles.length; i++)
					{
						returns[i] = "-1";
					}
					setVisible(false);
				}
			});

			this.add(okButton, new GridBagConstraints(0, titles.length + 1, 1, 1,
			        1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
			        new Insets(15, 5, 15, 20), 0, 0));
			this.add(cancelButton, new GridBagConstraints(1, titles.length + 1, 1,
			        1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			        new Insets(15, 20, 15, 5), 0, 0));
		}

		/**
		 * Récupère les données pour les renvoyer en fonctions des éléments affichés.
		 */
		public abstract void getData();

		/**
		 * Affiche la fenêtre
		 * 
		 * @return
		 * 		Les valeurs renvoyées par la fenêtre
		 */
		public String[] showDialog()
		{
			this.setVisible(true);

			return returns;
		}

		/**
		 * Modifie le texte d'introduction. Ce texte est affiché avant les
		 * textFields.
		 * 
		 * @param texte
		 *        Le nouveau texte d'introduction
		 */
		public void setTextIntro(String texte)
		{
			textIntro.setText(texte);
			pack();
		}

		/**
		 * Modifie le texte du bouton de validation. Ce bouton renvoie les valeurs
		 * rentrées dans les champs.
		 * 
		 * @param texte
		 *        Le nouveau texte du bouton
		 */
		public void setTextOkButton(String texte)
		{
			okButton.setText(texte);
			pack();
		}
		
		/**
		 * Mets en place les groupes. Pas encore bien géré.
		 * @param groupes
		 */
		public void setGroupes(int[] groupes)
		{
			this.groupes = groupes;
		}
		
		/**
		 * Modifie le texte du bouton d'annulation. Ce bouton renvoie des champs
		 * vides.
		 * 
		 * @param texte
		 *        Le nouveau texte du bouton
		 */
		public void setTextCancelButton(String texte)
		{
			cancelButton.setText(texte);
			pack();
		}

		/**
		 * Renvoie le nombre total de groupe
		 * 
		 * @return
		 * 		Le nombre total de groupe
		 */
		protected int getGroupesNumber()
		{
			int nbrGrp = 0;
			for(int i = 0 ; i < groupes.length ; i++)
			{
				if(groupes[i] > nbrGrp)
				{
					nbrGrp = groupes[i];
				}
			}
			
			return nbrGrp;
		}
}
