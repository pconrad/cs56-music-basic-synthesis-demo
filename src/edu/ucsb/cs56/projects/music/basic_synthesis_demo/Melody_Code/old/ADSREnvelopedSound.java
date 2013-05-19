package edu.ucsb.cs56.S12.ISSUE0000779.old;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
	A bit of code which generates an adsr envolope wrapped frequency. 
	The code first creates a linear climb based on a time ammount specifice in  attackTime, 
	followed by an exponential decay, at which point it will maintain the sustain volume.
*/

public class ADSREnvelopedSound{
	private SourceDataLine line;
	
	private double frequency;
	private double amplitude;
	private double sustainAmplitude;
	private double attackTime;
	private double decayTime;
	private double releaseTime;
	private double ADTime;//attack/decay time
	private double STime;//sustain time
	private int sampleRate;
	private int ADsamples;//attack decay samples
	private int Ssamples;//Sustain samples
	private int Rsamples;//release samples
	
	private byte [] audioData; //stores attack+decay data;
	//
	private byte [] sustainData;
	private byte [] releaseData;
	double testSample=0;
	double testTime=0;
	/**
	@param double frequency the frequency to play at
	@param double amplitude the maximum volume;
	@param double sustainAmplitude the sustain volume;
	@param double attackTime the number of seconds to attack for
	@param double decayTime amount of time to decay/sustain
	@param double releaseTime amount of time to release over
	@param int sampleRate the number of samples per second
	*/
	public ADSREnvelopedSound(	
			
								double frequency,
								double amplitude,
								double sustainAmplitude,
								double attackTime,
								double decayTime,
								double releaseTime,
								int sampleRate){
		this.frequency=frequency;
		this.amplitude=amplitude;
		this.sustainAmplitude=sustainAmplitude;
		this.attackTime=attackTime;
		this.decayTime=decayTime;
		this.releaseTime=releaseTime;
		//this.line=line;
		this.sampleRate=sampleRate;
		this.ADTime=attackTime+decayTime;
		this.releaseTime = releaseTime;
		this.STime=.4;//set the duration of a sustain to .4 seconds;
		unclipADTime();
		unclipSTime();
		unclipRTime();

		generateAttackDecayWave();
		generateSustainWave();
		generateReleaseWave();
		
	}

	/**
	 * find the amplitude of a specific sample within the audio array
	 * @return the amplitude value of a sample in the audio array at any given time. this is mostly for purposes
	 * @param double time  The second value for which you would like to recieve the sample
	 */
	public byte wavValAtTime(double time){

		double samptime=time*sampleRate;
		int index = (int) samptime;
		return audioData[index];
	}
	/**
	 * find the total time of attack, decay, and release (without sustain)
	 * 
	 * @return total ammount of time with no sustain
	 */
	public double totalTime(){
		return ADTime+releaseTime;
	}
	/**
	 * Prevents clipping by making sure that the sin wave finishes before switching from attack/decay to sustain.
	 * this will create a SLIGHT latency, but is a necessary evil to prevent clipping.
	 */
	private void unclipADTime(){

		this.ADsamples = (int)(this.ADTime*this.sampleRate);
		this.ADTime = this.ADsamples/(double)this.sampleRate;
		while(Math.abs(Math.sin(2*Math.PI*frequency*this.ADTime))>=0.0001){
			this.ADTime+=1/(double)this.sampleRate;
			this.ADsamples++;
			this.decayTime+=1/(double)this.sampleRate;
		}
	}
	/**
	 * Prevents clipping by making sure that the sin wave finishes before switching from sustain to release.
	 * this will create a SLIGHT latency, but is a necessary evil to prevent clipping.
	 */
	private void unclipSTime(){

		this.Ssamples = (int)(this.STime*this.sampleRate);
		this.STime = this.Ssamples/(double)this.sampleRate;
		while(Math.abs(Math.sin(2*Math.PI*frequency*this.STime))>=0.0001){
			this.STime+=1/(double)this.sampleRate;
				this.Ssamples++;
		}
	}
	
