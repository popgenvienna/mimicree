package mimicree.data;

import java.util.*;

import mimicree.data.recombination.*;
import mimicree.data.fitness.FitnessFunction;
import mimicree.data.fitness.MatingFunction;

/**
 * A collection of specimen
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
	 * Initialize a new population using a collection of diploid genomes, 
	 * @param genomes
	 * @param recRate
	 * @param fitnessFunction
	 * @return
	 */
	public static Population loadPopulation(ArrayList<DiploidGenome> genomes, RecombinationRate recRate,FitnessFunction fitnessFunction)
	{
		ArrayList<Specimen> specimen=new ArrayList<Specimen>();
		for(DiploidGenome genome: genomes)
		{
			double fitness=fitnessFunction.getFitness(genome);
			Specimen s=new Specimen(recRate,fitness,genome);
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
	public Population getNextGeneration(FitnessFunction fitnessFunction, MatingFunction matingFunction)
	{
		return null;
	}
}
