package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;

/**
	A bit of code which generates an adsr envolope. 
	The code first creates a linear climb based on a time ammount specifice in  attackTime,	followed by an exponential decay, at which point it will maintain the sustain volume. This encapsulates the attackTime, decayTime, sustainAmplitude, releaseTime, and sustainTime attributes that are used to create an ADSREnveloped Frequency.

	@author Bronwyn Perry-Huston	
	@version cs56 S13 project1
*/
public class ADSREnvelope{

    private double attackTime;
    private double decayTime;
    private double sustainAmplitude;
    private double releaseTime;
    private double sustainTime;

    /**
	* Five-arg Constructor for objects of class ADSR
    * @param double sustainAmplitude the sustain volume;
    * @param double sustainTime the number of seconds to sustain for
    * @param double attackTime the number of seconds to attack for
    * @param double decayTime amount of time to decay/sustain
    * @param double releaseTime amount of time to release over
    */

    public ADSREnvelope(double attackTime, double decayTime, double sustainAmplitude, double releaseTime, double sustainTime)
    {
        this.attackTime = attackTime;
        this.decayTime = decayTime;
        this.sustainAmplitude = sustainAmplitude;
        this.releaseTime = releaseTime;
        this.sustainTime = sustainTime;
    }

	/**
     * get the entire envelope of the ADSR envelope
     * 
     * @return the entire envelope
     */
    public double getADSREnvelope(){
        return this.attackTime+this.decayTime+this.releaseTime+this.sustainTime;
    }

	/**
     * set the entire envelope of the ADSR envelope
     * 
     * set all of the parameters at once
     */
    public void setADSREnvelope(double attackTime, double decayTime, double sustainAmplitude, double releaseTime, double sustainTime)
    {
        this.attackTime = attackTime;
        this.decayTime = decayTime;
        this.sustainAmplitude = sustainAmplitude;
        this.releaseTime = releaseTime;
        this.sustainTime = sustainTime;
    }

	/**
     * get the attack time of the ADSREnvelope
     * 
     * @return the attack time of the ADSREnvelope
     */
    public double getAttackTime() {
		return attackTime;
	}

	/**
     * set the attack time of the ADSREnvelope
     * 
     * @param attackTime the new attack time of the ADSREnvelope
     */
	public void setAttackTime(double attackTime) {
		this.attackTime = attackTime;
    }

	/**
     * get the decay time of the ADSREnvelope
     * 
     * @return the decay time of the ADSREnvelope
     */
	public double getDecayTime() {
		return decayTime;
	}

	/**
     * set the decay time of the ADSREnvelope
     * 
     * @param decayTime the new decay time of the ADSREnvelope
     */
	public void setDecayTime(double decayTime) {
		this.decayTime = decayTime;
	}

	/**
     * get the sustain amplitude of the ADSREnvelope
     * 
     * @return the sustain amplitude of the ADSREnvelope
     */
	public double getSustainAmplitude() {
		return sustainAmplitude;
	}

	/**
     * set the sustain amplitude of the ADSREnvelope
     * 
     * @param sustain Amplitude the new sustain amplitude of the ADSREnvelope
     */
	public void setSustainAmplitude(double sustainAmplitude) {
		this.sustainAmplitude = sustainAmplitude;
	}

	/**
     * get the release time of the ADSREnvelope
     * 
     * @return the release time of the ADSREnvelope
     */
	public double getReleaseTime() {
		return releaseTime;
	}

	/**
     * set the release time of the ADSREnvelope
     * 
     * @param releaseTime the new release time of the ADSREnvelope
     */
	public void setReleaseTime(double releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
     * get the sustain time of the ADSREnvelope
     * 
     * @return the sustain time of the ADSREnvelope
     */
	public double getSustainTime() {
		return sustainTime;
	}

	/**
     * set the sustain time of the ADSREnvelope
     * 
     * @param sustainTime the new sustain time of the ADSREnvelope
     */
	public void setSustainTime(double sustainTime) {
		this.sustainTime = sustainTime;
    }		
    
	 /** format ADSREnvelope as String 
     *  @return formatted ADSREnvelope
     */
    public String toString(){
       return " Sustain Amplitude: "+sustainAmplitude+" Atack Time: "+attackTime+" Decay Time: "+decayTime+ " Sustain Time: "+sustainTime+" Release Time: "+releaseTime;
    }

}
