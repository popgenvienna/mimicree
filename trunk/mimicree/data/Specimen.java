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
	private final RecombinationGenerator recLandscape;
	private final double fitness;
	private final DiploidGenome genome;
	public Specimen(RecombinationGenerator recLandscape, double fitness, DiploidGenome genome)
	{
		this.recLandscape=recLandscape;
		this.fitness=fitness;
		this.genome=genome;
	}
	
	
	public DiploidGenome getGenome()
	{
		return this.genome;
	}
	
	/**
	 * Obtain a gamete from the given Specimen i.e.: get a semen or an egg
	 * The gamete is a haploid and recombined product of the diploid genome
	 * @return
	 */
	public HaploidGenome getGamete()
	{
		RecombinationEvent recEv=recLandscape.getRecombinationEvent(this.genome);
		return recEv.getGamete(this.genome);
	}
	
	public double fitness()
	{
		return this.fitness;
	}
	
	public RecombinationGenerator recombinationLandscape()
	{
		return this.recLandscape;
	}

}
