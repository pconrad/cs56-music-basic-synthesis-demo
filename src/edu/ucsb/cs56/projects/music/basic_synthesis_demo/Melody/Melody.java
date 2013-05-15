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
    * method to check if the durations for all the notes in the melody are compatible with the envelope being used
    */
    public boolean checkCompatibility(ADSREnvelope a, Melody m){
        double note_duration = 0;
        for(Note n : m)
        {
            note_duration = n.getDuration() - a.getAttackTime() - a.getDecayTime() - a.getReleaseTime();
            if(note_duration < 0)
            {
                return false;
            }   

        }

        return true;
    }

	/**
	* void method to play the melody
	* @param e the ADSREnvelope that is used to play the note
	* @param m the melody to be played
	*/
	public void play(ADSREnvelope a, Melody m)
	{

		double freq, amp;
		double sustainamp = a.getSustainAmplitude();

		AudioFormat f= new AudioFormat(44100,8,1,true,true);

		try{
			SourceDataLine d = AudioSystem.getSourceDataLine(f);

			d.open(f, 44100*2);
		    d.start();
		
			for(int i =0 ; i<m.size() ; i++)
			{
                    Note n = m.get(i);
                    
					freq = n.getFrequency();          					
                    amp = n.getVolume();
                    a.setSustainTime(n.getDuration() - a.getAttackTime() - a.getDecayTime() - a.getReleaseTime());
					a.setSustainAmplitude(amp*sustainamp);
					ADSREnvelopedContinuousSound env =
							new ADSREnvelopedContinuousSound(freq, amp, a, 44100, d);
                    System.out.printf("Freq = %f \n", freq);
					env.load();
					
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
		double sustainTime = Double.parseDouble(args[3]);
		double releaseTime = Double.parseDouble(args[4]);

		ADSREnvelope a = new ADSREnvelope(attackTime,
						            		  decayTime,
						           		  sustainAmplitude,
						            		  releaseTime,
		                            		  sustainTime);

		
		Melody m = new Melody();

        double freq = 220;

        for(int i=1; i<=12; i++){
            freq = freq * Math.pow(2, 1/12.0);
		    m.add(new Note(freq, 0.5 , 0.3));
        }

        if(!m.checkCompatibility(a,m))
           	throw new IllegalArgumentException("Melody and Envelope are incompatible");

		m.play(a, m);
	}
	



}
