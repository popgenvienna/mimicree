package mimicree.data;

import java.util.*;

import mimicree.data.recombination.*;
import mimicree.data.fitness.FitnessFunction;
import mimicree.data.fitness.MatingFunction;

/**
 * A population
 * is a collection of specimen (individuals)
 * @author robertkofler
 *
 */
public class Population {
	private final ArrayList<Specimen> specimen;
	
	public Population(ArrayList<Specimen> specimen)
	{
		this.specimen=new ArrayList<Specimen>(specimen);
	}
	/**
	 * Initialize a new population using a collection of diploid genomes, the recombination rate and a fitness function
	 * @param genomes a collection of diploid genomes which will constitute the final population
	 * @param recRate the recombination rate every specimen will have the same recombination rate
	 * @param fitnessFunction the fitness function will decided the fitness of a specimen
	 * @return
	 */
	public static Population loadPopulation(ArrayList<DiploidGenome> genomes,FitnessFunction fitnessFunction)
	{
		ArrayList<Specimen> specimen=new ArrayList<Specimen>();
		for(DiploidGenome genome: genomes)
		{
			Specimen s=fitnessFunction.getSpecimen(genome);
			specimen.add(s);
		}
		
		// Create a new population
		return new Population(specimen);
	}

	
	/**
	 * Obtain the next generation of the population;
	 * The next generation will depend on the environment as the environment determines
	 * the fitness of any specimen and thus it's mating success
	 * @param environment 
	 * @return
	 */
	public Population getNextGeneration(FitnessFunction fitnessFunction, RecombinationGenerator recGenerator)
	{
		MatingFunction mf=MatingFunction.getMatingFunction(this);
		ArrayList<Specimen> nextGen=new ArrayList<Specimen>();
		
		for(int i=0; i<this.size(); i++)
		{
			Specimen[] merryCouple= mf.getCouple();
			HaploidGenome semen	=merryCouple[0].getGamete(recGenerator);
			HaploidGenome egg	=merryCouple[1].getGamete(recGenerator);
			DiploidGenome fertilizedEgg=new DiploidGenome(semen,egg);
			Specimen progeny=fitnessFunction.getSpecimen(fertilizedEgg);
			nextGen.add(progeny);
		}
		return new Population(nextGen);
		
	}
	
	/**
	 * Retrieve the specimen constituting a population
	 * @return
	 */
	public ArrayList<Specimen> getSpecimen()
	{
		return new ArrayList<Specimen>(this.specimen);
	}
	
	/**
	 * Return the sum of the fitness of all specimen for the given population
	 * @return
	 */
	public double fitnessSum()
	{
		double toret=0;
		for(Specimen spec: this.specimen)
		{
			toret+=spec.fitness();
		}
		return toret;
	}
	
	public int size()
	{
		return this.specimen.size();
	}
			
	
}
