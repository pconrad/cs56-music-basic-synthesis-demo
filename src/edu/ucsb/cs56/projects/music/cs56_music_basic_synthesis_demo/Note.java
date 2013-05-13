package edu.ucsb.cs56.projects.music.cs56_music_basic_synthesis_demo;

/**
    A Class that represents a Note.
    
    
    @author Bronwyn Perry-Huston
    @version CS56 S13 for project 1 
*/

public class Note{

    private int frequency;
    private int duration;
    private double volume;



    /**
       no-arg constructor of class Note
     */
    public Note()
    {
        //stub
    }

    /**
     * Three-arg Constructor for objects of class Note
     * @param frequency
     * @param duration
     * @param volume
     */
    public Note(int frequency, int duration, double volume)
    {
		//stub
    }


    /**
     * get the frequency of the Note
     * 
     * @return the Frequency of the Note
     */
    public int getFrequency()
    {
		//stub
        return -42;
    }

    /**
     * get the duration of the Note
     * 
     * @return the duration of the Note
     */
    public int getDuration()
    {
		//stub
        return -42;
    }

    /**
     * get the volume of the note
     * 
     * @return the volume of the Note
     */
    public double getVolume()
    {
		//stub
        return -42.1;
    }

    /**
     * set the frequency of the Note
     * 
     * @param frequency the new frequency of the Note
     */
    public void setFrequency(int frequency)
    {
        //stub
    }

    /**
     * set the duration of the Note
     * 
     * @param duration the new duration of the Note
     */
    public void setDuration(int duration)
    {
        //stub
    }

    /**
     * set the volume of the Note
     * 
     * @param volume the new volume of the Note
     */
    public void setVolume(double volume)
    {
       //stub
    }

    /** format Note as String, in an expression like:
     * 
     *  
     *  @return formatted Note
     */
    public String toString()
    {
        return "stub";
    }

    /**
       Check if the Notes are equal
       @param o another object to compare 
       @return true if this object is the same Note as o
     */
    public boolean equals(Object o)
    {
		double tol = 0.01;
	    if (! (o instanceof Note) )
	        return false;
	    Note other = (Note) o;
	    return (other.getFrequency()==this.getFrequency() &
	            other.getDuration()==this.getDuration() &
                Math.abs(other.getVolume() - this.getVolume()) < tol ); 
    }  


}
