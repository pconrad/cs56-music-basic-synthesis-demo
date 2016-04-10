package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import java.util.*;
import java.lang.Math;

/**
 *    A Class that maps Note names to their numerical frequencies  
 *    
 *   @author Bronwyn Perry-Huston
 *   @version CS56 S13  
 */
public class FrequencyMap {
	
    private HashMap<String,Integer> map;
    
    /**
     * no-arg constructor to create the mapping of Scientific name to key number 
     */
    public FrequencyMap() {
	map = new HashMap<String,Integer>();

	map.put("Cb",59);
	map.put("C",60);
	map.put("C#",61);map.put("Db",61);
	map.put("D",62);
	map.put("D#",63);map.put("Eb",63);
	map.put("E",64);map.put("Fb",64);
	map.put("E#",65);map.put("F",65);
	map.put("F#",66);map.put("Gb",66);
	map.put("G",67);
	map.put("G#",68);map.put("Ab",68);
	map.put("A",69);
	map.put("A#",70);map.put("Bb",70);
	map.put("B",71);
	map.put("B#",72);
	
	int key = 4;
	for(int octave = 1; octave <= 7; octave++) {
	    for(int note = 1; note <= 12; note++) {
		switch(note){
		case 1:
		    map.put("B#"+(octave-1), key);
		    map.put("C"+octave, key);
		    break;
		case 2:
		    map.put("C#"+octave, key);
		    map.put("Db"+octave, key);
		    break;
		case 3:
		    map.put("D"+octave, key);
		    break;
		case 4:
		    map.put("D#"+octave, key);
		    map.put("Eb"+octave, key);
		    break;
		case 5:
		    map.put("E"+octave, key);
		    map.put("Fb"+octave, key);
		    break;
		case 6:
		    map.put("E#"+octave, key);
		    map.put("F"+octave, key);
		    break;
		case 7:
		    map.put("F#"+octave, key);
		    map.put("Gb"+octave, key);
		    break;
		case 8:
		    map.put("G"+octave, key);
		    break;
		case 9:
		    map.put("G#"+octave, key);
		    map.put("Ab"+octave, key);
		    break;
		case 10:
		    map.put("A"+octave, key);
		    break;
		case 11:
		    map.put("A#"+octave, key);
		    map.put("Bb"+octave, key);
		    break;
		case 12:
		    map.put("B"+octave, key);
		    map.put("Cb"+(octave+1), key);
		    break;
		}
		key++;
	    }
	}
    }
    
    /**
     * getter method to access and return the frequency of a note 
     */
    public double getFreq(String note) {
	int keyNum = 0;
	try {
	    keyNum = map.get(note);
	} catch (Exception e) {
	    System.err.println("Error: unknown note: " + note + " passed to getFreq");
	    System.exit(4);
	}
	double freq = (Math.pow(2.0, ((keyNum-49)/12.0))) * 440;
	return freq;
    }
    
    public double getFreq(double midi) {
	double keyNum = midi - 20;
	double freq = (Math.pow(2.0, ((keyNum-49)/12.0))) * 440;
	return freq;
    }
}
