package edu.ucsb.cs56.projects.music.basic_synthesis_demo;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
   A wrapper for all enveloped sounds,
   implements basic sine wave with a constant amplitude.
 */
public class EnvelopedSound {
    private final boolean DEBUG = false;
    private SourceDataLine line;	
    protected double frequency;
    protected double totalTime;
    private int sampleRate;
    protected int samples;
    private byte [] audioData;
    private boolean unclipped;
    double testSample = 0;
    double testTime = 0;
    
    /**
       @param double frequency the frequency to play at
       @param double amplitude the maximum volume;
       @param double sustainAmplitude the sustain volume;
       @param double attackTime the number of seconds to attack for
       @param double decayTime amount of time to decay/sustain
       @param double releaseTime amount of time to release over
       @param int sampleRate the number of samples per second
       Children must invoke generateEnvelope() to prep the array 
       with audio data.
       protected values can be change as the developer sees fit.
    */
    protected EnvelopedSound(	
			      double frequency,
			      double totalTime,
			      int sampleRate,
			      SourceDataLine l) {
	this.frequency = frequency;
	this.sampleRate = sampleRate;
	this.line = l;
	this.totalTime = totalTime;
	this.unclipped = false;
	this.samples = (int)(this.totalTime*this.sampleRate);
    }

    protected EnvelopedSound(	
			      double frequency,
			      double totalTime,
			      int sampleRate,
			      SourceDataLine l,
			      boolean unclip){
	this.frequency=frequency;
	this.sampleRate=sampleRate;
	this.line=l;
	this.totalTime=totalTime;
	if(unclip)
	    unclipTime();
	this.unclipped=unclip;
	this.samples = (int)(this.totalTime * this.sampleRate);
    }

    /**
       Getter for the sample rate
       @return the sampleRate
     */
    public int getSampleRate() {
	return this.sampleRate;
    }

    /** 
	Setter for the the sampleRate
    */
    public void setSampleRate(int s) {
	this.sampleRate = s;
    }

    /**
       Getter for the audio line
       @return the line
    */
    public SourceDataLine getLine() {
	return line;
    }

    /** 
	Setter for the audio line
    */
    public void setLine(SourceDataLine line) {
	this.line = line;
    }

    /**
       Getter for the frequency
       @return the frequency
    */
    public double getFrequency() {
	return frequency;
    }

    /**
       Setter for the frequency
    */
    public void setFrequency(double freq) {
	this.frequency = freq;
    }

    /**
       find the total time of attack, decay, and release (without sustain)
       @return total ammount of time with no sustain
     */
    public double totalTime() {
	return totalTime;
    }

    /**
       Setter for the total time
    */
    public void setTime(double T) {
	this.totalTime = T;
    }

    /**
       Getter for the audio data
       @return the audioData
    */
    public byte[] getData(){
	return audioData;
    }
    
    /**
       @return the EnvelopedSound as a String
    */
    public String toString(){
	return "Frequency: " + frequency + 
	    "Duration: " + totalTime + 
	    " Sample rate: " + sampleRate; 
    } 
    
    /**
       find the amplitude of a specific sample within the audio array
       @return the amplitude value of a sample in the audio array at
               any given time
       @param time the second value for which you would like to
              recieve the sample
    */
    public byte wavValAtTime(double time) {

	double samptime = time*sampleRate;
	int index = (int) samptime;
	return audioData[index];
    }
    
    /**
       @return whether or not the clipping code has been called
     */
    public boolean getClipState() {
	return this.unclipped;
    }

    /**
       Prevents clipping by making sure that the sin wave finishes before
       switching from sustain to release.
       This will create a SLIGHT latency,
       but it is a necessary evil to prevent clipping.
    */
    protected void unclipTime() {
	this.totalTime = this.samples/(double)this.sampleRate;
	while (Math.abs(Math.sin(2*Math.PI*frequency*this.totalTime))>=0.001) {
	    this.totalTime += 1 / (double) this.sampleRate;
	    this.samples++;
	}
    }

    /**
       Generates the actual envelope as a function of the wave
       and the amplitdue; it should not be changed.
    */
    protected void generateEnvelope() {
	audioData = new byte[samples];
	double t = 0;
	double amp = 0;
	for (int i=0; i<samples; i++) {      //attack
	    if (DEBUG&&i%50 == 0)
		System.out.println(t+ " : time amplitude base:" + amp);
	    amp=generateAmplitude(t,amp);
	    if (DEBUG&&i%50 == 0)
	    	System.out.println("New Amplitude: " + amp);
	    audioData[i] = (byte)( (127) * generateWave(t) * amp);
	    t += 1.0 / (double)this.sampleRate;
	}	
    }

    /**
      Defines the value of the wave at a given time t 
    */
    protected double generateWave(double time) {
	    return Math.sin(2 * Math.PI *frequency*time);
	    /* the wave function x=A*Sin(omega*t+phi)
	       combined with phi=0, and omega = 2*Pi*f
	    */
    }

    /**
       Defines the amplitude of the wave, at the time t
       @return 1
     */
    protected double generateAmplitude(double time, double amplitude) {
	return 1;
    }

    /**
       load up the internal audio output line we get
       with the byte sample we already generated
    */
    public void load() {
	try {
	    line.write(audioData,0,audioData.length);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
