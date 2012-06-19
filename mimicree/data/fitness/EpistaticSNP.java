package mimicree.data.fitness;

import mimicree.data.*;
import java.util.*;



/**
 * Immutable representation of an epistatic effect of several SNPs (>=2)
 * @author robertkofler
 *
 */
public class EpistaticSNP {
	private final String name;
	private final double s; // selection coefficient
	private final ArrayList<EpistaticSubeffectSNP> epiSNPs;
	public EpistaticSNP(String name, double s, ArrayList<EpistaticSubeffectSNP> epiSNPs)
	{
		this.name=name;
		this.s=s;
		
		// Check if the EpistaticSubeffectSNPs are unique
		HashSet<GenomicPosition> posset=new HashSet<GenomicPosition>();
		for(EpistaticSubeffectSNP e:epiSNPs)
		{
			if(posset.contains(e.position())) throw new IllegalArgumentException("Every SNP may only be present once for epistatic effects");
			posset.add(e.position());
		}
		
		this.epiSNPs=new ArrayList<EpistaticSubeffectSNP>(epiSNPs);
	}
	
	/**
	 * Test whether the epistatic effect is present in a given diploid genome
	 * @param genome
	 * @return
	 */
	public boolean isPresent(DiploidGenome genome)
	{
		boolean isPresent=true;
		for(EpistaticSubeffectSNP es: epiSNPs)
		{
			// Test every sub-SNP whether the epistatic effect as a whole is present; 
	
			char[] genotype=genome.getSNPGenotype(es.position());
			boolean present=false;
			for (char c: genotype)
			{	
				// As the epistatic effect is dominant it is sufficient if one of the two alleles of a genotype has it
				if(c == es.epistaticChar()) present =true;
			}
			
			// If a single SNP does not have the epistatic effect the epistatic effect is absent
			if(!present)isPresent=false;
		}
		return isPresent;
	}
	
	/**
	 * Get the epistatic effect for the given genome
	 * if the effect is present: '1.0 - s'
	 * if absent: '1.0'
	 * @param genome
	 * @return
	 */
	public double getEpistaticEffect(DiploidGenome genome)
	{
		if(this.isPresent(genome))
		{
			return (1.0-this.s);
		}
		else
		{
			return 1.0;
		}
	}
	
	public String name()
	{
		return this.name;
	}
	
	public double s()
	{
		return this.s;
	}

	public ArrayList<EpistaticSubeffectSNP> getEpistaticSubeffectSNPs()
	{
		return new ArrayList<EpistaticSubeffectSNP>(this.epiSNPs);
	}
	
	
	public EpistaticSubeffectSNP getEpistaticSubeffectSNP(GenomicPosition position)
	{
		for(EpistaticSubeffectSNP es: this.epiSNPs)
		{
			if(position.equals(es.position()))return es;
		}
		return null;
	}
}


