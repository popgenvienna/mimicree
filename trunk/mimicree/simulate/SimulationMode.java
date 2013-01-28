package mimicree.simulate;

import java.util.*;

public enum SimulationMode {
	Timestamp,
	FixSelected;
	
	
	private ArrayList<Integer> timestamps;
	private boolean abortWhenSelectedFixed;
	
	public void setTimestamps(ArrayList<Integer> timestamps)
	{
		this.timestamps=new ArrayList<Integer>(timestamps);
	}

	public void setAbortWhenSelectedFixed(boolean abortWhenSelectedFixed)
	{
		this.abortWhenSelectedFixed=abortWhenSelectedFixed;
	}

	
	public ArrayList<Integer> getTimestamps()
	{
		return new ArrayList<Integer>(this.timestamps);
	}

	public boolean abortWhenSelectedFixed()
	{
		return this.abortWhenSelectedFixed;
	}


}
