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
    implements ChangeListener {
    
    private Melody melody = new Melody();
    JLabel numNotesLabel = new JLabel("Note Count: " + melody.size());
    private double timeTotal = 0;
	 JLabel lenMelodyLabel = new JLabel("Length: " + timeTotal + " s"); 
    /**
       creates the GUI, calling BasicGuiForSynth's go method first
    */    
    public void go(JFrame frame) {
	super.go(frame);
		
	// entire melody area JPanel
	JPanel melodyGrid = new JPanel();
	melodyGrid.setLayout(new GridLayout(3,1));

	// Melody
	JPanel topRow = new JPanel();
	topRow.setLayout(new GridLayout(1,2));

	// row of buttons on bottom of GUI (not in melody side)
	JPanel buttonRow = new JPanel();	
	buttonRow.setLayout(new GridLayout(1,3));
	
	// note count and time length
	JPanel botMelodyGrid = new JPanel();	
	botMelodyGrid.setLayout(new GridLayout(1,2));
	
	// save button / clear button
	JPanel midRowButtons = new JPanel();	
	midRowButtons.setLayout(new GridLayout(1,2));
	
	JLabel noteLabel = new JLabel("Melody", JLabel.CENTER);
	topRow.add(noteLabel);
	melodyGrid.add(topRow);
	
	JButton melodyButton = new JButton("Play Melody!");
	melodyButton.addActionListener(new melodyButtonListener());
	buttonRow.add(super.playButton);
	buttonRow.add(super.randomizer);
	buttonRow.add(melodyButton);
	
	JButton saveButton = new JButton("Save Note");
	saveButton.addActionListener(new saveNoteListener());
	 
	botMelodyGrid.add(numNotesLabel);
	botMelodyGrid.add(lenMelodyLabel);

	JButton clearArray = new JButton("Clear All Notes");
	clearArray.addActionListener(new clearNoteListener());
	midRowButtons.add(saveButton);
	midRowButtons.add(clearArray);
	melodyGrid.add(midRowButtons);
	melodyGrid.add(botMelodyGrid);

	frame.add(buttonRow, BorderLayout.SOUTH);
	frame.add(melodyGrid, BorderLayout.EAST);

	frame.setVisible(true);
    }
	
	
    /**
       inner class button listener to melodyButton
    */
    private class melodyButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    
	    AudioFormat f = new AudioFormat(44100,8,1,true,true);
	    
	    try {
		SourceDataLine d = AudioSystem.getSourceDataLine(f);
		ADSREnvelopedContinuousSound envCont = getNote(d);
		ADSREnvelope env = 
		    new ADSREnvelope(envCont.getAttackTime(),
				     envCont.getDecayTime(),
				     envCont.getSustainAmplitude(),
				     envCont.getReleaseTime(),
				     envCont.getSustainTime());
		melody.play(env,melody);		    
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}	
    }

    /**
       inner class button listener for clearing all notes
    */
    private class clearNoteListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    melody.clear();
	    numNotesLabel.setText("Note Count: " + melody.size());
		 timeTotal = 0;
		 lenMelodyLabel.setText("Length: " + timeTotal + "s");
	}
    }

    /**
       inner class button listener for saving a note
    */
    private class saveNoteListener implements ActionListener {

	/**
	   actionPerformed event for saving a note
	   @param e the ActionEvent
	*/
	public void actionPerformed(ActionEvent e) {
	    
	    AudioFormat f = new AudioFormat(44100,8,1,true,true);
	    try {
		SourceDataLine d = AudioSystem.getSourceDataLine(f);
		ADSREnvelopedContinuousSound env = getNote(d);
		
		double time = (env.getAttackTime() + env.getDecayTime() +
				  env.getReleaseTime() + env.getSustainTime());
		time = Math.round(time*100.0)/100.0;
		timeTotal += time;
		timeTotal = Math.round(timeTotal*100.0)/100.0;
		Note n = new Note(env.getFrequency(),
				  time, env.getAmplitude());
		melody.add(n);
	        numNotesLabel.setText("Note Count: " + melody.size());
		lenMelodyLabel.setText("Length: " + timeTotal + " s");
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
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
