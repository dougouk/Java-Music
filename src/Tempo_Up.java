import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tempo_Up implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			float tempoFactor = buildGUI.m.sequencer.getTempoFactor();
			double current_tempo;
			buildGUI.set_tempo( 
					( current_tempo = (double) (tempoFactor * 1.03)));
			
			buildGUI.Modify_Tempo();
		}
	}