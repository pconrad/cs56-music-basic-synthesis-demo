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
    public Melody() {
	super(1); //call constructor for ArrayList<Note>, with capacity 1
	freqmap = new FrequencyMap();
    }
    
    /**
     * void method to create a Melody from a text file
     */
    public static Melody createMelodyFromFile(String filename) throws IOException, FileNotFoundException{
	//acccess the file with the Melody
	InputStream melodyFileInputStream = null;
	try {
	    melodyFileInputStream = Melody.class.getResourceAsStream("/resources/"+ filename);
	} catch (Exception e) {
	    System.err.println("Could not get file " + filename + " from classpath. Trying filesystem...");
	    melodyFileInputStream = null;
	}

	if (melodyFileInputStream == null) {
	    melodyFileInputStream = new FileInputStream(filename);
	}
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(melodyFileInputStream));
	
	String line, note_name;
	int octave;
	double duration, volume, freq;
	
	Melody m = new Melody();	
	//read in the file one line at a time
	line = reader.readLine();
	while(line != null) {   
	    line = line.toUpperCase();
	    //parse each line
	    String[] splitline = line.split("\\s+");
	    note_name = splitline[0];
	    char firstLetter = note_name.charAt(0);
	    //checks to see if the first letter is a number, it is is, it's a freq
	    if(firstLetter >= 48 && firstLetter <= 57){ 
		freq = Double.parseDouble(note_name);
	    }
	    else {
		//else, we're given a string of either midi or scientific name 
		if(note_name.startsWith("M")) {
		    //gets just the number from the midi and sends it to have a frequency built
		    double midiNum = Double.parseDouble(note_name.substring(1)); 
		    freq = m.freqmap.getFreq(midiNum);
		} else {
		    freq = m.freqmap.getFreq(note_name);
		}
	    }
	    duration = Double.parseDouble(splitline[1]);
	    volume = Double.parseDouble(splitline[2]);
	    
	    //create the note and add it to the melody
	    m.add(new Note(freq,duration,volume));
	    
	    line = reader.readLine();
	    
	}
	reader.close();
	return m;
    }
    
    /**
     * method to check if the durations for all the notes in the
     * melody are compatible with the envelope being used
     */
    
    public boolean checkCompatibility(ADSREnvelope a, Melody m) {
        double note_duration = 0;
        for(Note n : m) {
	    //calculate the duration the note should be played for
	    
	    note_duration = n.getDuration() - a.getAttackTime() - a.getDecayTime() - a.getReleaseTime();
	    
	    //if the duration will be less than 0, then the Note doesn't fit the envelope that was entered
	    
	    if(note_duration < 0) {
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
    public void play(ADSREnvelope a, Melody m) {
	
	double freq, amp;
	double sustainamp = a.getSustainAmplitude();
	
	
	AudioFormat f= new AudioFormat(44100,8,1,true,true);
	
	try {
	    //Create a new audioLine which goes to the system, the
	    //audio format specifys all the features of the line.
	    
	    SourceDataLine d = AudioSystem.getSourceDataLine(f);
	    
	    //open the audioLine and set the buffer size
	    
	    d.open(f, 44100*2);	    
	    d.start();
	    
	    //iterate through every note in the melody, and play it
	    for(int i =0 ; i<m.size() ; i++) {
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
	} catch(Exception ex) {	    
	    ex.printStackTrace();
	}
    }

    public static void playOne(String filename, ADSREnvelope a) {
	//Create a melody from the default text file
	Melody m = null;
	try {
	    m = Melody.createMelodyFromFile(filename);
	}
	catch(Exception ex) {	
	    System.err.println("An error occured in creating the melody from " + filename);
	    ex.printStackTrace();
	    System.exit(1);
	}
	
	//Check that the melody and envelope are compatible
	if(!m.checkCompatibility(a,m))
	    throw new IllegalArgumentException("Melody and Envelope are incompatible");
	
	//play the melody
	m.play(a, m);
    }
    
    public static void playMany(String [] args, ADSREnvelope a) {
	
	//get the number of files the user wants to play
	int numFiles = Integer.parseInt(args[5]);
	    
	//check that the correct number of file names are present
	if (args.length != (6 + numFiles)) {
	    System.out.println("Error: " + numFiles + " File names were not entered");
	    System.exit(2);
	}
	
	//try to create a melody from each file that was entered
	ArrayList<Melody> melody_list = new ArrayList<Melody>();
	
	for (int i = 6 ; i < args.length ; i++) {
	    try {
		Melody n = createMelodyFromFile(args[i]);
		melody_list.add(n);		
	    } catch(Exception ex) {
		System.out.println("Could not create melody from file " + args[i]);
		System.exit(3);
	    }
	}
	
	//concatenate the melodys and play them all
	Melody concatMelody = new Melody();
	for (Melody m: melody_list) {
	    if(!m.checkCompatibility(a,m))
		throw new IllegalArgumentException("Melody and Envelope are incompatible");
	    
	    concatMelody.addAll(m);
	} // for 
	concatMelody.play(a,concatMelody);
	
    } // playMany
    
    /**
     * Main method that will play the melody
     * Takes in command line arguments to create ADSREnvelope
     * Takes in optional command line arguments to play one or more specific Melody files
     */
    public static void main(String[] args){
	
	//check that the minimum number of command line arguments were entered
	if (args.length < 5) {
	    System.out.println("attack, decay, sustain amp, sustain time, release");
	    System.out.println("Optional: Num_of_files, names of files");
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
	if (args.length == 5) 
	    playOne("Default.txt",a);
	else 
	    playMany(args,a);
    } // main
    
}
