package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import java.util.*;
import java.lang.Math;

/**
*    A Class that maps Note names to their numerical frequencies  
*    
*   @author Bronwyn Perry-Huston
*   @version CS56 S13  
*/
public class FrequencyMap{
	
	private HashMap map;
	
	/**
    * no-arg constructor to create the mapping of Scientific name to key number 
    */
	public FrequencyMap(){
		map = new HashMap();
		double key = 4;
		for(int octave = 1; octave <= 7; octave++)
		{
			for(int note = 1; note <= 12; note++)
		    {
				switch(note){
				case 1:  map.put("C"+octave, key);
						 break;
			
				case 2:	 map.put("C#"+octave, key);
				         map.put("Db"+octave, key);
				         break;

				case 3:  map.put("D"+octave, key);
				         break;

				case 4:  map.put("D#"+octave, key);
				         map.put("Eb"+octave, key);
				         break;

				case 5:  map.put("E"+octave, key);
				         break;
				    
				case 6:  map.put("F"+octave, key);
				         break;

				case 7:  map.put("F#"+octave, key);
				         map.put("Gb"+octave, key);
				         break;

				case 8:  map.put("G"+octave, key);
				         break;

				case 9:  map.put("G#"+octave, key);
				         map.put("Ab"+octave, key);
				         break;

				case 10: map.put("A"+octave, key);
				         break;

				case 11: map.put("A#"+octave, key);
				         map.put("Bb"+octave, key);
				    	 break;

				case 12: map.put("B"+octave, key);
				    	 break;
				}
				key++;
			}
		}
	}
	/**
    * getter method to access and return the frequency of a note 
    */
	public double getFreq(String note)
	{
		double keyNum = ((Double)map.get(note)).doubleValue();
		double freq = (Math.pow(2.0, ((keyNum-49)/12.0))) * 440;
		System.out.println(note + ": " + freq);
		return freq;
	}
	public double getFreq(double midi)
	{
		double keyNum = midi - 20;
		double freq = (Math.pow(2.0, ((keyNum-49)/12.0))) * 440;
		System.out.println(note + ": " + freq);
		return freq;
	}
}
