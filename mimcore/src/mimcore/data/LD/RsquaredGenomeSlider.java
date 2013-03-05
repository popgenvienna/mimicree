package data.LD;
import java.util.*;
import data.*;
import data.haplotypes.*;

public class RsquaredGenomeSlider implements IRsquaredIterator {
	private final HaploidGenomeCollection genomes;
	private final int maxDistance;
	private final SNPCollection snpcol;
	
	private int lastStartIndex=0;
	private int lastEndIndex=0;
	
	
	public RsquaredGenomeSlider(ArrayList<HaploidGenome> genomes, int maxDistance)
	{

		this.genomes=new HaploidGenomeCollection(genomes);
		this.maxDistance=maxDistance;
		
		// Extract SNP collection and make sure it is sorted
		SNPCollection snpcol=this.genomes.getNotFixedSNPs();
		assert(snpcol.isSorted());
		this.snpcol=snpcol;
	}
	
	
	/**
	 * Get the next R^2 between two SNPs 
	 */
	public PairwiseRsquared next()
	{
		// Get next pair of SNPs
		LocusPair pair=getNextPair();
		if(pair==null) return null;
		
		// Calculate relevant info
		int distance=pair.distance;
		double rsquared=this.genomes.calculateRsquared(pair.locusA,pair.locusB);
		assert(rsquared>=0.0 && rsquared <=1.0);
		PairwiseRsquared toret=new PairwiseRsquared(pair.locusA,pair.locusB,distance,rsquared);
		return toret;
	}
	
	/**
	 * Iterate over the SNP collection and get pairs of SNPs (Genomic positions)
	 * @return
	 */
	private LocusPair getNextPair()
	{
		if(this.snpcol.size()<2) return null;
		SNP a=this.snpcol.getSNPforIndex(lastStartIndex);
		
		// Eg:  size=3
		// allowed start indices = 0,1 (not 2)
		while(this.lastStartIndex != (this.snpcol.size()-1) )
		{
			this.lastEndIndex++;
			assert(lastEndIndex!=lastStartIndex);
			
			// previous example: allowed = 0,1,2 but not 3
			if(this.lastEndIndex==this.snpcol.size())
			{
				// If the last index exceeds the length -> reset
				lastStartIndex++;
				lastEndIndex=lastStartIndex;
				a=this.snpcol.getSNPforIndex(lastStartIndex);
				continue;
			}
			
			
			SNP b=this.snpcol.getSNPforIndex(lastEndIndex);
			int distance=Math.abs(a.genomicPosition().position() - b.genomicPosition().position());
			if(a.genomicPosition().chromosome().equals(b.genomicPosition().chromosome()) && distance <= this.maxDistance)
			{
				// Found a suitable pair
				return new LocusPair(a.genomicPosition(),b.genomicPosition(),distance);
			}
			else
			{
				lastStartIndex++;
				lastEndIndex=lastStartIndex;
				a=this.snpcol.getSNPforIndex(lastStartIndex);
			}
		}

		// Did not find any remaining suitable pairs
		return null;
	}
	

}


class LocusPair
{
	public LocusPair(GenomicPosition locusA, GenomicPosition locusB, int distance)
	{
		this.locusA=locusA;
		this.locusB=locusB;
		this.distance=distance;
	}
	public final GenomicPosition locusA;
	public final GenomicPosition locusB;
	public final int distance;
}
