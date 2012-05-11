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
	public static Population loadPopulation(ArrayList<DiploidGenome> genomes, RecombinationRate recRate,FitnessFunction fitnessFunction)
	{
		ArrayList<Specimen> specimen=new ArrayList<Specimen>();
		for(DiploidGenome genome: genomes)
		{
			double fitness=fitnessFunction.getFitness(genome);
			Specimen s=new Specimen(recRate,fitness,genome);
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
	public Population getNextGeneration(FitnessFunction fitnessFunction, MatingFunction matingFunction)
	{
		return null;
	}
}
