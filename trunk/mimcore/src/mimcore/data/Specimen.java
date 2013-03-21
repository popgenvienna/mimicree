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


	private static long specimenCounter=1;


	private static long assignName()
	{
		long name=specimenCounter;
		specimenCounter++;
		return name;
	}


	private final double fitness;
	private final double additiveFitness;
	private final double epistaticFitness;
	private final DiploidGenome genome;
	private final long name;
	private final long mother;
	private final long father;
	public Specimen( double fitness, double additiveFitness, double epistaticFitness, DiploidGenome genome, long mother, long father)
	{
		this.fitness=fitness;
		this.genome=genome;
		this.additiveFitness=additiveFitness;
		this.epistaticFitness=epistaticFitness;
		this.mother = mother;
		this.father = father;
		this.name=assignName();
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

	/**
	 * Get the unique identifier of the specimen
	 * @return
	 */
	public long name()
	{
		return this.name;
	}
	public long mother()
	{return this.mother;}

	public long father()
	{return this.father;}



}
