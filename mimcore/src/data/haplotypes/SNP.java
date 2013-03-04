package data.haplotypes;

import data.GenomicPosition;


/**
 * Immutable representation of a SNP;
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
		this.referenceCharacter=Character.toUpperCase(referenceCharacter);
		this.majorAllele=Character.toUpperCase(majorAllele);
		this.minorAllele=Character.toUpperCase(minorAllele);
	}

	/**
	 * return the position of the SNP in the genome
	 * @return
	 */
	public GenomicPosition genomicPosition()
	{
		return this.genpos;
	}
	
	/**
	 * return the character in the reference sequence
	 * @return
	 */
	public char referenceCharacter()
	{
		return this.referenceCharacter;
	}
	
	/**
	 * return the major allele of the SNP
	 * @return
	 */
	public char majorAllele()
	{
		return this.majorAllele;
	}
	
	/**
	 * return the minor allele of the SNP
	 * @return
	 */
	public char minorAllele()
	{
		return this.minorAllele;
	}
	
	@Override 
	public int compareTo(SNP a){ 
		return this.genomicPosition().compareTo(a.genomicPosition());
	}
	
	@Override
	public String toString()
	{
		return this.genpos.toString()+" "+this.majorAllele+"/"+this.minorAllele;
	}
	
	
   
	
	

}
