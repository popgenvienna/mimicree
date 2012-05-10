package mimicree.data.fitness;

import mimicree.data.*;

/**
 * Calculate the fitness 
 * @author robertkofler
 *
 */
public class FitnessFunction {
	private final EpistaticSNPFitness epiFitness;
	private final AdditiveSNPFitness addFitness;
	public FitnessFunction(AdditiveSNPFitness addFitness , EpistaticSNPFitness epiFitness)
	{
		this.epiFitness=epiFitness;
		this.addFitness=addFitness;
	}
	 
	
	/**
	 * Calculate the fitness of a genome
	 * Fitness consists of additive fitness and epistatic fitness
	 * @param genome
	 * @return
	 */
	public double getFitness(DiploidGenome genome)
	{
		double toret=1.0;
		toret*=epiFitness.getEpistaticFitness(genome);
		toret*=addFitness.getAdditiveFitness(genome);
		return toret;
	}
	

}
