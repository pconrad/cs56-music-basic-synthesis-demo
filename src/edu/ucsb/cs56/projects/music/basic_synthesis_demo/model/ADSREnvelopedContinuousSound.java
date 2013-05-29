package edu.ucsb.cs56.projects.music.basic_synthesis_demo;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
	A bit of code which generates a continuous adsr envolope 
	wrapped frequency. The code first creates a linear climb
	based on a time ammount specific in attackTime,
	followed by an exponential decay, at which point
	it will maintain the sustain volume. It extends 
	the basic framework for making enveloped sound presented
	in EnvelopedSound.
*/

public class ADSREnvelopedContinuousSound extends EnvelopedSound {
    private final boolean DEBUG = false;
    private double amplitude;
    private double attackTime;
    private double decayTime;
    private double sustainAmplitude;
    private double releaseTime;
    private double sustainTime;
    
    /**
       @param frequency the frequency to play at
       @param amplitude the maximum volume
       @param attackTime the number of seconds to attack for
       @param decayTime amount of time to decay/sustain
       @param sustainAmplitude the sustain volume
       @param sustainTime the amount of time to sustain over
       @param releaseTime amount of time to release over
       @param sampleRate the number of samples per second
       @param l the source data line
    */
    public ADSREnvelopedContinuousSound (	
			      double frequency,
			      double amplitude,
			      double attackTime,
			      double decayTime,
			      double sustainAmplitude,
			      double sustainTime,
			      double releaseTime,
			      int sampleRate,
			      SourceDataLine l) {
	super(frequency,attackTime+decayTime+sustainTime+releaseTime,
	      sampleRate,l);
	this.amplitude = amplitude;
	this.sustainAmplitude = sustainAmplitude;
	this.attackTime = attackTime;
	this.decayTime = decayTime;
	this.sustainTime = sustainTime;
	this.releaseTime = releaseTime;
	generateEnvelope();
    }

    /**
       @return the amplitude
    */
    public double getAmplitude() {
	return amplitude;
    }
    
    /**
       sets the amplitude
    */
    public void setAmplitude(double amp) {
	this.amplitude = amp;
    }
    
    /**
       @return the attackTime
    */
    public double getAttackTime() {
	return attackTime;
    }
    
    /**
       sets the attackTime and changes the totalTime accordingly
    */
    public void setAttackTime(double t) {
	this.totalTime -= this.attackTime;
	this.attackTime = t;
	this.totalTime += this.attackTime;
    }
    
    /**
       @return the decayTime
    */
    public double getDecayTime() {
	return decayTime;
    }
    
    /**
       sets the decayTime and changes totalTime accordingly
    */
    public void setDecayTime(double t) {
	this.totalTime -= this.decayTime;
	this.decayTime = t;
	this.totalTime += this.decayTime;
    }
    
    /**
       @return the releaseTime
    */
    public double getReleaseTime() {
	return releaseTime;
    }
    
    /**
       sets the releaseTime and changes totalTime accordingly
    */
    public void setReleaseTime(double t) {
	this.totalTime -= this.releaseTime;
	this.releaseTime = t;
	this.totalTime += this.releaseTime;
    }
    
    /**
       @return the sustainTime
    */
    public double getSustainTime() {
	return sustainTime;
    }
    
    /**
       sets the sustainTime and canges totalTime accordingly
    */
    public void setSustainTime(double t) {
	this.totalTime -= this.sustainTime;
	this.sustainTime = t;
	this.totalTime += this.sustainTime;
    }
 
    /**
       @return the sustainAmplitude
    */
    public double getSustainAmplitude() {
	return sustainAmplitude;
    }
    
    /**
       sets the sustainAmplitude and canges totalTime accordingly
    */
    public void setSustainAmplitude(double amp) {
	this.sustainAmplitude = amp;
    }
    
    /**
       @return the private instance variables in a String
    */
    public String toString() {
        return super.toString() + " Amplitude: " + amplitude + 
	    " Sustain Amplitude: " + sustainAmplitude +
	    " Atack Time: " + attackTime +
	    " Decay Time: " + decayTime +
	    " Sustain Time: " + sustainTime +
	    " Release Time: " + releaseTime;
    }

    protected double generateAmplitude(double time, double amplitude) {
	double amp = amplitude;
	if (time < this.attackTime) {
	    //the rate of growth of amplitude per sample
	    double deltaAmp = this.amplitude/(getSampleRate()*this.attackTime);
	    return amplitude + deltaAmp;
	} else if (time < this.attackTime + decayTime) {
	    double a = amplitude - this.sustainAmplitude;
	    //when amplitude =.0001*A[max value of 1] 127*A=0;
	    double k = Math.log(.001) / (this.decayTime*getSampleRate());
	    if(DEBUG && time < .26)
		System.out.println(k+" "+a+" "+decayTime+" "+Math.log(.001));
	    return amplitude + k*a;
	} else if (time < this.attackTime + this.decayTime + this.sustainTime) {
	    return amplitude;
	} else {
	    //when amplitude =.0001*A[max value of 1] 127*A=0;
	    double k = Math.log(.001) / (this.releaseTime * getSampleRate());
	    return amplitude + k*amplitude;
	}
    }

    /**
       executes the program
    */
    public static void main(String[] args) {
	if (args.length != 7) {
	    System.out.print("frequency, amp, attack, decay, sustain amp,");
	    System.out.println("sustain time, release");
	    System.exit(1);
	}

	AudioFormat f = new AudioFormat(44100,8,1,true,true);
	try {
	    /* Create a new audioLine which goes to the system, 
	       the audio format specifys all the features of the line.
	    */
	    SourceDataLine d = AudioSystem.getSourceDataLine(f);
	    ADSREnvelopedContinuousSound env =
		new ADSREnvelopedContinuousSound(Double.parseDouble(args[0]),
						 Double.parseDouble(args[1]),
						 Double.parseDouble(args[2]),
						 Double.parseDouble(args[3]),
						 Double.parseDouble(args[4]),
						 Double.parseDouble(args[5]),
						 Double.parseDouble(args[6]),
						 44100,
						 d);
	    d.open(f);
	    d.start();
	    env.load();
	    d.drain();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
