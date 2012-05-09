package mimicree.data.fitness;

import mimicree.data.*;

/**
 * Immutable representation of a additive effect of a single SNP
 * @author robertkofler
 *
 */
public class AdditiveSNP {
	private final GenomicPosition position;
	private final char w11char;
	private final double s;
	private final double h;
	public AdditiveSNP(GenomicPosition position, char w11char, double s, double h)
	{
		this.position=position;
		this.w11char=w11char;
		this.s=s;
		this.h=h;
	}
	
	/**
	 * Calculate the additive fitness effect for the given genotype. 
	 * The ordering of the alleles is not important
	 * @return
	 */
	public double getFitnessForGenotype(char[] genotype)
	{
		char allele1=genotype[0];
		char allele2=genotype[1];
		if(allele1  == w11char && allele2 == w11char)
		{
			// homozygous for w11 (fitness = 1.0)
			return 1.0;
		}
		else if (allele1 ==w11char || allele2 == w11char)
		{
			// heterozygous  
			return (1.0 - h*s);
		}
		else if((allele1==allele2) && (allele1 != w11char || allele2 != w11char))
		{
			// homozygous for the other allele w22
			return (1.0 - s);
		}
		else
		{
			throw new IllegalArgumentException("Invalid outcome for additive fitness; not valid alleles "+allele1 +" "+ allele2);
		}
	}
	
	/**
	 * Retrieve the genomic position for the given genotype
	 * @return
	 */
	public GenomicPosition getPosition()
	{
		return this.position;
	}
	
	
	
	
}
