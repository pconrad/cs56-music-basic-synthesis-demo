package edu.ucsb.cs56.projects.music.basic_synthesis_demo;

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
        this.frequency = 0;
		this.duration = 1;
		this.volume= 0;
    }

    /**
     * Three-arg Constructor for objects of class Note
     * @param frequency
     * @param duration
     * @param volume
     */
    public Note(int frequency, int duration, double volume)
    {
		if( frequency < 0 || duration<= 0 || volume < 0 || volume > 1)
			throw new IllegalArgumentException("Invalid Input");
		else{
			this.frequency = frequency;
			this.duration = duration;
			this.volume = volume;
		}
    }


    /**
     * get the frequency of the Note
     * 
     * @return the Frequency of the Note
     */
    public int getFrequency()
    {	
        return this.frequency;
    }

    /**
     * get the duration of the Note
     * 
     * @return the duration of the Note
     */
    public int getDuration()
    {
        return this.duration;
    }

    /**
     * get the volume of the note
     * 
     * @return the volume of the Note
     */
    public double getVolume()
    {
        return this.volume;
    }

    /**
     * set the frequency of the Note
     * 
     * @param frequency the new frequency of the Note
     */
    public void setFrequency(int frequency)
    {
        if( frequency < 0)
			throw new IllegalArgumentException("Frequency must be greater than or equal to 0");
		else
			this.frequency = frequency;
    }

    /**
     * set the duration of the Note
     * 
     * @param duration the new duration of the Note
     */
    public void setDuration(int duration)
    {
        if( duration <= 0)
			throw new IllegalArgumentException("Duration must be greater than 0");
		else
			this.duration = duration;
    }

    /**
     * set the volume of the Note
     * 
     * @param volume the new volume of the Note
     */
    public void setVolume(double volume)
    {
       if( volume < 0 || volume > 1)
			throw new IllegalArgumentException("Volume must be between 0 and 1 inclusive");
		else
			this.volume = volume;
    }

    /** format Note as String, in an expression like:
     * 
     *  
     *  @return formatted Note
     */
    public String toString()
    {
        return "Frequency:"+ this.frequency + ", Duration:1/"+this.duration+ " beats, Volume:" + this.volume;
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
