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
	
	/**
	 * get a SNP gamete, incorporating the crossovers
	 * @param crossovers
	 * @param randas
	 * @param genome
	 * @return
	 */
	private Haplotype getCrossoverHaplotype(CrossoverEvents crossovers,RandomAssortment randas,DiploidGenome genome)
	{
		Haplotype hA=genome.getHaplotypeA().getSNPHaplotype();
		Haplotype hB=genome.getHaplotypeB().getSNPHaplotype();
		SNPCollection scol=hA.getSNPCollection();
		
		// the new haplotype
		BitArrayBuilder newHap=new BitArrayBuilder(scol.size());
		
		Chromosome activeChr=Chromosome.getDefaultChromosome();
		LinkedList<GenomicPosition> activeCrossovers=new LinkedList<GenomicPosition>();
		boolean isHaplotypeA=false;
		
		for(int i=0; i<scol.size(); i++)
		{
			// Get the SNP at the given index
			SNP s=scol.getSNPforIndex(i);
			Chromosome chr=s.genomicPosition().chromosome();
			int position=s.genomicPosition().position();

			if(!(chr.equals(activeChr))){
				// the SNP is in a new chromosome -> switch chromosomes
				activeChr=chr;
				isHaplotypeA=randas.startWithFirstHaplotype(chr);
				activeCrossovers =crossovers.getCrossovers(chr);
			}
			// SNPs at 30 and 31; Crossover at 30
			// Start haplotype=A     => 30=A 31=B => thus crossover at 30 occurs directly after base
			while((!activeCrossovers.isEmpty()) && activeCrossovers.peekFirst().position() < position)
			{
				// a crossover event occured before the SNPs -> switch haplotype
				activeCrossovers.pollFirst();
				isHaplotypeA= !isHaplotypeA;
			}
			
			boolean majorHaplotype=false;
			if(isHaplotypeA)
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
		return new Haplotype(newHap.getBitArray(),scol);
	}
	

	
	/**
	 * Compute the new inversion-haplotype considering a parent, a random assortment and given crossover events
	 * create a inversion gamete (incorporating the crossovers)
	 * @param crossovers
	 * @param randas
	 * @param genome
	 * @return
	 */
	private InversionHaplotype getCrossoverInversionHaplotype(CrossoverEvents crossovers,RandomAssortment randas,DiploidGenome genome)
	{
		InversionHaplotype iA=genome.getHaplotypeA().getInversionHaplotype();
		InversionHaplotype iB=genome.getHaplotypeB().getInversionHaplotype();
		
		ArrayList<Inversion> newinvHap=new ArrayList<Inversion>();


		for(Inversion inv: Inversion.getInversions())
		{
			Chromosome chr=inv.chromosome();
			int position=inv.start();
			LinkedList<GenomicPosition> actcross=crossovers.getCrossovers(chr);
			
			boolean isHaplotypeA=randas.startWithFirstHaplotype(chr);
			
			// recombinations at 10 20 30  inversion at 31
			// starting with hapA -> B -> A -> B  
			// all three recombinations are smaller than the inversion at 31; inversion at 31 is thus at haplotype B
			while((!actcross.isEmpty()) &&  actcross.peekFirst().position() < position)
			{
				// a crossover event occured before the SNPs -> switch haplotype
				actcross.pollFirst();
				isHaplotypeA= !isHaplotypeA;
			}
			
			if(isHaplotypeA)
			{
				if(iA.hasInversion(inv)) newinvHap.add(inv);
			}
			else
			{
				if(iB.hasInversion(inv)) newinvHap.add(inv);
			}
		}
		
		return new InversionHaplotype(newinvHap);
	}
	
	

}
