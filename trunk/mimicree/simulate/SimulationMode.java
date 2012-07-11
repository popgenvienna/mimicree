package mimicree.simulate;

import java.util.*;

public enum SimulationMode {
	Timestamp,
	FixSelected;
	
	
	private ArrayList<Integer> timestamps;
	
	public void setTimestamps(ArrayList<Integer> timestamps)
	{
		this.timestamps=new ArrayList<Integer>(timestamps);
	}
	
	public ArrayList<Integer> getTimestamps()
	{
		return new ArrayList<Integer>(this.timestamps);
	}

}
