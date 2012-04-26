package mimicree.data.haplotypes;


/**
 * Representation of a SNP
 * Only two allelic SNPs are permited. Representation is immutable thus may be hashed (only chromosome and position is used for hashing)
 * 
 * @author robertkofler
 *
 */
public class SNP implements Comparable<SNP>{
	private final String chromosome;
	private final int position;
	private final char referenceCharacter;
	private final char majorAllele;
	private final char minorAllele;
	public SNP(String chromosome, int position, char referenceCharacter, char majorAllele,char minorAllele)
	{
		this.chromosome=chromosome;
		this.position=position;
		this.referenceCharacter=referenceCharacter;
		this.majorAllele=majorAllele;
		this.minorAllele=minorAllele;
	}
	public String chromosome()
	{
		return this.chromosome;
	}
	public int position()
	{
		return this.position;
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
	
	
    /**
     * Sort the SNPs by chromosome and than by position
     * @param b the SNP to compare this SNP to
     * @return the sort order
     */
    @Override
    public int compareTo(SNP b)
    {
    	
    	/*
    	 * this | b   | sortcode
    	 * 9    |     | -1
    	 * 10   | 10  |  0
    	 * 11   |     | +1
    	 */
        int chrcomp=this.chromosome.compareTo(b.chromosome());
        if(chrcomp!=0) return chrcomp;
        if(this.position() < b.position()) return -1;
        if(this.position() > b.position()) return 1;
        return 0;   
    }
    
    @Override
    public int hashCode()
    {
        int hc = 17;
        hc=hc * 31 + position;
        hc=hc * 31 + chromosome.hashCode();
        return hc;
    }
    
    @Override
    public boolean equals(Object o)
    {
    	// Robust implementation; First check if the object o is instance of SNP 
    	// than check for equality
        if(!(o instanceof SNP)){return false;}
        SNP tc=(SNP)o; 
        if(tc.position() == this.position() && tc.chromosome().equals(this.chromosome())){return true;}
        return false;
    }
	
	

}
