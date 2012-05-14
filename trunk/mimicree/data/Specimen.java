package mimicree.data;


import mimicree.data.recombination.*;
/**
 * Represent a single specimen which can be imagined as a phenotype.
 * A specimen has a diplod genome, a fitness and a recombination rate
 * Immutable
 * @author robertkofler
 *
 */
public class Specimen {
	private final RecombinationLandscape recLandscape;
	private final double fitness;
	private final DiploidGenome genome;
	public Specimen(RecombinationLandscape recLandscape, double fitness, DiploidGenome genome)
	{
		this.recLandscape=recLandscape;
		this.fitness=fitness;
		this.genome=genome;
	}
	
	
	/**
	 * Get a gamete of the specimen
	 * This 
	 * @return
	 */
	public HaploidGenome getGamete()
	{
		return null;
	}
	

}
