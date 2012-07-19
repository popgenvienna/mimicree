package mimicree.data.LD;
import java.util.*;
import mimicree.data.*;
import mimicree.data.haplotypes.*;

public class RsquaredGenomeIterator {
	private final ArrayList<HaploidGenome> genomes;
	private final int maxDistance;
	private final SNPCollection snpcol;
	
	private int lastStartIndex=0;
	private int lastEndIndex=0;
	
	
	
	private static class LocusPair
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
		public double pA;
		public double pB;
	}
	
	
	public RsquaredGenomeIterator(ArrayList<HaploidGenome> genomes, int maxDistance)
	{
		if(genomes.size()<1) throw new IllegalArgumentException("At least one genome needs to be provided");
		
		this.genomes=new ArrayList<HaploidGenome>(genomes);
		this.maxDistance=maxDistance;
		
		// Extract SNP collection and make sure it is sorted
		SNPCollection snpcol=genomes.get(0).getSNPHaplotype().getSNPCollection();
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
		double rsquared=calculateRsquared(pair);
		assert(rsquared>=0.0 && rsquared <=1.0);
		PairwiseRsquared toret=new PairwiseRsquared(pair.locusA,pair.locusB,distance,rsquared);
		return toret;
	}
	
	
	private double calculateRsquared(LocusPair pair)
	{
		double pA=pair.pA;
		double pB=pair.pB;
		double pAB=getPAB(pair.locusA,pair.locusB);
		double DAB=pAB-pA*pB;
		double divisor=pA*(1.0-pA)*pB*(1.0-pB);
		double res=Math.pow(DAB, 2)/divisor;
		return res;
	}
	
	private double getPAB(GenomicPosition positionA, GenomicPosition positionB)
	{
		int countMaj=0;
		double total = ((double)this.genomes.size());
		int indexA=this.snpcol.getIndexforPosition(positionA);
		int indexB=this.snpcol.getIndexforPosition(positionB);
		
		for(HaploidGenome g: this.genomes)
		{
			if(g.getSNPHaplotype().hasMajor(indexA) && g.getSNPHaplotype().hasMajor(indexB)) countMaj++;
		}
		return ((double)countMaj)/total;
	}
	
	private double getPx(GenomicPosition position)
	{
		int countMaj=0;
		int index=this.snpcol.getIndexforPosition(position);
		
		double total = ((double)this.genomes.size());
		for(HaploidGenome g: this.genomes)
		{
			if(g.getSNPHaplotype().hasMajor(index)) countMaj++;
		}
		return ((double)countMaj)/total;
	}
	
	
	/**
	 * Get the next pair of SNPs
	 * filter for pairs where both SNPs are not fixed
	 * @return
	 */
	private LocusPair getNextPair()
	{
		
		while(true)
		{
			LocusPair lp = getNextPairRaw();
			if(lp==null) return null;
			double pA=getPx(lp.locusA);
			double pB=getPx(lp.locusB);
			
			// None of the two SNPs are fixed; If one is fixed continue searching for a pair of SNPs that is not fixed;
			if(pA > 0.0 && pA < 1.0 && pB > 0.0 && pB < 1.0)
			{
				lp.pA=pA;
				lp.pB=pB;
				return lp;
			}
		}
	}
	
	/**
	 * Iterate over the SNP collection and get pairs of SNPs (Genomic positions)
	 * @return
	 */
	private LocusPair getNextPairRaw()
	{

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
