package mimicree.data.statistic;

import mimicree.data.haplotypes.SNPCollection;
import mimicree.data.haplotypes.SNP;

public class PopulationAlleleCount {
	private final SNPCollection snpcol;
	private final int[] majorCount;
	private final int[] minorCount;
	public PopulationAlleleCount(SNPCollection snpcol, int[] majorCount, int[] minorCount)
	{
		this.snpcol=snpcol;
		this.majorCount=majorCount;
		this.minorCount=minorCount;
	}

	
	public SNPCollection getSNPCollection()
	{
		return this.snpcol;
	}
	
	
	public int getCount(char toCount, int index)
	{
		int toret=0;
		SNP s=snpcol.getSNPforIndex(index);
		if(s.majorAllele()==toCount)
		{
			toret=majorCount[index];
		}
		else if(s.minorAllele()==toCount)
		{
			toret=minorCount[index];
		}
		return toret;
	}
	
	
	
	
}
