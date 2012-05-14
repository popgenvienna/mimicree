package mimicree.data.recombination;

import mimicree.data.*;
import mimicree.data.haplotypes.*;
import java.util.*;
import mimicree.data.*;
import mimicree.data.BitArray.*;


/**
 * Summarizes the recombination rate and the random assortment of chromosomes
 * @author robertkofler
 */
public class RecombinationLandscape {
	private final RecombinationRate recRate;
	private final RandomAssortmentGenerator assortGen;
	
	public RecombinationLandscape(RecombinationRate recRate, RandomAssortmentGenerator assortGen)
	{
		this.recRate=recRate;
		this.assortGen=assortGen;
	}	
	
	/**
	 * Get a randomly recombined gamete using the given Recombination Landscape
	 * This method will return a different HaploidGenome everytime it's called
	 * @param genome
	 * @return
	 */
	public HaploidGenome getGamete(DiploidGenome genome)
	{
		CrossoverEvents crossovers=recRate.generateCrossoverEvents();
		crossovers=crossovers.filterByInversion(genome);
		RandomAssortment randas=assortGen.getRandomAssortment(crossovers);
		
		Haplotype hapSNP=getCrossoverHaplotype(crossovers,randas,genome);
		InversionHaplotype hapInv=getCrossoverInversionHaplotype(crossovers,randas,genome);
		return new HaploidGenome(hapSNP,hapInv);
		
		
	}
	
	private Haplotype getCrossoverHaplotype(CrossoverEvents crossovers,RandomAssortment randas,DiploidGenome genome)
	{
		Haplotype hA=genome.getHaplotypeA().getSNPHaplotype();
		Haplotype hB=genome.getHaplotypeB().getSNPHaplotype();
		SNPCollection scol=hA.getSNPCollection();
		
		// the new haplotype
		BitArrayBuilder newHap=new BitArrayBuilder(scol.size());
		
		Chromosome activeChr=Chromosome.getDefaultChromosome();
		LinkedList<GenomicPosition> activeCrossovers=new LinkedList<GenomicPosition>();
		boolean activeHaplotype=false;
		
		for(int i=0; i<scol.size(); i++)
		{
			// Get the SNP at the given index
			SNP s=scol.getSNPforIndex(i);
			Chromosome chr=s.genomicPosition().chromosome();
			int pos=s.genomicPosition().position();

					
			if(!(chr.equals(activeChr))){
				// the SNP is in a new chromosome -> switch chromosomes
				activeChr=chr;
				activeHaplotype=randas.startWithFirstHaplotype(chr);
				activeCrossovers =crossovers.getCrossovers(chr);
			}
			


			while((!activeCrossovers.isEmpty()) && pos > activeCrossovers.peekFirst().position())
			{
				// a crossover event occured before the SNPs -> switch haplotype
				activeCrossovers.pollFirst();
				activeHaplotype= !activeHaplotype;
			}
			
			
			boolean majorHaplotype=false;
			if(activeHaplotype)
			{
				majorHaplotype=hA.hasMajor(i);
			}
			else
			{
				majorHaplotype=hB.hasMajor(i);
			}
			
			// Set the major haplotype bit if positive
			if(majorHaplotype) newHap.setBit(i);
			// else leave the default => no haplotype
		}
		
		//
		return new Haplotype(newHap.getBitArray(),scol);
		
	}
	

	
	
	private InversionHaplotype getCrossoverInversionHaplotype(CrossoverEvents crossovers,RandomAssortment randas,DiploidGenome genome)
	{
		return null;
	}
	
	

}
