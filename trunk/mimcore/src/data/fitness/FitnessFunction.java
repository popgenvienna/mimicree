package data.fitness;

import data.*;
import java.util.*;

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
		toret*=this.getEpistaticFitness(genome);
		toret*=this.getAdditiveFitness(genome);
		return toret;
	}
	
	public double getAdditiveFitness(DiploidGenome genome)
	{
		return addFitness.getAdditiveFitness(genome);
	}
	
	public double getEpistaticFitness(DiploidGenome genome)
	{
		return epiFitness.getEpistaticFitness(genome);
	}
	
	/**
	 * Obtain a new specimen
	 * @param genome
	 * @return
	 */
	public Specimen getSpecimen(DiploidGenome genome)
	{
		double addi=getAdditiveFitness(genome);
		double epi=getEpistaticFitness(genome);
		double fitness=addi*epi;
		return new Specimen(fitness,addi,epi,genome);
	}
	
	public AdditiveSNPFitness getAdditiveSNPFitness()
	{
		return this.addFitness;
	}
	public EpistaticSNPFitness getEpistaticSNPFitness()
	{
		return this.epiFitness;
	}
	
	/**
	 * Get the positions of the selected SNPs
	 * @return
	 */
	public ArrayList<GenomicPosition> getSelectedPositions()
	{
		HashSet<GenomicPosition> toret=new HashSet<GenomicPosition>();
		toret.addAll(this.addFitness.getSelectedPositions());
		toret.addAll(this.epiFitness.getSelectedPositions());
		return new ArrayList<GenomicPosition>(toret);
	}

}
