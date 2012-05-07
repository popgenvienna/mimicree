package mimicree.io.inversion;

import java.util.*;
import mimicree.data.*;
import mimicree.data.BitArray.*;
class InversionValidator {

	private HashMap<Chromosome,ArrayList<Inversion>> chrInversions;
	public InversionValidator(ArrayList<Inversion> inversions)
	{
		// Map inversions to a chromosome based collection of Inversions
		for(Inversion i: inversions)
		{
			Chromosome chr=i.chromosome();
			if(!chrInversions.containsKey(chr)) chrInversions.put(chr, new ArrayList<Inversion>());	
			chrInversions.get(chr).add(i);	
		}
		
		
	}
	
	/**
	 * test whether any of the given inversions are overlapping; 
	 * @return true when inversions are overlapping
	 */
	public boolean areOverlapping()
	{
		for(Map.Entry<Chromosome,ArrayList<Inversion>> entry : chrInversions.entrySet())
		{
			boolean areOverlapping=overlappingOnChromosome(entry.getValue());
			if(areOverlapping) return true;
		}
		return false;
	}
	
	
	// private member, test overlap for a single chromosome
	private boolean overlappingOnChromosome(ArrayList<Inversion> inversions)
	{
		int endpos=0;
		for(Inversion inv: inversions)
		{
			if(inv.end()>endpos)endpos=inv.end();
		}
		BitArrayBuilder bita=new BitArrayBuilder(endpos);
		
		for(Inversion inv: inversions)
		{
			for(int i=inv.start(); i<=inv.end(); i++)
			{
				if(bita.hasBit(i))return true;
				bita.setBit(i);
			}
		}
		return false;
	}
	
	
}
