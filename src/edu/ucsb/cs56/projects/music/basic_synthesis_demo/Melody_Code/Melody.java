package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.*;
import java.io.*;
/**
*    A Class that represents a Melody.   
*    
*   @author Bronwyn Perry-Huston
*   @version CS56 S13 
*/

public class Melody extends ArrayList<Note>{

	private boolean debug = false;
	private FrequencyMap freqmap;

	/**
    * no-arg constructor returns an empty melody 
    */
	public Melody(){
		//call constructor for ArrayList<Note>, with capacity 1
		super(1);
		freqmap = new FrequencyMap();
	}

	/**
	* void method to create a Melody from a text file
	*/
	public Melody createMelodyFromFile(String filename) throws IOException, FileNotFoundException{
		
			//acccess the file with the Melody
			InputStream melodyFile = Melody.class.getResourceAsStream("/resources/"+ filename);
			BufferedReader reader = new BufferedReader(new InputStreamReader(melodyFile));

			String line;
			String note_name;
			int octave;
			double duration;
			double volume;
			double freq;
			
			Melody m = new Melody();

			//read in the file one line at a time
			line = reader.readLine();
			while(line != null)
			{
				//parse each line
				String[] splitline = line.split("\\s+");
				note_name = splitline[0];
				octave = Integer.parseInt(splitline[1]);
				duration = Double.parseDouble(splitline[2]);
				volume = Double.parseDouble(splitline[3]);

				//calculate the frequency from the letter and octave
				freq = freqmap.getFreq(note_name) * Math.pow(2,octave);

				//create the note and add it to the melody
				m.add(new Note(freq,duration,volume));
			
				line = reader.readLine();

			}

			reader.close();
			return m;
		
	}

    /**
    * method to check if the durations for all the notes in the melody are compatible with the envelope being used
    */
    public boolean checkCompatibility(ADSREnvelope a, Melody m){
        double note_duration = 0;
        for(Note n : m)
        {
			//calculate the duration the note should be played for
            note_duration = n.getDuration() - a.getAttackTime() - a.getDecayTime() - a.getReleaseTime();

			//if the duration will be less than 0, then the Note doesn't fit the envelope that was entered
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
			//Create a new audioLine which goes to the system, the audio format specifys all the features of the line.
			SourceDataLine d = AudioSystem.getSourceDataLine(f);

			//open the audioLine and set the buffer size
			d.open(f, 44100*2);

		    d.start();
		
			//iterate through every note in the melody, and play it
			for(int i =0 ; i<m.size() ; i++)
			{
                    Note n = m.get(i);
                    
					//get the amplitude and frequency for the note
					freq = n.getFrequency();          					
                    amp = n.getVolume();

					//scale the envelope parameters for the duration and volume of the note
                    a.setSustainTime(n.getDuration() - a.getAttackTime() - a.getDecayTime() - a.getReleaseTime());
					a.setSustainAmplitude(amp*sustainamp);

					//create and load the note
					ADSREnvelopedContinuousSound env =
							new ADSREnvelopedContinuousSound(freq, amp, a, 44100, d);
	
                    if(debug){System.out.printf("Freq = %f \n", freq);};

					//load the ADSREnveloped note to the audioLine
					env.load();
					d.drain();

					
			}

			d.close();
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
	}
	
	/**
	* Main method that will play the melody
	* Takes in command line arguments to create ADSREnvelope
	* Takes in optional command line arguments to play one or more specific Melody files
	*/
	public static void main(String[] args){

		//check that the minimum number of command line arguments were entered
		if(args.length<5){
	        System.out.println("attack, decay, sustain amp, sustain time, release");
			System.out.println("Optional: Num_of_file, names of files");
	        System.exit(1);
	    }
		
			

		//parse all of the command line arguments to create the ADSREnvelope
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
		
		if(args.length == 5)
		{
			//Create a melody from the default text file
			Melody m = new Melody();

			//try to open the file and create the melody
			try{
				m = m.createMelodyFromFile("Default.txt");
			
			}
			catch(Exception ex)
			{	
				System.out.println("An error occured in creating the melody from the file");
				ex.printStackTrace();
			}

			//Check that the melody and envelope are compatible
		    if(!m.checkCompatibility(a,m))
		       	throw new IllegalArgumentException("Melody and Envelope are incompatible");

			//play the melody
			m.play(a, m);
		}
		else{
			//get the number of files the user wants to play
			int numFiles = Integer.parseInt(args[5]);
			
			//check that the correct number of file names are present
			if(args.length != (6 + numFiles))
			{
				System.out.println("Error: " + numFiles + " File names were not entered");
				System.exit(2);
			}

			//try to create a melody from each file that was entered
			ArrayList<Melody> melody_list = new ArrayList<Melody>();
			Melody n = new Melody();
			try{
				for(int i = 6 ; i < args.length ; i++)
				{
					n = n.createMelodyFromFile(args[i]);
					melody_list.add(n);
				}
			}catch(Exception ex)
			{
				System.out.println("An error occured in creating the melody from the file here. Check that the file exists and is in the correct format");
				System.exit(3);
			}

			//concatenate the melodys and play them all
			Melody concatMelody = new Melody();
			for(Melody m: melody_list)
			{
				if(!m.checkCompatibility(a,m))
		       		throw new IllegalArgumentException("Melody and Envelope are incompatible");
				
				concatMelody.addAll(m);
			}
			concatMelody.play(a,concatMelody);

		}
	}
	



}
