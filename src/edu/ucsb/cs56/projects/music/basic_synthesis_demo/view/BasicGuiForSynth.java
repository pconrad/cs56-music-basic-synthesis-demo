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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField field_freq = new JTextField("Enter the frequency (0 < 1000)",4);
		//field_freq.addActionListener(new TextFieldListener());
		
		/*
		amp
		attack
		decay
		sus_amp
		sus_time
		release*/		

	    //JButton test_button = new JButton("hello this is a test");

	    
	    
	    frame.getContentPane().add(BorderLayout.NORTH,field_freq);
	    frame.setSize(1000,400);
	    frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		BasicGuiForSynth synthGUI = new BasicGuiForSynth();
		synthGUI.go();
	}
}
