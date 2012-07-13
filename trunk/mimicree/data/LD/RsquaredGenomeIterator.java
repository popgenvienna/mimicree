package mimicree.data.LD;
import java.util.*;
import mimicree.data.*;
import mimicree.data.haplotypes.*;

public class RsquaredGenomeIterator {
	private final ArrayList<DiploidGenome> genomes;
	private final int maxDistance;
	private final SNPCollection snpcol;
	
	private int lastStartIndex=-1;
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
	}
	
	
	public RsquaredGenomeIterator(ArrayList<DiploidGenome> genomes, int maxDistance)
	{
		if(genomes.size()<1) throw new IllegalArgumentException("At least one genome needs to be provided");
		this.genomes=new ArrayList<DiploidGenome>(genomes);
		this.maxDistance=maxDistance;
		SNPCollection snpcol=genomes.get(0).getHaplotypeA().getSNPHaplotype().getSNPCollection();
		assert(snpcol.isSorted());
		this.snpcol=snpcol;
	}
	
	public PairwiseRsquared next()
	{
		LocusPair pair=getNextPair();
		if(pair==null) return null;
		int distance=pair.distance;
		double rsquared=calculateRsquared(pair);
		PairwiseRsquared toret=new PairwiseRsquared(pair.locusA,pair.locusB,distance,rsquared);
		return toret;
	}
	
	private double calculateRsquared(LocusPair pair)
	{
		return 0.0;
	}
	
	private LocusPair getNextPair()
	{
		return null;
	}
	

}
