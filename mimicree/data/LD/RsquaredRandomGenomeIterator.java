package mimicree.data.LD;
import java.util.*;


import mimicree.data.*;
import mimicree.data.haplotypes.*;

public class RsquaredRandomGenomeIterator implements IRsquaredIterator{
	private final HaploidGenomeCollection genomes;
	private final int randomSamples;
	private final boolean intraChromosomal;
	private final SNPCollection snpcol;
	private final HashMap<Chromosome,ArrayList<SNP>> chr2snp;
	private int counter=0;
	
	
	
	public RsquaredRandomGenomeIterator(ArrayList<HaploidGenome> genomes, int randomSamples, boolean intraChromosomal)
	{

		this.genomes=new HaploidGenomeCollection(genomes);
		this.randomSamples=randomSamples;
		this.intraChromosomal=intraChromosomal;
		
		// Extract SNP collection and make sure it is sorted
		SNPCollection snpCol=this.genomes.getNotFixedSNPs();
		assert(snpCol.isSorted());
		this.snpcol=snpCol;
		
		chr2snp=new HashMap<Chromosome,ArrayList<SNP>>();
		for(int i=0; i<this.snpcol.size(); i++)
		{
			SNP s=this.snpcol.getSNPforIndex(i);
			Chromosome chr=s.genomicPosition().chromosome();
			if(!chr2snp.containsKey(chr)) chr2snp.put(chr, new ArrayList<SNP>());
			chr2snp.get(chr).add(s);
		}
		
		// Check if every chromosome has at least 2 non-fixed SNPs
		if(intraChromosomal)
		{
			for(Map.Entry<Chromosome, ArrayList<SNP>> e: this.chr2snp.entrySet())
			{
				if(e.getValue().size()<2)throw new IllegalArgumentException("Invalid entries, every chromosome must at least contain two non-fixed SNPs");
			}
		}
	}
	
	
	/**
	 * Get the next R^2 between two SNPs 
	 */
	public PairwiseRsquared next()
	{
		if(counter>=this.randomSamples)return null;
		// Get next pair of SNPs
		LocusPair pair=getNextPair();

		
		// Calculate relevant info
		int distance=pair.distance;
		double rsquared= this.genomes.calculateRsquared(pair.locusA,pair.locusB);
		assert(rsquared>=0.0 && rsquared <=1.0);
		
		this.counter++;
		PairwiseRsquared toret=new PairwiseRsquared(pair.locusA,pair.locusB,distance,rsquared);
		return toret;
		
	}
	
	


	

	
	

	
	/**
	 * Iterate over the SNP collection and get pairs of SNPs (Genomic positions)
	 * @return
	 */
	private LocusPair getNextPair()
	{	
		SNP first=this.snpcol.getSNPforIndex((int)(Math.random()*this.snpcol.size()));
		SNP second;
		do
		{
			
			if(this.intraChromosomal)
			{
				ArrayList<SNP> chrspec=this.chr2snp.get(first.genomicPosition().chromosome());
				second=chrspec.get((int)(Math.random()* chrspec.size()));
			}
			else
			{
				second=this.snpcol.getSNPforIndex((int)(Math.random()*this.snpcol.size()));
			}
			
		}while(first.genomicPosition().equals(second.genomicPosition()));
		int distance=-1;
		if(first.genomicPosition().chromosome().equals(second.genomicPosition().chromosome()))
		{
			distance=Math.abs(first.genomicPosition().position()-second.genomicPosition().position());
		}
		return new LocusPair(first.genomicPosition(),second.genomicPosition(),distance);
	}
	
	
	
	

}
