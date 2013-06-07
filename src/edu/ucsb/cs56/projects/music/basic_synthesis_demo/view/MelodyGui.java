package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code.*;
import java.lang.Math;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
   @author Chris Atanasian
   @author Marc Lindsay

   A melody gui for the music sythesis program.
   Allows editing of the parameters with JTextFields and JSliders.
   There is a play sound button that plays the sound with the parameters set.
   There is a button that adds the note to a list.
   There is a button that plays the notes in the list as a melody.
*/

public class MelodyGui extends BasicGuiForSynth
    implements ActionListener, ChangeListener {
    
    private Melody melody = new Melody(); 
    private JFormattedTextField melodyLength = 
	new JFormattedTextField("set melody length");
    
    /**
       creates the GUI, calling BasicGuiForSynth's go method first
    */    
    public void go(JFrame frame) {
	super.go(frame);
	
	melodyLength.setHorizontalAlignment(JTextField.CENTER);

	JPanel topRowGrid = new JPanel();
	topRowGrid.setLayout(new GridLayout(1,2));

	JPanel buttonRow = new JPanel();	
	buttonRow.setLayout(new GridLayout(1,2));
		
	JPanel melodyGrid = new JPanel();
	melodyGrid.setLayout(new GridLayout(3,1));
	
	JPanel botRowGrid = new JPanel();	
	botRowGrid.setLayout(new GridLayout(1,2));
	
	JProgressBar pBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 5);

	JLabel noteLabel = new JLabel("Melody", JLabel.CENTER);
	topRowGrid.add(noteLabel);
	topRowGrid.add(melodyLength);
	melodyGrid.add(topRowGrid);
	
	JButton melodyButton = new JButton("Play Melody!");
	buttonRow.add(super.playButton);
	buttonRow.add(melodyButton);

	JButton saveButton = new JButton("Save Note");
	JButton clearArray = new JButton("Clear All Notes");
	botRowGrid.add(saveButton);
	botRowGrid.add(clearArray);
	melodyGrid.add(botRowGrid);
	melodyGrid.add(pBar);

	frame.add(buttonRow, BorderLayout.SOUTH);
	frame.add(melodyGrid, BorderLayout.EAST);

	frame.setVisible(true);
    }
    
    /**
       executes the program so it will run
    */

    public static void main(String[] args) {
	MelodyGui melodyGui = new MelodyGui();
	JFrame frame = new JFrame();
	melodyGui.go(frame);
    }
}
