package mimcore.data;


import mimcore.data.recombination.*;
/**
 * Represent a single specimen which can be imagined as a phenotype.
 * A specimen has a diplod genome, a fitness and a recombination rate
 * Immutable
 * @author robertkofler
 *
 */
public class Specimen {
	private final double fitness;
	private final double additiveFitness;
	private final double epistaticFitness;
	private final DiploidGenome genome;
	public Specimen( double fitness, double additiveFitness, double epistaticFitness, DiploidGenome genome)
	{
		this.fitness=fitness;
		this.genome=genome;
		this.additiveFitness=additiveFitness;
		this.epistaticFitness=epistaticFitness;
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
	public HaploidGenome getGamete(RecombinationGenerator recGen)
	{
		RecombinationEvent recEv=recGen.getRecombinationEvent(this.genome);
		return recEv.getGamete(this.genome);
	}
	
	public double fitness()
	{
		return this.fitness;
	}
	
	public double additiveFitness()
	{
		 return this.additiveFitness;
	}
	public double epistaticFitness()
	{
		return this.epistaticFitness;
	}

}