	private void unclipRTime(){
		this.Rsamples = (int)(this.releaseTime*this.sampleRate);
		this.releaseTime = this.Rsamples/(double)this.sampleRate;
		while(Math.abs(Math.sin(2*Math.PI*frequency*this.releaseTime))>=0.0001){
			this.releaseTime+=1/(double)this.sampleRate;
				this.Rsamples++;
		}
	}
	
	private void generateAttackDecayWave(){
		audioData = new byte[ADsamples];
		int atkTime=(int)(this.attackTime*this.sampleRate);  //number of samples in attack
		//int decTime=(int)ADTime*this.sampleRate-atkTime; //(int)(this.decayTime*this.sampleRate);   //number of samples in decay
		double deltaAmp=this.amplitude/(double)atkTime;      //the rate of growth of amplitude per sample
		double amplitude=0;
		double t=0;
		int i=0;
		for(; i<atkTime; i++){      //attack				
			double sample = amplitude*Math.sin(2 * Math.PI *frequency*t);	//the wave function x=A*Sin(omega*t+phi) combined with phi=0, and omega = 2*Pi*f
			audioData[i]=(byte)(127*sample);
			//if(i<.52*sampleRate&&i>.48*sampleRate)
				//System.out.println(audioData[i]+" "+amplitude+" "+deltaAmp+" "+this.amplitude+" "+atkTime);			
			amplitude+=deltaAmp;   //change over time
			t+=1.0/(double)this.sampleRate;
			testTime=t;
		}
		double a=this.amplitude-this.sustainAmplitude;
		//when amplitude =.0001*A[max value of 1] 127*A=0;
		double k = Math.log(.0001)/(this.ADTime-this.attackTime);
		for(; i<ADsamples; i++){
			double sample = amplitude*Math.sin(2 * Math.PI *frequency*t);
			testSample=sample;
			audioData[i]=(byte)(127*sample);
			t+=1.0/(double)this.sampleRate;
			testTime=t;
			amplitude=a*Math.pow(Math.E,k*(t-this.attackTime))+this.sustainAmplitude;
		}
	}
	private void generateReleaseWave(){
		releaseData=new byte [Rsamples];
		double amplitude=this.amplitude;
		double k = Math.log(.0001)/this.releaseTime;
		double t =0;
		for(int i=0; i<Rsamples; i++){
			double sample = amplitude*Math.sin(2 * Math.PI *frequency*t);
			testSample=sample;
			releaseData[i]=(byte)(127*sample);
			t+=1.0/(double)this.sampleRate;
			testTime=t;
			amplitude=this.sustainAmplitude*Math.pow(Math.E,k*(t));
		}
	}
	
	private void generateSustainWave(){
		sustainData=new byte [Ssamples];
		double amplitude=this.sustainAmplitude;
		double t=0;
		for(int i=0; i<Ssamples; i++){
			double sample = amplitude*Math.sin(2 * Math.PI *frequency*t);
			sustainData[i]=(byte)(127*sample);
			t+=1.0/(double)this.sampleRate;
		}
	}
	
	
	/**
	 * writes the attack/decay data to the audioData byte array
	 */
	
	public void loadAttackDecay(SourceDataLine line){
		try{
			line.write(audioData,0,audioData.length);
			//this.line.drain();
		}/* catch(LineUnavailableException ex){
			System.out.println("could not establish audio line");
			System.exit(25);
		} */catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * writes the release data to the audioData byte array
	 */
	
	public void loadRelease(SourceDataLine line){
		try{
			line.write(releaseData,0,releaseData.length);
			//this.line.drain();
		}/* catch(LineUnavailableException ex){
			System.out.println("could not establish audio line");
			System.exit(25);
		} */catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * writes the sustain data to the audioData byte array
	 */
	
	public void loadSustain(SourceDataLine line){
		try{
			line.write(sustainData,0,sustainData.length);
			//this.line.drain();
		}/* catch(LineUnavailableException ex){
			System.out.println("could not establish audio line");
			System.exit(25);
		} */catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
