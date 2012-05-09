package mimicree.data;

import mimicree.data.HaploidGenome;


/**
 * Immutable representation of a diploid genome
 * @author robertkofler
 *
 */
public class DiploidGenome {
	private final HaploidGenome hap1;
	private final HaploidGenome hap2;
	

	public DiploidGenome(HaploidGenome hap1, HaploidGenome hap2)
	{
		this.hap1=hap1;
		this.hap2=hap2;
	}
	
	/**
	 * Retrieve the genotype a given genomic Position
	 * @param position
	 * @return
	 */
	public char[] getSNPGenotype(GenomicPosition position)
	{
		char[] toret=new char[2];
		toret[0]=hap1.getSNPAllele(position);
		toret[1]=hap2.getSNPAllele(position);
		return toret;
	}

}
