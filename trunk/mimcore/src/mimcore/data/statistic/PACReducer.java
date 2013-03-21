package mimcore.data.statistic;

import java.util.*;

import mimcore.data.*;
import mimcore.data.haplotypes.*;

public class PACReducer {

	private final ArrayList<Haplotype> haplotypes;

	public PACReducer(ArrayList<DiploidGenome> genomes)
	{
		this.haplotypes =new ArrayList<Haplotype>();
		for(DiploidGenome dg : genomes)
		{
			haplotypes.add(dg.getHaplotypeA().getSNPHaplotype());
			haplotypes.add(dg.getHaplotypeB().getSNPHaplotype());
		}
	}


	public PACReducer(Population population)
	{
		ArrayList<Specimen> specs=population.getSpecimen();
		this.haplotypes=new ArrayList<Haplotype>();
		for(Specimen s :specs)
		{
			haplotypes.add(s.getGenome().getHaplotypeA().getSNPHaplotype());
			haplotypes.add(s.getGenome().getHaplotypeB().getSNPHaplotype());
		}
	}



	public PopulationAlleleCount reduce()
	{
			return getPAC(haplotypes);
	}

	private PopulationAlleleCount getPAC(ArrayList<Haplotype> haplotypes)
	{
			SNPCollection snpcol=haplotypes.get(0).getSNPCollection();

			int[] ancestralCount=new int[snpcol.size()];
			int[] derivedCount=new int[snpcol.size()];
			for(int i=0; i<snpcol.size(); i++)
			{
				int ancCount=0;
				int derCount=0;
				for(Haplotype h: haplotypes)
				{
					if(h.hasAncestral(i))
					{
						ancCount++;
					}
					else
					{
						derCount++;
					}
				}

				ancestralCount[i]=ancCount;
				derivedCount[i]=derCount;
			}
		return new PopulationAlleleCount(snpcol,ancestralCount,derivedCount);
	}

}
