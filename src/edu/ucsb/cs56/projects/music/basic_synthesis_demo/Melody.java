package edu.ucsb.cs56.projects.music.basic_synthesis_demo;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;

/**
*    A Class that represents a Melody.   
*    
*   @author Bronwyn Perry-Huston
*   @version CS56 S13 for project 1 
*/

public class Melody extends ArrayList<Note>{


	/**
    * no-arg constructor returns an empty melody 
    */
	public Melody(){
		//call constructor for ArrayList<Note>, with capacity 1
		super(1);
	}

	/**
	* void method to play the melody
	* @param e the ADSREnvelope that is used to play the note
	* @param m the melody to be played
	*/
	public void play(ADSREnvelope a, Melody m)
	{

		double freq, amp;

		AudioFormat f= new AudioFormat(44100,8,1,true,true);

		try{
			SourceDataLine d = AudioSystem.getSourceDataLine(f);

			d.open(f, 44100*2);
		    d.start();
		
			for(Note n: m)
			{
					freq = n.getFrequency();
					amp = n.getVolume();
					ADSREnvelopedContinuousSound env =
							new ADSREnvelopedContinuousSound(freq, amp, a, 44100, d);
					env.load();
					System.out.printf("Freq = %f \n", freq);

			}

			d.drain();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	* Main method that will play the melody
	* Takes in command line arguments to create ADSREnvelope
	*/
	public static void main(String[] args){

		if(args.length!=5){
	        System.out.println("attack, decay, sustain amp, sustain time, release");
	        System.exit(1);
	    }


		double attackTime = Double.parseDouble(args[0]);
		double decayTime = Double.parseDouble(args[1]);
		double sustainAmplitude = Double.parseDouble(args[2]);
		double releaseTime = Double.parseDouble(args[3]);
		double sustainTime = Double.parseDouble(args[4]);

		ADSREnvelope a = new ADSREnvelope(attackTime,
						            		  decayTime,
						           		  sustainAmplitude,
						            		  releaseTime,
		                            		  sustainTime);

		
		Melody m = new Melody();
		m.add(new Note(220.0, 1, 0.9));
		m.add(new Note(330.0, 1, 0.9));
		m.play(a, m);
	}
	



}
