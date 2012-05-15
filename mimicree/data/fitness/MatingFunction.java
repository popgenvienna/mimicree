package mimicree.data.fitness;

import mimicree.data.*;
import java.util.*;

/**
 * Mating function generates couples for mating for a given population of specimen.
 * Mating success is directly proportional to fitness
 * @author robertkofler
 *
 */
public class MatingFunction {
	
	private ArrayList<FitnessTransformedSpecimen> specimens;
	
	private static class FitnessTransformedSpecimen
	{
		public Specimen specimen;
		public double transformedFitness;
		public FitnessTransformedSpecimen(Specimen specimen, double transformedFitness)
		{
			this.specimen=specimen;
			this.transformedFitness=transformedFitness;
		}
	}
	
	private int getFitnessSum()
	
	
	public MatingFunction(Population population)
	{
		ArrayList<Specimen> popSpecimen=population.
	}
	
	/**
	 * Choose a couple for mating
	 * @return
	 */
	public Specimen[] getCouple()
	{
		return null;
	}

}
