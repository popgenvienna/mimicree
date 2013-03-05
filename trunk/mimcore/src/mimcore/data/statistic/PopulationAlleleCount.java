package mimcore.data.statistic;

import mimcore.data.haplotypes.SNPCollection;
import mimcore.data.haplotypes.SNP;

public class PopulationAlleleCount {
	private final SNPCollection snpcol;
	private final int[] ancestralCount;
	private final int[] derivedCount;
	public PopulationAlleleCount(SNPCollection snpcol, int[] ancestralCount, int[] derivedCount)
	{
		this.snpcol=snpcol;
		this.ancestralCount=ancestralCount;
		this.derivedCount=derivedCount;
	}

	
	public SNPCollection getSNPCollection()
	{
		return this.snpcol;
	}
	
	public int ancestralCount(int index)
	{
		return this.ancestralCount[index];
	}
	
	public int derivedCount(int index)
	{
		return this.derivedCount[index];
	}
	
	
	public int getCount(char toCount, int index)
	{
		int toret=0;
		SNP s=snpcol.getSNPforIndex(index);
		if(s.ancestralAllele()==toCount)
		{
			toret=ancestralCount[index];
		}
		else if(s.derivedAllele()==toCount)
		{
			toret=derivedCount[index];
		}
		return toret;
	}
	
	
	
	
}
