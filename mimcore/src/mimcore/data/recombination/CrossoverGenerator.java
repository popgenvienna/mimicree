package mimcore.data.recombination;

import java.util.*;
import mimcore.data.*;
public class CrossoverGenerator {
	private final ArrayList<RecombinationWindow> windows;
	
	public CrossoverGenerator(ArrayList<RecombinationWindow> windows)
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



