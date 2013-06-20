package mimcore.data.fitness;

import mimcore.data.*;
import mimcore.misc.MimicrEERandom;

import java.util.*;

/**
 * Mating function generates couples for mating for a given population of specimen.
 * Mating success is directly proportional to fitness
 * @author robertkofler
 *
 */
public class MatingFunction {
	
	
	/**
	 * accessor method for getting an instance of a mating function
	 * @param population
	 * @return
	 */
	public static MatingFunction getMatingFunction(Population population)
	{
		return new MatingFunction(population);
	}
	
	private ArrayList<FitnessTransformedSpecimen> specimens;
	
	private static class FitnessTransformedSpecimen
	{
		public Specimen specimen;
		public double transformedFitnessSum;
		public int index;
		public FitnessTransformedSpecimen(Specimen specimen, double transformedFitnessSum, int index)
		{
			this.specimen=specimen;
			this.transformedFitnessSum=transformedFitnessSum;
			this.index=index;
		}
	}
	
	
	
	private MatingFunction(Population population)
	{
		
		ArrayList<Specimen> popSpecimen=population.getSpecimen();
		specimens=new ArrayList<FitnessTransformedSpecimen>();
		double fitnessEquivalent=1.0/population.fitnessSum();
		double fitSum=0.0;
		for(int i=0; i<popSpecimen.size(); i++)
		{
			Specimen spec=popSpecimen.get(i);
			double transformedFitness=spec.fitness()*fitnessEquivalent;
			fitSum+=transformedFitness;
			this.specimens.add(new FitnessTransformedSpecimen(spec,fitSum,i));
		}
	}
	
	
	
	
	/**
	 * Choose a couple for mating
	 * @return
	 */
	public Specimen[] getCouple()
	{
		
		FitnessTransformedSpecimen s1=getSpecimenForRandomNumber(MimicrEERandom.getDouble());
		FitnessTransformedSpecimen s2=getSpecimenForRandomNumber(MimicrEERandom.getDouble());
		while(s1.index==s2.index)
		{
			s2=getSpecimenForRandomNumber(MimicrEERandom.getDouble());
		}		
		Specimen[] merryCouple=new Specimen[2];
		merryCouple[0]=s1.specimen;
		merryCouple[1]=s2.specimen;
		return merryCouple;
	}
	
	/**
	 * Get the specimen for a given random number
	 * @param random
	 * @return
	 */
	private FitnessTransformedSpecimen getSpecimenForRandomNumber(double random)
	{
		// 0.1  0.1  0.2  0.2
		// 0.1  0.2  0.4  0.6
		// random 0.01 -> das erste mit 0.1
		// random 0.0 -> das erste mit 0.1
		// random 0.1 -> das zweite mit 0.2
		// random 0.999 -> das letzte mit 1.0! das letzte sollte genau 1 haben
		for(FitnessTransformedSpecimen tspec:this.specimens)
		{
			if(tspec.transformedFitnessSum > random) return tspec;
		}
		throw new IllegalArgumentException("State not allowed in mating function");	
	}
	
	/**
	 * Retrieve a specimen for a given number  [0,1)
	 * @param random
	 * @return
	 */
	public Specimen getSpecimen(double random)
	{
		return this.getSpecimenForRandomNumber(random).specimen;
	}

}
