package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
   @TODO add a tool for inserting keybindgs/keyboard mode: in keybinding, 
   certain keys are set to bind to a set of params, with keyboard mode
   a set of keys isused as a keyboard for a make a frame with five input
   boxes plus a keylistner bound to the space character
	
	*****************************
	*atk time		    *
	*decay time	       	    *
	*release time		    *
	*atk vol(0-1)		    *
	*sustain vol(0-1)	    *
	*****************************
	use JLabel and JTextBox then add a keyboardlistner--on keypress start 
	the loader thread on release intterupt the sustain thread literal on key
*/

public class BasicGuiForSynth {
	public void go() {
	    JFrame frame = new JFrame();
	    JPanel textFields = new JPanel();
	    JPanel sliders = new JPanel();
	    JPanel center = new JPanel();
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JTextField field_freq = 
		new JTextField("Enter\nfrequency\n(0 - 1000)");
	    JTextField field_amp = 
		new JTextField("Enter the amplitude (0 - 1.0)");
	    JTextField field_attack = 
		new JTextField("Enter the attack (0 - 1.0)");
	    JTextField field_decay = 
		new JTextField("Enter the decay (0 - 1.0)");
	    JTextField field_sustainAmp = 
		new JTextField("Enter the sustain amplitude (0 - 1.0)");
	    JTextField field_sustainTime = 
		new JTextField("Enter the sustain time (0 - 1.0)");
	    JTextField field_release = 
		new JTextField("Enter the release (0 - 1.0)");

	    //JButton test_button = new JButton("hello this is a test");

	    center.setLayout(new GridLayout(2,1));

	    textFields.setLayout(new GridLayout(1, 7));
	    textFields.add(field_freq);
	    textFields.add(field_amp);
	    textFields.add(field_attack);
	    textFields.add(field_decay);
	    textFields.add(field_sustainAmp);
	    textFields.add(field_sustainTime);
	    textFields.add(field_release);
	    
	    center.add(textFields);

	    frame.add(center,BorderLayout.CENTER);
	    frame.add(new JButton("Play Sound!"),BorderLayout.SOUTH);
	    frame.setSize(1000,400);
	    frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		BasicGuiForSynth synthGUI = new BasicGuiForSynth();
		synthGUI.go();
	}
}
