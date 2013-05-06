package edu.ucsb.cs56.S12.ISSUE0000779.old;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


/*
A test class for Synth.java
@author Daniel Imberman and Scott Cesar
@version 2012.16.05
@see Synth

*/



public class synthTest {

    /** Test case for the time values of the synth
    @see synth
   */

    @Test
	public void testTotalTime() {
		ADSREnvelopedSound test = new ADSREnvelopedSound(400,1,.5,.5,.1,.1,44100);
		double a=test.totalTime();  
		assertEquals(a,.7,.02);
    	
    }
    /** Test case for the wave values of the synth, showing we are getting a perfect sin wave
    @see synth
   */
	
    @Test
	public void testwave1() {
		ADSREnvelopedSound test = new ADSREnvelopedSound(1,1,.5,1,.1,.1,44100);
		long a = (long)test.wavValAtTime(.25);
		long b = (long)test.wavValAtTime(.5);
		long c = (long)test.wavValAtTime(.75);
		
		assertEquals(a, 31);	
		assertEquals(b, 0);
		assertEquals(c ,-95);
		
		
    }


    /** Test case for total time of synth made 
     @see synth
    */
   




    


}
