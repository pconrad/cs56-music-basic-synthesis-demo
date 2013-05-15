package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.text.*;
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
	    
	    JPanel labels = new JPanel();
	    JPanel textFields = new JPanel();
	    JPanel sliders = new JPanel();
	    JPanel center = new JPanel();
	    
	    JLabel label1 = new JLabel("Frequency (0-1000)", JLabel.CENTER);
	    JLabel label2 = new JLabel("Amplitude (0-1)", JLabel.CENTER);
	    JLabel label3 = new JLabel("Attack (0-1)", JLabel.CENTER);
	    JLabel label4 = new JLabel("Decay (0-1)", JLabel.CENTER);
	    JLabel label5 = new JLabel("Sustain Amp (0-1)",
				       JLabel.CENTER);
	    JLabel label6 = new JLabel("Sustain Time (0-1)", JLabel.CENTER);
	    JLabel label7 = new JLabel("Release (0-1)", JLabel.CENTER);

	    labels.setLayout(new GridLayout(1,7));
	    labels.add(label1);
	    labels.add(label2);
	    labels.add(label3);
	    labels.add(label4);
	    labels.add(label5);
	    labels.add(label6);
	    labels.add(label7);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    NumberFormat f = NumberFormat.getNumberInstance();
	    f.format(new Integer(123456789));
	    JFormattedTextField field_freq = 
		new JFormattedTextField("220");
	    field_freq.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_amp = 
		new JFormattedTextField("0.9");
	    field_amp.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_attack = 
		new JFormattedTextField("0.1");
	    field_attack.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_decay = 
		new JFormattedTextField("0.2");
	    field_decay.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_sustainAmp = 
		new JFormattedTextField("0.6");
	    field_sustainAmp.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_sustainTime = 
		new JFormattedTextField("1.0");
	    field_sustainTime.setHorizontalAlignment(JTextField.CENTER);
	    JFormattedTextField field_release = 
		new JFormattedTextField("0.2");
	    field_release.setHorizontalAlignment(JTextField.CENTER);

	    sliders.setLayout(new GridLayout(1,7));

	    JSlider slider_freq = new JSlider();
	    slider_freq.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_freq.setPaintTicks(true);
	    slider_freq.setPaintLabels(true);

	    JSlider slider_amp = new JSlider();
	    slider_amp.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_amp.setPaintTicks(true);
	    slider_amp.setPaintLabels(true);

	    JSlider slider_attack = new JSlider();
	    slider_attack.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_attack.setPaintTicks(true);
	    slider_attack.setPaintLabels(true);

	    JSlider slider_decay = new JSlider();
	    slider_decay.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_decay.setPaintTicks(true);
	    slider_decay.setPaintLabels(true);

	    JSlider slider_susAmp = new JSlider();
	    slider_susAmp.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_susAmp.setPaintTicks(true);
	    slider_susAmp.setPaintLabels(true);

	    JSlider slider_susTime = new JSlider();
	    slider_susTime.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_susTime.setPaintTicks(true);
	    slider_susTime.setPaintLabels(true);
	    
	    JSlider slider_release = new JSlider();
	    slider_release.setMajorTickSpacing(20);
	    //slider_freq.setMinorTickSpacing(10);
	    slider_release.setPaintTicks(true);
	    slider_release.setPaintLabels(true);

	    sliders.add(slider_freq);
	    sliders.add(slider_amp);
	    sliders.add(slider_attack);
	    sliders.add(slider_decay);
	    sliders.add(slider_susAmp);
	    sliders.add(slider_susTime);
	    sliders.add(slider_release);

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
	    center.add(sliders);
 
	    frame.add(labels,BorderLayout.NORTH);
	    frame.add(center,BorderLayout.CENTER);
	    frame.add(new JButton("Play Sound!"),BorderLayout.SOUTH);
	    frame.setSize(1000,200);
	    frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		BasicGuiForSynth synthGUI = new BasicGuiForSynth();
		synthGUI.go();
	}
}
