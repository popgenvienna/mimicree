package mimicree.data.recombination;

import java.util.*;
import mimicree.data.*;
public class RecombinationRate {
	private final ArrayList<RecombinationWindow> windows;
	
	public RecombinationRate(ArrayList<RecombinationWindow> windows)
	{
		this.windows=new ArrayList<RecombinationWindow>(windows);
	}

	
	/**
	 * Return a collection of randomly generated crossover events for the whole genome
	 * Note that this is only half of the recombination leading to a gamete
	 * the other half is the random assortment of chromosomes
	 * @return
	 */
	public CrossoverEvents generateCrossoverEvents()
	{
		ArrayList<GenomicPosition> recEvents=new ArrayList<GenomicPosition>();
		for(RecombinationWindow window:windows)
		{
			if(window.hasRecombinationEvent()) recEvents.add(window.getRandomPosition());
		}
		return new CrossoverEvents(recEvents);
	}

}



