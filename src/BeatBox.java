
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BeatBox implements Serializable{
	JPanel mainPanel;
	ArrayList <JToggleButton> checkBoxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame frame;
	double current_tempo;
	JTextField tempo_textField;
	boolean[] selected_array;
	Box tempo_box;
	JLabel tempo_label;
	
	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
			"Open Hi-Hate", "Acoustic Snare", "Crash Cymbal",
			"Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle",
			"Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conga"};
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	final static long serialVersionUID = 30293948;
	
	public static void main (String[] args)
	{
		buildGUI one = new buildGUI();
		one.build();
	}
	
	
	
	
		
	
	
}
