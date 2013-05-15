package edu.ucsb.cs56.S12.ISSUE0000779.old;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
	/**
	 * 
	 * Starts a thread which will load the attack/decay routine, then block loading the sustain
	 *  routine until interrupted at which point it willl load the release routine.
	 */
	public class AudioLoader implements Runnable{
		SourceDataLine loadMe;
		ADSREnvelopedSound loader;
		Thread owner;
		public AudioLoader(SourceDataLine audioFeed, ADSREnvelopedSound loader){
			loadMe=audioFeed;
			this.loader=loader;
		}
		public void run(){
			try{
			loader.loadAttackDecay(loadMe);
				while(true)
				{
					loader.loadSustain(loadMe);
					Thread.sleep(330);
				}
			} catch(InterruptedException ex){
				loadMe.flush();
				loader.loadRelease(loadMe);
				loadMe.drain();
			}
		}
	}
