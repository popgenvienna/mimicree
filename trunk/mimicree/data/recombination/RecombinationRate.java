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
	public ArrayList<GenomicPosition> generateCrossoverEvents()
	{
		ArrayList<GenomicPosition> recEvents=new ArrayList<GenomicPosition>();
		for(RecombinationWindow window:windows)
		{
			if(window.hasRecombinationEvent()) recEvents.add(window.getRandomPosition());
		}
		return recEvents;
	}
	
	/**
	 * Generate random assortment of chromosomes
	 * Note that this only half of recombinaton, the other half are the actual crossover events
	 * @return
	 */
	public HashMap<Chromosome,Integer> generateRandomAssortment()
	{
		
	}
}

// Will need a function which creates a Gamete from a Diploid Genome,
