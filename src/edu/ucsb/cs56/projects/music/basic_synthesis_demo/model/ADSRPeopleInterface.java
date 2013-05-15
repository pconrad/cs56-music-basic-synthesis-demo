package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.Scanner;
import java.util.HashMap;
/**
 This is the command-line UI. It will ask the user for a series of parameters
 to place into the ADSR, 
 and then produce said note until user prompts the note to release.
 This will be released by a GUI at a later time.
 */

/*
get use input, dispatch a thread to run it;
Use a pool?
w/ command line interface, echo the command to run it from ant
*/
public class ADSRPeopleInterface{
	
	/*private HashMap<String,ADSREnvelopedSound> soundTable;
	AudioFormat f= new AudioFormat(44100,8,1,true,true);
	SourceDataLine d = AudioSystem.getSourceDataLine(f);
	
	public ADSRPeopleInterface(){
		soundTable=new HashMap<String,ADSREnvelopedSound>();
	}
	public void dispatchSlave(String key, double duration){
		Thread t = new Thread(new Audio)
	}
	*/ 
	
	/**
	 * asks for user input and generates a note
	 */
	public static ADSREnvelopedSound makeNote(Scanner s) {
		while(true) {
	    try {
		    System.out.print("Enter an attack volume between 0 and 1,");
			 System.out.print(" values will be chopped at 0 and 1");
		    double atkAmplitude= s.nextDouble();
		    atkAmplitude=((atkAmplitude>1)?1:((atkAmplitude<0)?0: atkAmplitude));		    
		    System.out.print("Enter a sustain volume between 0 and 1,");
 			 System.out.print(" values will be chopped at 0 and 1");
		    double sustAmplitude= s.nextDouble();
		    sustAmplitude=((sustAmplitude>1)?1:((sustAmplitude<0)?0: sustAmplitude));
		    System.out.print("Enter an attack time between 0 and inf,"); 
			 System.out.print(" values will be chopped at 0");
		    double atkT= s.nextDouble();
		    atkT=(atkT<0)?0:atkT;
	 	    System.out.print("Enter a decay time between 0 and inf,");
			 System.out.print(" values will be chopped at 0");
		    double decayT= s.nextDouble();
		    decayT=(decayT<0)?0:decayT;
		    System.out.print("Enter a release time between 0 and inf,");
			 System.out.print(" values will be chopped at 0");
		    double releaseT= s.nextDouble();
		    releaseT=(releaseT<0)?0:releaseT;
		    System.out.print("Enter a Frequency between 0 and 30000, though");
			 System.out.print(" values above ~4000 are extremely irritating.");
		    double freq = s.nextDouble();
		    freq=((freq>30000)?30000:((freq<0)?0: freq));
			return new ADSREnvelopedSound(freq,atkAmplitude,sustAmplitude,
													atkT,decayT,releaseT,44100);
		}
	   catch (java.util.InputMismatchException ex) {
			System.out.println("Enter real numbers please");
		} 
	}
}
	/**
	 * executes the program
	*/
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		AudioFormat f= new AudioFormat(44100,8,1,true,true);
		try{
		    SourceDataLine d = AudioSystem.getSourceDataLine(f);
		    d.open(f);
		    d.start();
		    String exitToken;
		    boolean morenote=true;
		    while(morenote) {
			d.flush();
			Thread dr= new Thread(new AudioDrain(d));
			Thread t = new Thread(new AudioLoader(d,makeNote(s)));
			t.start();
			//Thread.sleep(10);
			dr.start();
			System.out.println("Enter any key to exit");
			while(!s.hasNext()) {}
			t.interrupt();
			s.next();
			System.out.println("good job! make another? y/n");
			while(!(exitToken=s.next()).equals("y")&&!exitToken.equals("n"));
			if (exitToken.equals("n"))
			    morenote=false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
//play a .5 c .3 a .5
}

