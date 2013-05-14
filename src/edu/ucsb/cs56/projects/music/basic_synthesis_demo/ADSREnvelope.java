package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

public class ADSREnvelope{

    private double attackTime;
    private double decayTime;
    private double sustainAmplitude;
    private double releaseTime;
    private double sustainTime;

    /**
    @param double sustainAmplitude the sustain volume;
    @param double sustainTime the number of seconds to sustain for
    @param double attackTime the number of seconds to attack for
    @param double decayTime amount of time to decay/sustain
    @param double releaseTime amount of time to release over
    */
    public ADSREnvelope(double attackTime, double decayTime, double sustainAmplitude, double releaseTime, double sustainTime)
    {
        this.attackTime = attackTime;
        this.decayTime = decayTime;
        this.sustainAmplitude = sustainAmplitude;
        this.releaseTime = releaseTime;
        this.sustainTime = sustainTime;
    }

    public double getADSREnvelope(){
        return this.attackTime+this.decayTime+this.sustainAmplitude+this.releaseTime+this.sustainTime;
    }
    public void setADSREnvelope(double attackTime, double decayTime, double sustainAmplitude, double releaseTime, double sustainTime)
    {
        this.attackTime = attackTime;
        this.decayTime = decayTime;
        this.sustainAmplitude = sustainAmplitude;
        this.releaseTime = releaseTime;
        this.sustainTime = sustainTime;
    }
    public double getAttackTime() {
		return attackTime;
	}
	public void setAttackTime(double attackTime) {
		this.attackTime = attackTime;
    }

	public double getDecayTime() {
		return decayTime;
	}

	public void setDecayTime(double decayTime) {
		this.decayTime = decayTime;
	}

	public double getSustainAmplitude() {
		return sustainAmplitude;
	}

	public void setSustainAmplitude(double sustainAmplitude) {
		this.sustainAmplitude = sustainAmplitude;
	}

	public double getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(double releaseTime) {
		this.releaseTime = releaseTime;
	}

	public double getSustainTime() {
		return sustainTime;
	}

	public void setSustainTime(double sustainTime) {
		this.sustainTime = sustainTime;
    }		
    
    public String toString(){
       return " Sustain Amplitude: "+sustainAmplitude+" Atack Time: "+attackTime+" Decay Time: "+decayTime+ " Sustain Time: "+sustainTime+" Release Time: "+releaseTime;
    }

}
