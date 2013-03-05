package mimcore.data.recombination;

import java.util.*;

import mimcore.data.Chromosome;
import mimcore.data.GenomicPosition;
import mimcore.data.Inversion;
import mimcore.data.DiploidGenome;

/**
 * Represents a set of crossover events
 * @author robertkofler
 *
 */
public class CrossoverEvents {
	private final ArrayList<GenomicPosition> crossoverEvents;
	private final HashMap<Chromosome,LinkedList<GenomicPosition>> reorganized;
	
	public CrossoverEvents(ArrayList<GenomicPosition> crossoverEvents)
	{
		this.crossoverEvents=new ArrayList<GenomicPosition>(crossoverEvents);
		

		HashMap<Chromosome,LinkedList<GenomicPosition>> toret=new HashMap<Chromosome,LinkedList<GenomicPosition>>();
		
		for(GenomicPosition pos: crossoverEvents)
		{
			if(!toret.containsKey(pos.chromosome()))toret.put(pos.chromosome(),new LinkedList<GenomicPosition>());
			toret.get(pos.chromosome()).add(pos);
		}
		this.reorganized=toret;
	}
	
	/**
	 * Create a subset of the given crossover-events by filtering for those with a valid inversion state
	 * @param genome
	 * @return
	 */
	public CrossoverEvents filterByInversion(DiploidGenome genome)
	{
		ArrayList<GenomicPosition> toret=new ArrayList<GenomicPosition>();
		for(GenomicPosition croso: this.crossoverEvents){
			
			// The crossover event is valid if the inversion state of the two genomes in the given position is equal
			Inversion[] invs=genome.getInversionGenotype(croso);
			if(invs[0].equals(invs[1])) toret.add(croso);
		}
		return new CrossoverEvents(toret);
	}
	
	
	/**
	 * Get a sorted list of crossover events for the given chromosome
	 * @param chromosome
	 * @return
	 */
	public LinkedList<GenomicPosition> getCrossovers(Chromosome chromosome)
	{
		LinkedList<GenomicPosition> toret=new LinkedList<GenomicPosition>();
		if(reorganized.containsKey(chromosome)) toret=new LinkedList<GenomicPosition>(reorganized.get(chromosome));
		Collections.sort(toret);
		return toret;
		
	}
}
