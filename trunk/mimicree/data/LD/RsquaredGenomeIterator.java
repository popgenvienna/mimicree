package mimicree.data.LD;
import java.util.*;
import mimicree.data.*;
import mimicree.data.haplotypes.*;

public class RsquaredGenomeIterator {
	private final ArrayList<DiploidGenome> genomes;
	private final int maxDistance;
	private final SNPCollection snpcol;
	
	private int lastStartIndex=0;
	private int lastEndIndex=1;
	
	
	
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
	}
	
	
	public RsquaredGenomeIterator(ArrayList<DiploidGenome> genomes, int maxDistance)
	{
		if(genomes.size()<1) throw new IllegalArgumentException("At least one genome needs to be provided");
		
		this.genomes=new ArrayList<DiploidGenome>(genomes);
		this.maxDistance=maxDistance;
		
		// Extract SNP collection and make sure it is sorted
		SNPCollection snpcol=genomes.get(0).getHaplotypeA().getSNPHaplotype().getSNPCollection();
		assert(snpcol.isSorted());
		this.snpcol=snpcol;
	}
	
	
	
	public PairwiseRsquared next()
	{
		// Get next pair of SNPs
		LocusPair pair=getNextPair();
		if(pair==null) return null;
		
		// Calculate relevant info
		int distance=pair.distance;
		double rsquared=calculateRsquared(pair);
		PairwiseRsquared toret=new PairwiseRsquared(pair.locusA,pair.locusB,distance,rsquared);
		return toret;
	}
	
	private double calculateRsquared(LocusPair pair)
	{
		double pA=getPx(pair.locusA);
		double pB=getPx(pair.locusB);
		double pAB=getPAB(pair.locusA,pair.locusB);
		double DAB=pAB-pA*pB;
		double divisor=pA*(1.0-pA)*pB*(1.0-pB);
		double res=Math.pow(DAB, 2)/divisor;
		return res;
	}
	
	private double getPAB(GenomicPosition positionA, GenomicPosition positionB)
	{
		int countMaj=0;
		double total = ((double)this.genomes.size())*2.0;
		for(DiploidGenome g: this.genomes)
		{
			if(g.getHaplotypeA().hasMajor(positionA) && g.getHaplotypeA().hasMajor(positionB)) countMaj++;
			if(g.getHaplotypeB().hasMajor(positionA) && g.getHaplotypeB().hasMajor(positionB)) countMaj++;
		}
		return ((double)countMaj)/total;
	}
	
	private double getPx(GenomicPosition position)
	{
		int countMaj=0;
		double total = ((double)this.genomes.size())*2.0;
		for(DiploidGenome g: this.genomes)
		{
			if(g.getHaplotypeA().hasMajor(position)) countMaj++;
			if(g.getHaplotypeB().hasMajor(position)) countMaj++;
		}
		return ((double)countMaj)/total;
	}
	
	/**
	 * Iterate over the SNP collection and get pairs of SNPs (Genomic positions)
	 * @return
	 */
	private LocusPair getNextPair()
	{
		// lastStartIndex=0;
		// lastEndIndex=0;
		SNP a=this.snpcol.getSNPforIndex(lastStartIndex);
		SNP b=null;
		
		while((lastEndIndex+1) < this.snpcol.size())
		{
			lastEndIndex++;
			assert(lastEndIndex!=lastStartIndex);
			
			b=this.snpcol.getSNPforIndex(lastEndIndex);
			
			int distance=Math.abs(a.genomicPosition().position()-b.genomicPosition().position());
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
