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
    
    /**
       creates the GUI, calling BasicGuiForSynth's go method first
    */
    
    public void go() {
	super.go();
    }
    
    /**
       executes the program so it will run
    */

    public static void main(String[] args) {
	MelodyGui melodyGui = new MelodyGui();
	melodyGui.go();
    }
}
