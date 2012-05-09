package mimicree.data.fitness;

import java.util.*; 
import mimicree.data.*;


public class AdditiveSNPFitness {
	private final ArrayList<AdditiveSNP> additiveSNPs;
	
	/**
	 * Represents a summary of the additive fitness effects of SNPs
	 * Immutable; Also allows to calculate the additive fitness effect of SNPs
	 * @param addSnps
	 */
	public AdditiveSNPFitness(ArrayList<AdditiveSNP> addSnps)
	{
		this.additiveSNPs=new ArrayList<AdditiveSNP>(addSnps);
	}

	
	/**
	 * Obtain the additive fitness of SNPs for a given diploid genome
	 * @param dipGenome a diploid genome for which to calculate the additive fitness
	 * @return
	 */
	public double getAdditiveFitness(DiploidGenome dipGenome)
	{
		double toret=1.0;
		for(AdditiveSNP snp: additiveSNPs)
		{
			char[] genotype=dipGenome.getSNPGenotype(snp.getPosition());
			double tmpfitness=snp.getFitnessForGenotype(genotype);
			toret*=tmpfitness;
		}
		return toret;
	}
	
	
}
