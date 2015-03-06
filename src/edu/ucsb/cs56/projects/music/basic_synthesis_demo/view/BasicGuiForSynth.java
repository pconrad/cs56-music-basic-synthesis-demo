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

   A basic gui for the music sythesis program.
   Allows editing of the parameters with JTextFields and JSliders.
   There is a play sound button that plays the sound with the parameters set.
*/
public class BasicGuiForSynth implements ChangeListener {
    public JFormattedTextField field_freq = 
	new JFormattedTextField("220");
    public JFormattedTextField field_amp = 
	new JFormattedTextField("0.9");
    public JFormattedTextField field_attack = 
	new JFormattedTextField("0.1");
    public JFormattedTextField field_decay = 
	new JFormattedTextField("0.2");
    public JFormattedTextField field_sustainAmp = 
	new JFormattedTextField("0.6");
    public JFormattedTextField field_sustainTime = 
	new JFormattedTextField("1.0");
    public JFormattedTextField field_release = 
	new JFormattedTextField("0.2");
    public JFormattedTextField field_volume = 
	new JFormattedTextField("100");

    protected JButton playButton = new JButton("Play Sound!");
    protected JButton randomizer = new JButton("Random Note!");
    protected JFrame mFrame = null;
    public class Graph extends JPanel {
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		int width = getWidth();
		int height = getHeight();
		double attack = (Double.parseDouble(field_attack.getText()));
		double amp = (1-Double.parseDouble(field_amp.getText()));
		double decay = Double.parseDouble(field_decay.getText());
		double sustainAmp =( 1-Double.parseDouble(field_sustainAmp.getText()));
		double sustainTime = Double.parseDouble(field_sustainTime.getText());
		double release = Double.parseDouble(field_release.getText());
		
		double totalTime = attack + decay + sustainTime + release;
		
 		// draw horizontal home 			
	        for ( double i = 0 ; i < totalTime ; i+=0.1 )
		    {		  
	         	g.drawLine( ( int) ( i*width/totalTime) , 0 ,(int) ( i*width /totalTime) , (int)( height) );
			}

		// draw vertical line
		for( int  i =0 ; i < 10 ; i++)
	           {
			g.drawLine(0,(int)(  i*height/10)  , (int) (width) ,(int) ( i*height/10)   );
	       	   }
		
		int nAttack = (int) ( attack/totalTime* width );
		int nDecay = (int) ( decay/ totalTime *width);
		int nSustainTime =( int) ( sustainTime/ totalTime * width );
		int nRelease = (int) ( release/ totalTime *width );

		g2.setStroke(new BasicStroke (5));
		g2.drawLine(0,height,nAttack,(int) (amp*height) );
		g2.drawLine( nAttack, (int)( amp* height) , nAttack + nDecay, (int) (sustainAmp * height));
		g2.drawLine( nAttack + nDecay , (int)(sustainAmp*height) , nAttack+ nDecay+ nSustainTime, (int)(sustainAmp* height)  );
		g2.drawLine( nAttack+nDecay+nSustainTime,(int)(sustainAmp*height) , nAttack+nDecay+nSustainTime+nRelease,height);
		
