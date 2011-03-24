import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
public class RadioDialog extends AbstractDialog
{
	private JRadioButton[] radioButtons;

	public RadioDialog(JFrame parent, String title, boolean modal,
			String[] titres)
	{
		super(parent, title, modal, titres);
		setElements();
	}

	public RadioDialog(JFrame parent, String title, boolean modal,
	        String[] titres, int[] groupes)
	{
		super(parent, title, modal, titres, groupes);
		setElements();
	}
	
	@Override
	public void getData()
	{
		for (int i = 0; i < titles.length; i++)
		{
			if(radioButtons[i].isSelected())
			{
				returns[0] = radioButtons[i].getText();
			}
		}
	}

	@Override
    protected void setElements()
    {
		boolean groupesSet = false;
		ButtonGroup[] bg = new ButtonGroup[1];
		if(groupes.length > 0)
		{
			groupesSet = true;
			bg = new ButtonGroup[getGroupesNumber()];
			for(int i = 0 ; i < bg.length ; i ++)
			{
				bg[i] = new ButtonGroup();
			}
		}
		radioButtons = new JRadioButton[titles.length];
		for (int i = 0; i < titles.length; i++)
		{
			radioButtons[i] = new JRadioButton(titles[i]);
			if(groupesSet)
			{
				bg[groupes[i] - 1].add(radioButtons[i]);
				
			}else
			{
				bg[0] = new ButtonGroup();
				bg[0].add(radioButtons[i]);
			}
			this.add(radioButtons[i], new GridBagConstraints(0, i + 1, 2, 1, 1.0,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 20), 0, 0));
		}
		if(titles.length > 0)
		{
			radioButtons[0].setSelected(true);
		}
    }
}
