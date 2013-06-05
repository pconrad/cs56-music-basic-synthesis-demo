package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
   creates a new thread that consistently drains the audioData [] byte array,
   causing the note to play
*/	
public class AudioDrain implements Runnable {

    SourceDataLine runMe;

    public AudioDrain(SourceDataLine inputData) {
	runMe = inputData;
    }

    public void run() {
	try {
	    runMe.drain();
	} catch(Exception ex) {
	    ex.printStackTrace();
	}
    }
}
	
