package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
*	A bit of code which generates an adsr envolope wrapped frequency. 
*	The code first creates a linear climb based on a time ammount specifice in  attackTime,	followed by an exponential decay, at *which point it will maintain the sustain volume. it extends the basic framework for making enveloped sounds presented in *EnvelopedSound
*
*@author Bronwyn Perry-Huston
*@version cs56 S13 project 1
*/

public class ADSREnvelopedContinuousSound extends EnvelopedSound{
    private final boolean DEBUG=false;
    private double amplitude;
    private ADSREnvelope adsr;

 
   /**
       @param double frequency the frequency to play at
       @param double amplitude the maximum volume;
       @param ADSREnvelope adsr an envelope for the sound
       @param int sampleRate the number of samples per second
    */
    public ADSREnvelopedContinuousSound(	
			      double frequency,
			      double amplitude,
			      ADSREnvelope adsr,
			      int sampleRate,
			      SourceDataLine l){
	    super(frequency,adsr.getADSREnvelope(),sampleRate,l);
	    this.adsr = adsr;
	    generateEnvelope();

    }
    public double getAmplitude(){
	    return amplitude;
    }
    public void setAmplitude(double amp){
	    this.amplitude=amp;
    }
 
    public String toString(){
        return super.toString()+" Amplitude: "+this.getAmplitude()+ adsr;
    }




    protected double generateAmplitude(double time, double amplitude){
	    double amp = amplitude;
	    if(time<adsr.getAttackTime()){
	        double deltaAmp=this.amplitude/(getSampleRate()*adsr.getAttackTime());      //the rate of growth of amplitude per sample
	        return amplitude+deltaAmp;
	    }
	    else if(time <adsr.getAttackTime()+adsr.getDecayTime()){
	        double a=amplitude- adsr.getSustainAmplitude();
	        //when amplitude =.0001*A[max value of 1] 127*A=0;
	        double k = Math.log(.001)/(adsr.getDecayTime()*getSampleRate());
	        	    if(DEBUG && time<.26)
			    System.out.println(k+" "+a+" "+adsr.getDecayTime()+" "+Math.log(.001));
	        return amplitude+k*a;
	    }
	    else if(time<adsr.getAttackTime()+adsr.getDecayTime()+adsr.getSustainTime()){
	        return amplitude;
	    }
	    else{
	        //when amplitude =.0001*A[max value of 1] 127*A=0;
	        double k = Math.log(.001)/(adsr.getReleaseTime()*getSampleRate());
	        return amplitude+k*amplitude;
	    }
    }





    /**
     * executes the program
     */
    public static void main(String[] args){
	    if(args.length!=7){
	        System.out.println("frequency, amp, attack, decay, sustain amp, sustain time, release");
	        System.exit(1);
	    }

		double freq = Double.parseDouble(args[0]);
		double amp = Double.parseDouble(args[1]);

	    AudioFormat f= new AudioFormat(44100,8,1,true,true);
	    try{
	        //Create a new audioLine which goes to the system, the audio format specifys all the features of the line.
	        SourceDataLine d = AudioSystem.getSourceDataLine(f);

			System.out.printf("Buffer Size = %d \n", d.getBufferSize());
            ADSREnvelope a = new ADSREnvelope(Double.parseDouble(args[2]),
						            Double.parseDouble(args[3]),
						            Double.parseDouble(args[4]),
						            Double.parseDouble(args[5]),
		                            Double.parseDouble(args[6]));

	        ADSREnvelopedContinuousSound env =
		    new ADSREnvelopedContinuousSound(freq,
						     amp,
						     a,
						     44100,
						     d);
	        d.open(f, 44100*2);
	        d.start();
	        env.load();



			for(int i=0; i<=12; i++){
				freq = freq * Math.pow(2, 1/12.0);
				ADSREnvelopedContinuousSound env2 =
		    			new ADSREnvelopedContinuousSound(freq,
						     amp,
						     a,
						     44100,
						     d);
				env2.load();
				System.out.printf("Freq = %f \n", freq);

			}

	        d.drain();
	    }catch (Exception ex){
	        ex.printStackTrace();
	    }
    }
	

}
