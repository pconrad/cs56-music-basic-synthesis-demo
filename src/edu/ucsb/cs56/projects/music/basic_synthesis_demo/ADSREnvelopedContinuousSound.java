package edu.ucsb.cs56.projects.music.basic_synthesis_demo;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
	A bit of code which generates an adsr envolope wrapped frequency. 
	The code first creates a linear climb based on a time ammount
	specifice in attackTime, followed by an exponential decay,
	at which point it will maintain the sustain volume.
	It extends the basic framework for making enveloped sounds
	presented in EnvelopedSound.
*/

public class ADSREnvelopedContinuousSound extends EnvelopedSound {
    private final boolean DEBUG=false;
    private double amplitude;
    private double attackTime;
    private double decayTime;
    private double sustainAmplitude;
    private double releaseTime;
    private double sustainTime;
    
    /**
       @param double frequency the frequency to play at
       @param double amplitude the maximum volume
       @param double sustainAmplitude the sustain volume
       @param double attackTime the number of seconds to attack for
       @param double decayTime amount of time to decay/sustain
       @param double releaseTime amount of time to release over
       @param int sampleRate the number of samples per second
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
    
    public double getAmplitude() {
	return amplitude;
    }
    public void setAmplitude(double amp) {
	this.amplitude = amp;
    }
    public double getAttackTime() {
	return attackTime;
    }
    public void setAttackTime(double t) {
	this.totalTime -= this.attackTime;
	this.attackTime = t;
	this.totalTime += this.attackTime;
    }
    public double getDecayTime() {
	return decayTime;
    }
    public void setDecayTime(double t) {
	this.totalTime -= this.decayTime;
	this.decayTime = t;
	this.totalTime += this.decayTime;
    }
    public double getReleaseTime() {
	return releaseTime;
    }
    public void setReleaseTime(double t) {
	this.totalTime -= this.releaseTime;
	this.releaseTime = t;
	this.totalTime += this.releaseTime;
    }
    public double getSustainTime() {
	return sustainTime;
    }
    public void setSustainTime(double t) {
	this.totalTime -= this.sustainTime;
	this.sustainTime = t;
	this.totalTime += this.sustainTime;
    }
 
    public double getSustainAmplitude() {
	return sustainAmplitude;
    }
    public void setSustainAmplitude(double amp) {
	this.sustainAmplitude = amp;
    }
    
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
	if (time<this.attackTime) {
	    //the rate of growth of amplitude per sample
	    double deltaAmp = this.amplitude/(getSampleRate()*this.attackTime);
	    return amplitude + deltaAmp;
	}
	else if (time < this.attackTime + decayTime) {
	    double a = amplitude - this.sustainAmplitude;
	    //when amplitude =.0001*A[max value of 1] 127*A=0;
	    double k = Math.log(.001) / (this.decayTime*getSampleRate());
	    if(DEBUG && time < .26)
		System.out.println(k+" "+a+" "+decayTime+" "+Math.log(.001));
	    return amplitude + k*a;
	}
	else if (time < this.attackTime + this.decayTime + this.sustainTime) {
	    return amplitude;
	}
	else {
	    //when amplitude =.0001*A[max value of 1] 127*A=0;
	    double k = Math.log(.001) / (this.releaseTime * getSampleRate());
	    return amplitude + k*amplitude;
	}
    }

    /**
     * executes the program
     */
    public static void main(String[] args) {
	if(args.length!=7){
	    System.out.println("frequency, amp, attack, decay, sustain amp, sustain time, release");
	    System.exit(1);
	}

	AudioFormat f= new AudioFormat(44100,8,1,true,true);
	try{
	    //Create a new audioLine which goes to the system, the audio format specifys all the features of the line.
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
	}catch (Exception ex){
	    ex.printStackTrace();
	}
    }
}
