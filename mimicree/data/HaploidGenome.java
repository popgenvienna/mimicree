package mimicree.data;

import mimicree.data.haplotypes.Haplotype;

/**
 * Represents a haploid genome.
 * Consists of the haplotypes of SNPs and of inversions
 * @author robertkofler
 *
 */
public class HaploidGenome {
	private final Haplotype haplotype;
	private final InversionHaplotype invHaplotype;
	public HaploidGenome(Haplotype haplotype, InversionHaplotype invHaplotype)
	{
		this.haplotype=haplotype;
		this.invHaplotype=invHaplotype;
	}
	
	public char getSNPAllele(GenomicPosition position)
	{
		return haplotype.getAllele(position);
	}
	
	public Inversion getInversionAllele(GenomicPosition position)
	{
		return invHaplotype.getGenotype(position);
	}
	
	public Haplotype getSNPHaplotype()
	{
		return this.haplotype;
	}
	
	public InversionHaplotype getInversionHaplotype()
	{
		return this.invHaplotype;
	}
	

}
