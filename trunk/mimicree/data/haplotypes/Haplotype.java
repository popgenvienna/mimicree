package mimicree.data.haplotypes;

import mimicree.data.BitArray.BitArray;

/**
 * Represents the haplotype of the SNPs
 * Immutable
 * @author robertkofler
 *
 */
public class Haplotype {
	private final SNPCollection snpcollection;
	private final BitArray haplotype;
	
	public Haplotype(BitArray haplotype, SNPCollection snpcollection)
	{
		this.haplotype=haplotype;
		this.snpcollection=snpcollection;
	}
	
}
