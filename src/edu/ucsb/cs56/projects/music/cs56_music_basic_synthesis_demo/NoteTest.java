package edu.ucsb.cs56.projects.music.cs56_music_basic_synthesis_demo;

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
	assertEquals(0,n.getFrequency());
	assertEquals(0,n.getDuration());
    assertEquals(0, n.getVolume());

    }

    /** Test case for Note Three Argument Constructor
        @see Note
    */
    @Test
	public void test_ThreeArgConstructor() {

	Note n = new Note(1,3,0.2);
	assertEquals(1,n.getFrequency());
	assertEquals(2,n.getDuration());
    assertEquals(0.2, n.getVolume());

    }


    /** Test case for Note.getFrequency()
        @see Note
    */
    @Test
	public void test_getFrequency() {
	    assertEquals(1,note.getFrequency());
    }

    /** Test case for Note.getDuration()
        @see Note
    */
    @Test
	public void test_getDuration() {
	    assertEquals(2,note.getDuration());
    }
   
    /** Test case for Note.getVolume()
        @see Note
    */
    @Test
	public void test_getVolume() {
	    assertEquals(0.2, note.getVolume(), 0.01);
    }

    /** Test case for Note.setFrequency()
        @see Note
    */
    @Test
	public void test_setFrequency() {
        note.setFrequency(3);
	    assertEquals(3,note.getFrequency());
    }

    /** Test case for Note.setDuration()
        @see Note
    */
    @Test
	public void test_setDuration() {
        note.setDuration(3);
	    assertEquals(3,note.getDuration());
    }
   
    /** Test case for Note.setVolume()
        @see Note
    */
    @Test
	public void test_setVolume() {
        note.setVolume(0.4);
	    assertEquals(0.4,note.getVolume(), 0.01);
    }


    /** Test case for Note.toString()
        @see Note
    */    
    @Test
    public void test_toString(){
        assertEquals("Frequency:1, Duration:2, Volume:0.1",note.toString());
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
