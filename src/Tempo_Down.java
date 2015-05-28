import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Tempo_Down implements ActionListener{
	public void actionPerformed (ActionEvent a)
	{
		float tempoFactor = buildGUI.m.sequencer.getTempoFactor();
		buildGUI.set_tempo( 
				( (double) (tempoFactor * 0.97)));
		
		buildGUI.Modify_Tempo();
	}
}
