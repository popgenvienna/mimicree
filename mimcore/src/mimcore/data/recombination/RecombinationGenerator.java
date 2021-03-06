package mimcore.data.recombination;

import mimcore.data.*;



/**
 * Summarizes the recombination rate and the random assortment of chromosomes
 * @author robertkofler
 */
public class RecombinationGenerator {
	private final CrossoverGenerator recRate;
	private final RandomAssortmentGenerator assortGen;
	
	public RecombinationGenerator(CrossoverGenerator recRate, RandomAssortmentGenerator assortGen)
	{
		this.recRate=recRate;
		this.assortGen=assortGen;
	}	
	
	/**
	 * Obtain a new randomly generated recombination event, including crossovers and random assortments
	 * This method will return a new random recombination event upon each call
	 * @param genome
	 * @return
	 */
	public RecombinationEvent getRecombinationEvent(DiploidGenome genome)
	{
		CrossoverEvents crossovers=recRate.generateCrossoverEvents();
		crossovers=crossovers.filterByInversion(genome);
		RandomAssortment randas=assortGen.getRandomAssortment(crossovers);
		return new RecombinationEvent(randas,crossovers);
	}
	
	

}
