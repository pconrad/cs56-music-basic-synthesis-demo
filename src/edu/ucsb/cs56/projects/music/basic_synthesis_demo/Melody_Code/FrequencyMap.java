package edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code;
import java.util.*;

public class FrequencyMap{
	
	private HashMap map;
	
	public FrequencyMap(){
		map = new HashMap();

		map.put("A", new Double(27.5));
		map.put("B", new Double(30.87));
		map.put("C", new Double(16.35));
		map.put("D", new Double(18.35));
		map.put("E", new Double(20.60));
		map.put("F", new Double(21.83));
		map.put("G", new Double(24.50));
	}
   
	public double getFreq(String note)
	{
		double freq = ((Double)map.get(note)).doubleValue();
		return freq;
	}
}
