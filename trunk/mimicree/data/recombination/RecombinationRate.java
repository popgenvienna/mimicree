package mimicree.data.recombination;

import java.util.*;
public class RecombinationRate {
	private final ArrayList<RecombinationWindow> windows;
	
	public RecombinationRate(ArrayList<RecombinationWindow> windows)
	{
		this.windows=new ArrayList<RecombinationWindow>(windows);
	}

}

// Will need a function which creates a Gamete from a Diploid Genome,
