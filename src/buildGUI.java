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
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
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


public class buildGUI
{
	JPanel mainPanel;
	JFrame frame;
	static JTextField tempo_textField;
	JLabel tempo_label;
	Box tempo_box;
	
	static ArrayList <JToggleButton> checkBoxList;
	static double current_tempo;
	
	
	static Midi m;
	
	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
			"Open Hi-Hate", "Acoustic Snare", "Crash Cymbal",
			"Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle",
			"Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", 
			"High Agogo", "Open High Conga"};
	
	public void build()
		{
			try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	        }
			
			frame = new JFrame ("Cyber BeatBox");
			frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			BorderLayout layout = new BorderLayout();
			JPanel background = new JPanel(layout);
			JPanel east_panel = new JPanel(layout);
			background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			checkBoxList = new ArrayList<JToggleButton>();
			Box buttonBox = new Box(BoxLayout.Y_AXIS);
			
			JButton start = new JButton ("Start");
			start.addActionListener(new Start_Listener());
			buttonBox.add(start);
			
			JButton stop = new JButton("Stop");
			stop.addActionListener(new Stop_Listener());
			buttonBox.add(stop);
			
			JButton upTempo = new JButton ("Tempo Up");
			upTempo.addActionListener(new Tempo_Up());
			buttonBox.add(upTempo);
			
			JButton downTempo = new JButton ("Tempo down");
			downTempo.addActionListener(new Tempo_Down());
			buttonBox.add(downTempo);
			
			JButton save = new JButton("Save");
			save.addActionListener(new Save());
			buttonBox.add(save);
			
			JButton load = new JButton("Load");
			load.addActionListener (new Load());
			buttonBox.add(load);
			
			JButton clear = new JButton ("Clear");
			clear.addActionListener (new Clear());
			buttonBox.add(clear);
			
			current_tempo = 1.00;
			tempo_label = new JLabel ("Tempo: ");
			tempo_textField = new JTextField (String.format("%.2f", current_tempo));
			tempo_textField.setPreferredSize(new Dimension(6,1));
			tempo_textField.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev)
				{
					current_tempo = Double.parseDouble(ev.getActionCommand());
					tempo_textField.setText(String.format("%.2f" , current_tempo ));
					Modify_Tempo();
				}
			});
			
			buttonBox.add(tempo_textField);
			tempo_box = new Box(BoxLayout.X_AXIS);
			
			tempo_box.add(tempo_label);
			tempo_box.add(tempo_textField);
			Box nameBox = new Box (BoxLayout.Y_AXIS);
			for (int i = 0; i < instrumentNames.length; i++)
			{
				nameBox.add(new Label (instrumentNames[i]));
			}
			east_panel.setLayout(new BoxLayout(east_panel, BoxLayout.Y_AXIS));
			east_panel.add(buttonBox);
			east_panel.add(tempo_box);
			background.add(BorderLayout.EAST, east_panel);
			background.add(BorderLayout.WEST, nameBox);
			
			frame.getContentPane().add(BorderLayout.CENTER, background);
			
			
			
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(16,16));
			background.add(BorderLayout.CENTER, mainPanel);
			
			for (int i = 0; i < 256; i++)
			{
				JToggleButton c = new JToggleButton();
				checkBoxList.add(c);		
				mainPanel.add(c);
			}
			m = new Midi();
			m.setUpMidi();
			
			frame.setBounds(250, 250, 800, 500);
			
			frame.setVisible(true);
		}
	
	public static void set_tempo(double in){
		current_tempo = in;
	}
	
	public static double get_tempo(){
		return current_tempo;
	}
	/*public class Start_Listener implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			m.buildTrackAndStart();
			System.out.println("Pressed: Start");
			
		}
	}
	*/
	/*public class Stop_Listener implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			m.sequencer.stop();
		}
	}*/
	
	public class Tempo_Up implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			float tempoFactor = m.sequencer.getTempoFactor();
			current_tempo = (double) (tempoFactor * 1.03);
			Modify_Tempo();
			tempo_textField.setText(String.format("%.2f" , current_tempo ));
		}
	}
	
	public class Tempo_Down implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			float tempoFactor = m.sequencer.getTempoFactor();
			current_tempo =  (double) (tempoFactor * 0.97);
			Modify_Tempo();
			tempo_textField.setText(String.format("%.2f" , current_tempo ));
		}
	}
	
	public static void Modify_Tempo()
	{
		m.sequencer.setTempoFactor((float) current_tempo);
		tempo_textField.setText(String.format("%.2f" , current_tempo ));
	}
	
	public class Save implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{	
			try
		{
				JFileChooser fc = new JFileChooser();
				if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) 
				{
					Save_File(fc.getSelectedFile());
				}
		}
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Save Implements ActionListener");
			}
		}
	}
	
	public void Save_File(File file)
	{
		BeatBox save_Object;
		try {
			save_Object = new BeatBox();
			save_Object.checkBoxList = checkBoxList;
			
			FileOutputStream fileOutStream = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fileOutStream);
			System.out.println("Before writing");
			os.writeObject(save_Object);
			System.out.println("After writing");
			os.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			
		} catch (IOException e) {
			System.out.println("Cant Save_File");
		}
	}
	
	public class Load implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			try
			{
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(frame);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream (fc.getSelectedFile()));
				Load_Object(ois.readObject());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Cant Load File");
				
			}
		}
	}
	
	public void Load_Object(Object object)
	{
		Clear_Grid();
		checkBoxList.clear();
		mainPanel.removeAll();
		BeatBox load_Object = (BeatBox) object;
		
		System.out.println("Before loading");
		
		ArrayList<JToggleButton> temp = new ArrayList<JToggleButton>();
		temp = load_Object.checkBoxList;
		
		JToggleButton temp_Button;
		for (int i = 0; i < 256; i++)
		{
			temp_Button = temp.get(i);
			if (temp_Button.isSelected())
			{
				System.out.println("Inside If");
				temp_Button.setSelected(true);
			}
			checkBoxList.add(temp_Button);
			mainPanel.add(temp_Button);
		}
		
		System.out.println("After Loading");
	}
	public class Clear implements ActionListener
	{
		public void actionPerformed (ActionEvent a)
		{
			Clear_Grid();
		}
	}
	
	public void Clear_Grid()
	{
		for (JToggleButton jc :  checkBoxList)
		{
			
			jc.setSelected(false);
		}
	}
	
}