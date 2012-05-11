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
	private final RecombinationRate recRate;
	private final double fitness;
	private final DiploidGenome genome;
	public Specimen(RecombinationRate recRate, double fitness, DiploidGenome genome)
	{
		this.recRate=recRate;
		this.fitness=fitness;
		this.genome=genome;
	}
	

}
