package data.haplotypes;

import java.util.ArrayList;
import java.util.HashSet;

import data.BitArray.BitArray;
import data.BitArray.BitArrayBuilder;
import data.GenomicPosition;

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
	
	
	public boolean hasMajor(GenomicPosition position)
	{
		int index=snpcollection.getIndexforPosition(position);
		return haplotype.hasBit(index);
	}
	
	/**
	 * Get the allele at a given index
	 * @param index
	 * @return
	 */
	public char getAllele(int index)
	{
		SNP snp=this.snpcollection.getSNPforIndex(index);
		if(hasMajor(index))
		{
			return snp.majorAllele();
		}
		else
		{
			return snp.minorAllele();
		}
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
	
	/**
	 * Get a subset of the haplotypes consisting of the provided collection of SNPs
	 * @param positions
	 * @return
	 */
	public Haplotype getSubHaplotype(ArrayList<GenomicPosition> positions)
	{
		HashSet<GenomicPosition> filter =new HashSet<GenomicPosition>(positions);
		
		BitArrayBuilder bitBuilder=new BitArrayBuilder(filter.size());
		ArrayList<SNP> filteredSNPs=new ArrayList<SNP>();
		int newIndex=0;
		for(int i=0; i<this.snpcollection.size(); i++)
		{
			SNP s=this.snpcollection.getSNPforIndex(i);
			if(filter.contains(s.genomicPosition()))
			{
				filteredSNPs.add(s);
				if(this.hasMajor(i)) bitBuilder.setBit(newIndex);
				newIndex++;
			}
		}
		
		return new Haplotype(bitBuilder.getBitArray(),new SNPCollection(filteredSNPs));
	}
	
}
