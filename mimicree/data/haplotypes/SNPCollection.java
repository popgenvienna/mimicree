package mimicree.data.haplotypes;

import java.util.*;
import mimicree.data.GenomicPosition;

/**
 * Immutable representation of a collection of SNPs.
 * Provides functionality for fast element access via GenomicPosition
 * @author robertkofler
 *
 */
public class SNPCollection {
	private final ArrayList<SNP> mysnps;
	private final HashMap<GenomicPosition,Integer> pos2snp;
	
	public SNPCollection(ArrayList<SNP> snps)
	{
		// First set the internal 
		this.mysnps=new ArrayList<SNP>(snps);
		Collections.sort(this.mysnps);
		
		HashMap<GenomicPosition,Integer> hm=new HashMap<GenomicPosition,Integer>();
		for(int i=0; i<mysnps.size(); i++)
		{
			GenomicPosition gp=mysnps.get(i).genomicPosition();
			hm.put(gp,i);
		}
		this.pos2snp=hm;
	}
	
	
	/**
	 * Get the index of a given GenomicPosition within the SNP collection
	 * @param gp
	 * @return
	 */
	public int getIndexforPosition(GenomicPosition gp)
	{
		if(!this.pos2snp.containsKey(gp)) throw new IllegalArgumentException("Map does not contain key " +gp.toString());
		int toret= this.pos2snp.get(gp);
		return toret;
	}
	
	/**
	 * Get the SNP for a given GenomicPosition;
	 * @param gp
	 * @return
	 */
	public SNP getSNPforPosition(GenomicPosition gp)
	{
		return getSNPforIndex(getIndexforPosition(gp));
	}
	
	
	/**
	 * Get the SNP for a given index within the SNP collection
	 * @param i the index of the SNP within the SNPCollection
	 * @return a SNP
	 */
	public SNP getSNPforIndex(int i)
	{
		return this.mysnps.get(i);
	}
	
	/**
	 * Get the size of the SNP collection
	 * @return
	 */
	public int size()
	{
		return this.mysnps.size();
	}
	
	
	
	
	

}
