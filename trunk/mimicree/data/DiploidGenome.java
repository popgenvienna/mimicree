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
	 * Retrieve the genotype a given genomic position
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
	
	/**
	 * Retrieve the inversion-genotype for a given genomic position
	 * An inversion-genotype consists of two inversion states. 
	 * If no inversion is found the given postion, the default inversion-genotype will be returned,
	 * which indicates the absence of any inversion.
	 * @param position
	 * @return
	 */
	public Inversion[] getInversionGenotype(GenomicPosition position)
	{
		Inversion[] toret= new Inversion[2];
		toret[0]=hap1.getInversionAllele(position);
		toret[1]=hap2.getInversionAllele(position);
		return toret;
	}
	
	
	public HaploidGenome getHaplotypeA()
	{
		return this.hap1;
	}
	public HaploidGenome getHaplotypeB()
	{
		return this.hap2;
	}
	
	/**
	 * Get a gamete for the genome
	 * @return
	
	public HaploidGenome getGamete()
	{
		
	}
	 */

}
