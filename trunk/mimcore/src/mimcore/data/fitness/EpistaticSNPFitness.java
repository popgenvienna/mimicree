package mimcore.data.fitness;

import mimcore.data.*;
import java.util.*;
/**
 * Representation of all epistatic effects
 * @author robertkofler
 *
 */
public class EpistaticSNPFitness {
	private final ArrayList<EpistaticSNP> epiSNPs;
	private final HashMap<GenomicPosition,ArrayList<EpistaticSNP>> pos2epi;
	
	public EpistaticSNPFitness(ArrayList<EpistaticSNP> epiSNPs)
	{
				
		this.epiSNPs=new ArrayList<EpistaticSNP>(epiSNPs);
		
	
		pos2epi=new HashMap<GenomicPosition,ArrayList<EpistaticSNP>>();
		for(EpistaticSNP esnp:epiSNPs)
		{
			// Every epistatic effect has to have at least two SNPs
			if(esnp.getEpistaticSubeffectSNPs().size()<2) throw new IllegalArgumentException("At least two SNPs are required for an epistatic SNP");

			for(EpistaticSubeffectSNP episub:esnp.getEpistaticSubeffectSNPs())
			{
				GenomicPosition pos=episub.position();
				if(!pos2epi.containsKey(pos)) pos2epi.put(pos,new ArrayList<EpistaticSNP>());
				pos2epi.get(pos).add(esnp);
			}
			
		}
		
	}
	
	/**
	 * Calculate the sum of all epistatic effects for the given genome;
	 * If no epistatic effects are present or found the result will be: '1.0'
	 * @param genome
	 * @return
	 */
	public double getEpistaticFitness(DiploidGenome genome)
	{
		double toret=1.0;
		for(EpistaticSNP s: epiSNPs)
		{
			toret*=s.getEpistaticEffect(genome);
		}
		return toret;
	}
	
	public ArrayList<EpistaticSNP> getEpistaticSNP(GenomicPosition position)
	{
		if(!this.pos2epi.containsKey(position)) return new ArrayList<EpistaticSNP>();	
		
		// Return the found epistatic effects
		return new ArrayList<EpistaticSNP>(this.pos2epi.get(position));
	} 
	
	/**
	 * test if all SNPs involved in epistatic effects are fixed
	 * @param population
	 * @return
	 */
	public boolean areEpistaticFixed(Population population)
	{
		for(EpistaticSNP e: this.epiSNPs)
		{
			for(EpistaticSubeffectSNP esub: e.getEpistaticSubeffectSNPs())
			{
				if(!population.isFixed(esub.position())) return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the positions of the selected SNPs
	 * @return
	 */
	public ArrayList<GenomicPosition> getSelectedPositions()
	{
		return new ArrayList<GenomicPosition>(new HashSet<GenomicPosition>(this.pos2epi.keySet()));
	}
	
	
	

}
