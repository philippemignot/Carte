import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings({"serial"})
public class InputDialog extends AbstractDialog
{
	private String[] defaults;
	private JTextField[] textFields;

	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres)
	{
		super(parent, title, modal, titres);
		setElements();
	}
	
	public InputDialog(JFrame parent, String title, boolean modal,
	        String[] titres, int[] groupes)
	{
		super(parent, title, modal, titres, groupes);
		setElements();
	}

	public void setDefaults(String[] def)
	{
		this.defaults = def;
		for (int i = 0; i < Math.min(titles.length, defaults.length); i++)
		{
			textFields[i].setText(defaults[i]);
		}
	}

	@Override
	public void getData()
	{
		for (int i = 0; i < titles.length; i++)
		{
			returns[i] = textFields[i].getText();
		}
	}

	@Override
    protected void setElements()
    {
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
}
