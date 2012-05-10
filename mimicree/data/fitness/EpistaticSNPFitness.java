package mimicree.data.fitness;

import mimicree.data.*;
import java.util.*;
/**
 * Representation of all epistatic effects
 * @author robertkofler
 *
 */
public class EpistaticSNPFitness {
	private final ArrayList<EpistaticSNP> epiSNPs;
	
	public EpistaticSNPFitness(ArrayList<EpistaticSNP> epiSNPs)
	{
		this.epiSNPs=new ArrayList<EpistaticSNP>(epiSNPs);
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
	
	
	

}
