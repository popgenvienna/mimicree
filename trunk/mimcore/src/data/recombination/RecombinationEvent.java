package data.recombination;

import java.util.ArrayList;
import java.util.LinkedList;

import data.Chromosome;
import data.DiploidGenome;
import data.GenomicPosition;
import data.HaploidGenome;
import data.Inversion;
import data.InversionHaplotype;
import data.BitArray.BitArrayBuilder;
import data.haplotypes.Haplotype;
import data.haplotypes.SNP;
import data.haplotypes.SNPCollection;

public class RecombinationEvent {
	private final CrossoverEvents crossovers;
	private final RandomAssortment randAssort;
	public RecombinationEvent(RandomAssortment randAssort, CrossoverEvents crossovers)
	{
		this.crossovers=crossovers;
		this.randAssort=randAssort;
	}
	
	
	
	/**
	 * Get a randomly recombined gamete for the given RecombinationEvents
	 * @param genome
	 * @return
	 */
	public HaploidGenome getGamete(DiploidGenome genome)
	{
		Haplotype hapSNP=getCrossoverHaplotype(genome.getHaplotypeA().getSNPHaplotype(),genome.getHaplotypeB().getSNPHaplotype());
		InversionHaplotype hapInv=getCrossoverInversionHaplotype(genome.getHaplotypeA().getInversionHaplotype(),genome.getHaplotypeB().getInversionHaplotype());
		return new HaploidGenome(hapSNP,hapInv);
	}
	
	/**
	 * Compute a recombined SNP haplotype
	 * @param crossovers
	 * @param randas
	 * @param genome
	 * @return
	 */
	public Haplotype getCrossoverHaplotype(Haplotype haplotypeA, Haplotype haplotypeB)
	{

		SNPCollection scol=haplotypeA.getSNPCollection();
		
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
				isHaplotypeA=randAssort.startWithFirstHaplotype(chr);
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
				majorHaplotype=haplotypeA.hasMajor(i);
			}
			else
			{
				majorHaplotype=haplotypeB.hasMajor(i);
			}
			
			// Set the major haplotype bit if positive
			if(majorHaplotype) newHap.setBit(i);
			// else leave the default => no haplotype
		}
		return new Haplotype(newHap.getBitArray(),scol);
	}
	

	
	/**
	 * Compute a recombined inversion haplotype
	 * @param haplotypeA
	 * @param haplotypeB
	 * @return
	 */
	public InversionHaplotype getCrossoverInversionHaplotype(InversionHaplotype haplotypeA, InversionHaplotype haplotypeB)
	{

		
		ArrayList<Inversion> newinvHap=new ArrayList<Inversion>();


		for(Inversion inv: Inversion.getInversions())
		{
			Chromosome chr=inv.chromosome();
			int position=inv.start();
			LinkedList<GenomicPosition> actcross=crossovers.getCrossovers(chr);
			
			boolean isHaplotypeA=this.randAssort.startWithFirstHaplotype(chr);
			
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
				if(haplotypeA.hasInversion(inv)) newinvHap.add(inv);
			}
			else
			{
				if(haplotypeB.hasInversion(inv)) newinvHap.add(inv);
			}
		}
		
		return new InversionHaplotype(newinvHap);
	}
	
	
	

}
