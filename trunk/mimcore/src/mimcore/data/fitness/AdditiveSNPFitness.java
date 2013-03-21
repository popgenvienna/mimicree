package mimcore.data.fitness;

import java.util.*; 
import mimcore.data.*;
import mimcore.data.haplotypes.*;

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
	 * Count the number of additive SNPs that fixed in the correct direction
	 * Additive SNPs need to be fixed, it will throw an error if one of them are not fixed
	 * @param population
	 * @return
	 */
	public int countAdditiveFixedCorrectly(Population population)
	{
		SNPCollection snpCol=population.getSpecimen().get(0).getGenome().getHaplotypeA().getSNPHaplotype().getSNPCollection();

		int countCorrect=0;
		for(AdditiveSNP as:this.additiveSNPs)
		{
			 SNP snp=snpCol.getSNPforPosition(as.getPosition());
			 boolean ancestralFixed=population.isAncestralFixed(as.getPosition());
			 boolean isAncestralSelected=as.isAncestralSelected(snp.ancestralAllele());
				if(ancestralFixed && isAncestralSelected)
				{
					countCorrect++;
				}
				else if( (!ancestralFixed) && (!isAncestralSelected))
				{
					countCorrect++;
				}
		}
		return countCorrect;
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


	public ArrayList<AdditiveSNP> getAdditiveSNPs()
	{
		return new ArrayList<AdditiveSNP>(this.additiveSNPs);
	}

	/**
	 * Get a neutral representation of the additive SNP fitness
	 * Thus setting s=h=0.0
	 * @return
	 */
	public AdditiveSNPFitness getNeutralAdditiveFitness()
	{
		ArrayList<AdditiveSNP> toret =new ArrayList<AdditiveSNP>();
		for(AdditiveSNP as: this.additiveSNPs)
		{
			  AdditiveSNP ns = new AdditiveSNP(as.getPosition(),as.w11Char(),0.0,0.0);
			toret.add(ns);
		}
		return new AdditiveSNPFitness(toret);
	}


	
	
}
