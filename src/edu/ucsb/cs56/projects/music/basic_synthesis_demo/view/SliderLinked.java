package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
   @author: Chris Atanasian and Marc Lindsay
   @author: 6/02/2013

   A JSlider subclass that is linked with a TextField
*/

public class SliderLinked extends JSlider {
    
    JTextField text;
    
    public <T extends JTextField> SliderLinked(T text) {
	super();
	this.text = text;
    }

}
