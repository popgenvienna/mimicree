package mimicree.data.haplotypes;

import mimicree.data.GenomicPosition;


/**
 * Representation of a SNP;
 * Only the information about IGenomicPositon is relevant (for sorting as well as for hashing
 * 
 * Only two allelic SNPs are permited. Representation is immutable thus may be hashed (only chromosome and position is used for hashing)
 * 
 * @author robertkofler
 *
 */
public class SNP implements Comparable<SNP>{
	private final GenomicPosition genpos;
	private final char referenceCharacter;
	private final char majorAllele;
	private final char minorAllele;
	
	public SNP(GenomicPosition genpos, char referenceCharacter, char majorAllele,char minorAllele)
	{
		this.genpos=genpos;
		this.referenceCharacter=referenceCharacter;
		this.majorAllele=majorAllele;
		this.minorAllele=minorAllele;
	}

	public GenomicPosition genomicPosition()
	{
		return this.genpos;
	}
	
	public char referenceCharacter()
	{
		return this.referenceCharacter;
	}
	public char majorAllele()
	{
		return this.majorAllele;
	}
	public char minorAllele()
	{
		return this.minorAllele;
	}
	
	@Override
	public int compareTo(SNP a){ 
		return this.genomicPosition().compareTo(a.genomicPosition());
	}
	
	
   
	
	

}
