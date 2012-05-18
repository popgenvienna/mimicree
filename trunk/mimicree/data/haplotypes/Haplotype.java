package mimicree.data.haplotypes;

import mimicree.data.BitArray.BitArray;
import mimicree.data.GenomicPosition;

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
		if(haplotype.size()!=snpcollection.size()) throw new IllegalArgumentException("Can not create haplotype; Number of SNPs and number of haplotypes has to be identical");
		this.haplotype=haplotype;
		this.snpcollection=snpcollection;
	}
	
	
	/**
	 * Retrieve the allele at a given position in the genome
	 * @param position
	 * @return
	 */
	public char getAllele(GenomicPosition position)
	{
		int index=snpcollection.getIndexforPosition(position);
		boolean isMajorAllele=haplotype.hasBit(index);
		SNP snp=snpcollection.getSNPforIndex(index);
		
		// Retrieve the major allele at the given position if the bit is set.
		if(isMajorAllele)
		{
			return snp.majorAllele();
		}
		else
		{
			return snp.minorAllele();
		}
	}
	
	/**
	 * Is the major allele set at the given index
	 * @param index
	 * @return
	 */
	public boolean hasMajor(int index)
	{
		return haplotype.hasBit(index);
	}
	
	/**
	 * Return the SNP collection
	 * @return
	 */
	public SNPCollection getSNPCollection()
	{
		return this.snpcollection;
	}
	
	/**
	 * The size of the haplotype
	 * @return
	 */
	public int size()
	{
		return this.haplotype.size();
	}
	
}
