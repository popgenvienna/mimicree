package mimicree.data.fitness;

import java.util.*; 
import mimicree.data.*;

/**
 * Represents a summary of the additive fitness effects of SNPs
 * Immutable; Also allows to calculate the additive fitness effect of SNPs
 */
public class AdditiveSNPFitness {
	private final ArrayList<AdditiveSNP> additiveSNPs;
	private final HashMap<GenomicPosition,AdditiveSNP> pos2add;
	

	public AdditiveSNPFitness(ArrayList<AdditiveSNP> addSnps)
	{
		this.pos2add=new HashMap<GenomicPosition,AdditiveSNP>();
		for(AdditiveSNP as: addSnps)
		{
			pos2add.put(as.getPosition(), as);
		}
		this.additiveSNPs=new ArrayList<AdditiveSNP>(addSnps);
	}

	
	/**
	 * Calculate the additive fitness for a given genome
	 * If no additive effects are present, the default of '1.0' will be returned
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
	
	/**
	 * Check if all additive SNPs are fixed
	 * @param population
	 * @return
	 */
	public boolean areAdditiveFixed(Population population)
	{
		for(AdditiveSNP as:this.additiveSNPs)
		{
			if(!population.isFixed(as.getPosition())) return false;
		}
		return true;
	}
	
	
	/**
	 * Get the additive SNP for a given position
	 * @param position
	 * @return
	 */
	public AdditiveSNP getAdditiveforPosition(GenomicPosition position)
	{
		if(!this.pos2add.containsKey(position)) return null;
		return this.pos2add.get(position);
	}
	
	public ArrayList<GenomicPosition> getSelectedPositions()
	{
		return new ArrayList<GenomicPosition>(new HashSet<GenomicPosition>(this.pos2add.keySet()));
	}
	
	
}
