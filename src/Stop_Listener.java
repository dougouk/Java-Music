import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stop_Listener implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			buildGUI.m.sequencer.stop();
		}
	}