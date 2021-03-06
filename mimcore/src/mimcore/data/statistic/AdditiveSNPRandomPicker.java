package mimcore.data.statistic;

import mimcore.data.GenomicPosition;
import mimcore.data.fitness.AdditiveSNP;
import mimcore.data.fitness.AdditiveSNPFitness;
import mimcore.data.haplotypes.*;
import mimcore.misc.MimicrEERandom;

import javax.print.attribute.standard.DateTimeAtCompleted;


import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 3/21/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdditiveSNPRandomPicker {
	private final PopulationAlleleCount pac;
	private final double s;
	private final double h;
	private final int snpCount;
	private final double maxFrequency;
	private final SNPCollection snpCol;
	public AdditiveSNPRandomPicker(PopulationAlleleCount pac, double s, double h, int snpCount, double maxFrequency)
	{
		this.pac=pac;
		this.snpCol=pac.getSNPCollection();
		this.s=s;
		this.h=h;
		this.snpCount=snpCount;
		this.maxFrequency=maxFrequency;
	}


	public AdditiveSNPFitness getAdditiveSNPs()
	{
		ArrayList<AdditiveSNP> positions= this.pickPositions();
		return new AdditiveSNPFitness(positions);
	}

	private ArrayList<AdditiveSNP> pickPositions()
	{

		ArrayList<AdditiveSNP> toret =new ArrayList<AdditiveSNP>();
		HashSet<Integer> sampledPositions=new HashSet<Integer>();

		int successfullCount=0;
		while(successfullCount < this.snpCount)
		{
			int randindex =  (int)(MimicrEERandom.getDouble()* snpCol.size());
			// Discard already sampled positions
			if(sampledPositions.contains((Integer)randindex)) continue;


			SNP snp = snpCol.getSNPforIndex(randindex);
			char w11;
			double frequency;
			if(MimicrEERandom.getDouble() < 0.5) // Ancestral allele
			{

				// selectedAllele=s.ancestralAllele();
				frequency=pac.ancestralFrequency(randindex);
				w11 = snp.derivedAllele();
			}
			else  // Derived allele
			{
				// eg ancestral A=0.8 - derived C=0.2 maxfreq=0.3
				 // selectedAllele=s.derivedAllele();
				frequency=pac.derivedFrequency(randindex);
				w11=snp.ancestralAllele();
			}
			// Discard all where frequency is exceeding the maxFrequency
			if(frequency > this.maxFrequency) continue;

			// OK store the new SNP
			sampledPositions.add((Integer)randindex);
			toret.add(new AdditiveSNP(snp.genomicPosition(),w11,this.s,this.h));
			successfullCount++;
		}
		return toret;
	}







}
