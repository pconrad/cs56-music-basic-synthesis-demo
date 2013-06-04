package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

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

public class BasicGuiForSynth implements ActionListener, ChangeListener {
    JFormattedTextField field_freq = 
	new JFormattedTextField("220");
    JFormattedTextField field_amp = 
	new JFormattedTextField("0.9");
    JFormattedTextField field_attack = 
	new JFormattedTextField("0.1");
    JFormattedTextField field_decay = 
	new JFormattedTextField("0.2");
    JFormattedTextField field_sustainAmp = 
	new JFormattedTextField("0.6");
    JFormattedTextField field_sustainTime = 
	new JFormattedTextField("1.0");
    JFormattedTextField field_release = 
	new JFormattedTextField("0.2");
    
            

    public void go() {
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	    
	
	JPanel labels = new JPanel();
	JPanel textFields = new JPanel();
	JPanel sliders = new JPanel();
	JPanel center = new JPanel();
	
	// create JLabels for each parameter
	JLabel label1 = new JLabel("<html>Frequency<br>(0 - 20,000.0 Hz)</html>", JLabel.CENTER);
	JLabel label2 = new JLabel("<html>Amplitude<br>(0 - 1.0)</html>", JLabel.CENTER);
	JLabel label3 = new JLabel("<html>Attack<br>(0 - 1.0)</html>", JLabel.CENTER);
	JLabel label4 = new JLabel("<html>Decay<br>(0 - 1.0)</html>", JLabel.CENTER);
	JLabel label5 = new JLabel("<html>Sustain Amp<br>(0 - 1.0)</html>",
				   JLabel.CENTER);
	JLabel label6 = new JLabel("<html>Sustain Time<br>(0 - 1.0)</html>", JLabel.CENTER);
	JLabel label7 = new JLabel("<html>Release<br>(0 - 1.0)</html>", JLabel.CENTER);
	
	labels.setLayout(new GridLayout(1,7));
	labels.add(label1);
	labels.add(label2);
	labels.add(label3);
	labels.add(label4);
	labels.add(label5);
	labels.add(label6);
	labels.add(label7); 
	
	// create formatted text fields for each parameter
	NumberFormat f = NumberFormat.getNumberInstance();
	f.format(new Integer(123456789));
	    
	field_freq.setHorizontalAlignment(JTextField.CENTER);
	field_amp.setHorizontalAlignment(JTextField.CENTER);
	field_attack.setHorizontalAlignment(JTextField.CENTER);
	field_decay.setHorizontalAlignment(JTextField.CENTER);
	field_sustainAmp.setHorizontalAlignment(JTextField.CENTER);
	field_sustainTime.setHorizontalAlignment(JTextField.CENTER);
	field_release.setHorizontalAlignment(JTextField.CENTER);
	
	// create sliders for each parameter
	sliders.setLayout(new GridLayout(1,7));
	
	JSlider slider_freq = new SliderLinked(field_freq);
	slider_freq.setMajorTickSpacing(5000);
	slider_freq.setPaintTicks(true);
	slider_freq.addChangeListener(this);
	slider_freq.setMaximum(20000);
	
	JSlider slider_amp = new SliderLinked(field_amp);
	slider_amp.setMajorTickSpacing(10);
	slider_amp.setPaintTicks(true);
	slider_amp.addChangeListener(this);
	slider_amp.setMaximum(100);
	
	JSlider slider_attack = new SliderLinked(field_attack);
	slider_attack.setMajorTickSpacing(10);
	slider_attack.setPaintTicks(true);
	slider_attack.addChangeListener(this);
	slider_attack.setMaximum(100);

	JSlider slider_decay = new SliderLinked(field_decay);
	slider_decay.setMajorTickSpacing(10);
	slider_decay.setPaintTicks(true);
	slider_decay.addChangeListener(this);
	slider_decay.setMaximum(100);
	
	JSlider slider_susAmp = new SliderLinked(field_sustainAmp);
	slider_susAmp.setMajorTickSpacing(10);
	slider_susAmp.setPaintTicks(true);
	slider_susAmp.addChangeListener(this);
	slider_susAmp.setMaximum(100);
	
	JSlider slider_susTime = new SliderLinked(field_sustainTime);
	slider_susTime.setMajorTickSpacing(10);
	slider_susTime.setPaintTicks(true);
	slider_susTime.addChangeListener(this);
	slider_susTime.setMaximum(100);

	JSlider slider_release = new SliderLinked(field_release);
	slider_release.setMajorTickSpacing(10);
	slider_release.setPaintTicks(true);
	slider_release.addChangeListener(this);
	slider_release.setMaximum(100);
	
	sliders.add(slider_freq);
	sliders.add(slider_amp);
	sliders.add(slider_attack);
	sliders.add(slider_decay);
	sliders.add(slider_susAmp);
	sliders.add(slider_susTime);
	sliders.add(slider_release);
	
	// add everything into the frame using layout managers
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
	
	JButton button = new JButton("Play Sound!");
	button.addActionListener(this);
	frame.add(button,BorderLayout.SOUTH);
	frame.setSize(1000,200);
	frame.setVisible(true);
    }
    public void stateChanged(ChangeEvent e) {
	SliderLinked s = (SliderLinked) e.getSource();
	double value = s.getValue();
	if (s.text != field_freq)
	    s.text.setText(String.valueOf(Math.round(value*0.01*100.0)/100.0));
	else
	    s.text.setText(String.valueOf(value));
    }
    public void actionPerformed(ActionEvent e) {
     	AudioFormat f = new AudioFormat(44100,8,1,true,true);
    	try {
    	    /* Create a new audioLine which goes to the system, 
     	       the audio format specifys all the features of the line.
   	    */
     	    SourceDataLine d = AudioSystem.getSourceDataLine(f);

	    rangeFix(field_freq);
	    rangeFix(field_attack);
	    rangeFix(field_decay);
	    rangeFix(field_sustainAmp);
	    rangeFix(field_sustainTime);
	    rangeFix(field_release);

	    double freq        =   
		Math.round(Double.parseDouble(field_freq.getText()));
	    double amp         =   Double.parseDouble(field_amp.getText());
	    double attack      =   
		Double.parseDouble(field_attack.getText());
	    double decay       =   
		Double.parseDouble(field_decay.getText());
	    double sustainAmp  =   
		Double.parseDouble(field_sustainAmp.getText());
	    double sustainTime = 
		Double.parseDouble(field_sustainTime.getText());
	    double release     = 
		Double.parseDouble(field_release.getText());
	    
	    ADSREnvelopedContinuousSound env =
		new ADSREnvelopedContinuousSound(freq,amp,attack,decay,
						 sustainAmp,sustainTime,
						 release,
						 44100,d);
	    d.open(f);
	    d.start();
	    env.load();
	    d.drain();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public <T extends JTextField> void rangeFix(T text){
	try {
	    double value = Double.parseDouble(text.getText());
	    double min = 0;
	    double max;
	    if (text != field_freq)
		max = 1;
	    else
	    max = 10000;
	    
	    if (value > max)
		value = max;
	    else if (value < min)
		value = min;
	    
	    text.setText(String.valueOf(value));
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    public static void main(String[] args) {
	BasicGuiForSynth synthGUI = new BasicGuiForSynth();
	synthGUI.go();
    }
}
