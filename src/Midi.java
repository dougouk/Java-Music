import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JToggleButton;


public class Midi {
	
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	
	
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	
	public void setUpMidi()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track=sequence.createTrack();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void buildTrackAndStart()
	{
		int[] trackList = null;
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		
		for (int i = 0; i < 16; i++)
		{
			trackList = new int[16];
			
			int key = instruments[i];
			
			for (int j = 0; j < 16; j++)
			{
				JToggleButton jc = (JToggleButton) buildGUI.checkBoxList.get (j + (16*i) );
				if (jc.isSelected())
				{
//					jc.setContentAreaFilled(false);
//					jc.setOpaque(true);
//					jc.setBackground(Color.GREEN);
					trackList[j] = key;
				}
				else
				{
					trackList[j] = 0;
				}
			}
			
			makeTrack(trackList);
			track.add(makeEvent(176,1,127,0,16));
			
		}
		
		try
		{
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(120);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		}
		
		catch ( Exception ex)
		{ ex.printStackTrace(); }
	}
	
	public void makeTrack (int[] list)
	{
		for (int i = 0; i < 16; i++)
		{
			int key = list[i];
			
			if (key != 0)
			{
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
	
	}
	
	public MidiEvent makeEvent (int status, int channel, int note, int velocity, int tick)
	{
		MidiEvent event = null;
		try
		{
			ShortMessage a = new ShortMessage();
			a.setMessage (status, channel, note, velocity);
			event = new MidiEvent(a, tick);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return event;
	}
}
