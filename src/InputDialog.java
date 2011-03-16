import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class InputDialog extends JDialog
{
	private String[] titles;
	private String[] defaults;
	private String[] returns;
	private JTextField[] textFields;
	private JButton okButton = new JButton("Ok");
	private JButton cancelButton = new JButton("Annuler");
	private JLabel textIntro = new JLabel("Veuillez entrer les paramètres :");

	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres)
	{
		super(parent, title, modal);
		this.setLayout(new GridBagLayout());
		setTitles(titres);
		setButtons();
		// this.setSize(200, 80);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres, String[] defaults)
	{
		super(parent, title, modal);
		this.setLayout(new GridBagLayout());
		setTitles(titres);
		setDefauts(defaults);
		setButtons();
		// this.setSize(200, 80);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	private void setTitles(String[] titres)
	{
		this.add(textIntro, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
		        GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(
		                15, 20, 15, 20), 0, 0));
		this.titles = titres;
		returns = new String[titles.length];
		JLabel[] labels = new JLabel[titles.length];
		textFields = new JTextField[titles.length];
		for (int i = 0; i < titles.length; i++)
		{
			labels[i] = new JLabel(titles[i]);
			textFields[i] = new JTextField(8);

			this.add(labels[i], new GridBagConstraints(0, i + 1, 1, 1, 1.0,
			        0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			        new Insets(5, 20, 5, 10), 0, 0));
			this.add(textFields[i], new GridBagConstraints(1, i + 1, 1, 1, 1.0,
			        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
			        new Insets(5, 10, 5, 20), 0, 0));
		}
	}

	private void setDefauts(String[] def)
	{
		this.defaults = def;
		for (int i = 0; i < Math.min(titles.length, defaults.length); i++)
		{
			textFields[i].setText(defaults[i]);
		}

	}

	@SuppressWarnings("serial")
	private void setButtons()
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

		okButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (int i = 0; i < titles.length; i++)
				{
					returns[i] = textFields[i].getText();
				}

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
					returns[i] = "";
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
}