		g.drawString("Attack:"+ attack , nAttack/2 , 25 );
		g.drawString("Decay:"+ decay , nAttack + nDecay/2 , 25 );
		g.drawString("Sustain:"+ sustainTime , nAttack + nDecay + nSustainTime/2 , 25 );
		g.drawString("Release:" + release , nAttack + nDecay + nSustainTime + nRelease/2  , 25);}

    }
    /**
       creates the GUI
    */
    public void go(JFrame frame) {
	mFrame = frame; 
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	    
	
	boolean isBasic = true;
	if (this instanceof MelodyGui)
	    isBasic = false;		
	JPanel labels = new JPanel();
	JPanel textFields = new JPanel();
	JPanel sliders = new JPanel();
	JPanel center = new JPanel();
	Graph graph = new Graph();
	// create JLabels for each parameter
	JLabel label1 = new JLabel("<html>Frequency<br>(0 - 1,000.0)</html>", 
				   JLabel.CENTER);
	JLabel label2 = new JLabel("<html>Amplitude<br>(0 - 1.0)</html>",
				   JLabel.CENTER);
	JLabel label3 = new JLabel("<html>Attack<br>(0 - 1.0)</html>", 
				   JLabel.CENTER);
	JLabel label4 = new JLabel("<html>Decay<br>(0 - 1.0)</html>", 
				   JLabel.CENTER);
	JLabel label5 = new JLabel("<html>Sustain Amp<br>(0 - 1.0)</html>",
				   JLabel.CENTER);
	JLabel label6 = new JLabel("<html>Sustain Time<br>(0 - 1.0)</html>", 
				   JLabel.CENTER);
	JLabel label7 = new JLabel("<html>Release<br>(0 - 1.0)</html>", 
				   JLabel.CENTER);
	JLabel label8 = new JLabel("<html>Volume<br<(0 - 100%)</html>",
				   JLabel.CENTER);

	labels.setLayout(new GridLayout(1,9));
	labels.add(label1);
	labels.add(label2);
	labels.add(label3);
	labels.add(label4);
	labels.add(label5);
	labels.add(label6);
	labels.add(label7); 
	labels.add(label8);

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
	field_volume.setHorizontalAlignment(JTextField.CENTER);

	// create sliders for each parameter
	sliders.setLayout(new GridLayout(1,8));
	
	JSlider slider_freq = new SliderLinked(field_freq);
	slider_freq.setMajorTickSpacing(100);
	slider_freq.setPaintTicks(true);
	slider_freq.addChangeListener(this);
	slider_freq.setMaximum(1000);
	
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

	JSlider slider_volume = new SliderLinked(field_volume);
	slider_volume.setMajorTickSpacing(10);
	slider_volume.setPaintTicks(true);
	slider_volume.addChangeListener(this);
	slider_volume.setMaximum(100);
	slider_volume.setValue(100);
	
	sliders.add(slider_freq);
	sliders.add(slider_amp);
	sliders.add(slider_attack);
	sliders.add(slider_decay);
	sliders.add(slider_susAmp);
	sliders.add(slider_susTime);
	sliders.add(slider_release);
	sliders.add(slider_volume);

	// add everything into the frame using layout managers
	textFields.setLayout(new GridLayout(1, 8));
	textFields.add(field_freq);
	textFields.add(field_amp);
	textFields.add(field_attack);	
	textFields.add(field_decay);
	textFields.add(field_sustainAmp);
	textFields.add(field_sustainTime);
	textFields.add(field_release);
	textFields.add(field_volume);
	playButton.addActionListener(new playNoteListener());
	randomizer.addActionListener(new randomListener());

	center.setLayout(new GridLayout(3,1));

	center.add(labels);
	center.add(textFields);
	center.add(sliders);	

	center.add(playButton);
	frame.add(center,BorderLayout.NORTH);

	if (isBasic) {
	    JPanel row = new JPanel(new GridLayout(1,2));
	    row.add(playButton);
	    row.add(randomizer);
	    frame.add(row,BorderLayout.SOUTH);
	}
	frame.add(graph, BorderLayout.CENTER);
	frame.setSize(1000,500);
	frame.setVisible(true);
	frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    /**
       changes the value of the field parameters based on the slider
       @param e the ChangeEvent
    */
    public void stateChanged(ChangeEvent e) {
	SliderLinked s = (SliderLinked) e.getSource();
	double value = s.getValue();
	if (s.text != field_freq && s.text != field_volume)
	    s.text.setText(String.valueOf(Math.round(value*0.01*100.0)/100.0));
	else if(s.text == field_freq)
	    s.text.setText(String.valueOf(value));
	else
	{
		s.text.setText(String.valueOf(value)); 
		//global volume converted from 0-100 to 0-1 and then multiplied by .95 to get to 0-.95 range
		Global.masterVolume =  Math.round( Double.parseDouble(field_volume.getText() ));
		Global.masterVolume = (Global.masterVolume/100.0)*.95;
		System.out.println("Master Vol: " + Global.masterVolume);
	}
	mFrame.repaint();
	}

        /**
       inner class button listener for randomizing a note
    */
    private class randomListener implements ActionListener {

	/**
	   actionPerformed event for randomizing a note
	   @param e the ActionEvent
	*/
	public void actionPerformed(ActionEvent e) {
	    Random r = new Random();
	    
	    field_amp.setText(String.valueOf(Math.round(r.nextDouble()*
							100.0)/100.0));
	    field_attack.setText(String.valueOf(Math.round(r.nextDouble()*
							   100.0)/100.0));
	    field_decay.setText(String.valueOf(Math.round(r.nextDouble()*
							  100.0)/100.0));
	    field_sustainAmp.setText(String.valueOf(Math.round(r.nextDouble()*
							       100.0)/100.0));
	    field_sustainTime.setText(String.valueOf(Math.round(r.nextDouble()*
								100.0)/100.0));
	    field_release.setText(String.valueOf(Math.round(r.nextDouble()*
							    100.0)/100.0));
	    field_freq.setText(String.valueOf(r.nextInt(1000)+1));	    
	}
    }
    
    /**
       inner class button listener for playing a sound
    */
    private class playNoteListener implements ActionListener {

	/**
	   plays the specified sound given the parameters inputted
	   @param e the ActionEvent
	*/
	public void actionPerformed(ActionEvent e) {
	    AudioFormat f = new AudioFormat(44100,8,1,true,true);
	    try {
		/* Create a new audioLine which goes to the system, 
		   the audio format specifys all the features of the line.
		*/
		SourceDataLine d = AudioSystem.getSourceDataLine(f);

		ADSREnvelopedContinuousSound env = getNote(d);
		d.open(f);
		d.start();
		env.load();
		d.drain();
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }

    /**
       returns a note given the field parameters
       @param d data line to which data may be written
    */

    public ADSREnvelopedContinuousSound getNote(SourceDataLine d) {

	rangeFix(field_freq);
	rangeFix(field_attack);
	rangeFix(field_decay);
	rangeFix(field_sustainAmp);
	rangeFix(field_sustainTime);
	rangeFix(field_release);
	rangeFix(field_volume);

	double freq        =   
	    Math.round(Double.parseDouble(field_freq.getText()));
	double amp         =   
	    Double.parseDouble(field_amp.getText());
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

	return env;
    }

    /**
       ensures that the values entered in the JTextFields are
       within the correct range
       @param text the JTextField or JFormattedTextField to correct
    */
    public <T extends JTextField> void rangeFix(T text){
	try {
	    double value = Double.parseDouble(text.getText());
	    double min = 0;
	    double max;
	    if (text != field_freq && text != field_volume)
			max = 1;
	    else if(text == field_freq)
			max = 10000;
		else
			max = 100;
	    
	    if (value > max)
		value = max;
	    else if (value < min)
		value = min;
	    
	    text.setText(String.valueOf(value));
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    /**
       executes the program so it will run
    */

    public static void main(String[] args) {
	BasicGuiForSynth synthGUI = new BasicGuiForSynth();
	JFrame frame = new JFrame();
	synthGUI.go(frame);
    }
}
