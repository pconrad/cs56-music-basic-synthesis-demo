package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

/** Test class for Note

 * @author Bronwyn Perry-Huston
 * @version cs56 13S for project 1
 * @see Note

*/

public class NoteTest {


    private Note note;
	private double tol = 0.01;

    @Before
    public void initNote() {
	this.note = new Note(1,2,0.1);
    }

    /** Test case for Note No Argument Constructor
        @see Note
    */
    @Test
	public void test_NoArgConstructor() {

	Note n = new Note();
	assertEquals(0,n.getFrequency(), tol);
	assertEquals(1,n.getDuration(), tol);
    assertEquals(0, n.getVolume(), tol);

    }

    /** Test case for Note Three Argument Constructor
        @see Note
    */
    @Test
	public void test_ThreeArgConstructor() {

	Note n = new Note(1,3,0.2);
	assertEquals(1,n.getFrequency(), tol);
	assertEquals(3,n.getDuration(), tol);
    assertEquals(0.2, n.getVolume(), tol);

    }


    /** Test case for Note.getFrequency()
        @see Note
    */
    @Test
	public void test_getFrequency() {
	    assertEquals(1,note.getFrequency(), tol);
    }

    /** Test case for Note.getDuration()
        @see Note
    */
    @Test
	public void test_getDuration() {
	    assertEquals(2,note.getDuration(), tol);
    }
   
    /** Test case for Note.getVolume()
        @see Note
    */
    @Test
	public void test_getVolume() {
	    assertEquals(0.1, note.getVolume(), tol);
    }

    /** Test case for Note.setFrequency()
		Test for valid input
        @see Note
    */
    @Test
	public void test_setFrequencyValid() {
        note.setFrequency(3);
	    assertEquals(3,note.getFrequency(), tol);
    }

    /** Test case for Note.setDuration()
		Test for valid input
        @see Note
    */
    @Test
	public void test_setDurationValid() {
        note.setDuration(3);
	    assertEquals(3,note.getDuration(), tol);
    }
   
    /** Test case for Note.setVolume()
		Test for valid input
        @see Note
    */
    @Test
	public void test_setVolumeValid() {
        note.setVolume(0.4);
	    assertEquals(0.4,note.getVolume(), tol);
    }


 /** Test case for Note.setFrequency()
		Test for non valid input
        @see Note
    */
    @Test (expected = IllegalArgumentException.class)
	public void test_setFrequencyNonValid() {
        		note.setFrequency(-3);
    }

    /** Test case for Note.setDuration()
		Test for nonvalid input
        @see Note
    */
    @Test (expected = IllegalArgumentException.class)
	public void test_setDurationNonValid() {
        note.setDuration(0);
    }
   
    /** Test case for Note.setVolume()
		Test for non-valid input
        @see Note
    */
    @Test  (expected = IllegalArgumentException.class)
	public void test_setVolumeNonValid() {
        note.setVolume(12);
    }



    /** Test case for Note.toString()
        @see Note
    */    
    @Test
    public void test_toString(){
        assertEquals("Frequency:1.0, Duration:1/2.0 beats, Volume:0.1",note.toString());
    }

    /** Test case for Note.equals()
        @see Note
    */
    @Test
    public void test_equals()
    {
        Note n = new Note(1,2,0.1);
        assertTrue(note.equals(n));
    }

}
