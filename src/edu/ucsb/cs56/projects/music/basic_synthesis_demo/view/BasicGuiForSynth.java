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
	    frame.getContentPane().setLayout(new GridLayout(2,1));
	    JPanel p1 = new JPanel();
	    JPanel p2 = new JPanel();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JTextField field_freq = 
		new JTextField("Enter the frequency (0 - 1000)");
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

	    p1.setLayout(new GridLayout(1, 7));
	    p1.add(field_freq);
	    p1.add(field_amp);
	    p1.add(field_attack);
	    p1.add(field_decay);
	    p1.add(field_sustainAmp);
	    p1.add(field_sustainTime);
	    p1.add(field_release);
	    
	    frame.add(p1);
	    frame.setSize(1000,400);
	    frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		BasicGuiForSynth synthGUI = new BasicGuiForSynth();
		synthGUI.go();
	}
}
