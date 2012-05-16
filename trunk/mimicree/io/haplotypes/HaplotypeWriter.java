package mimicree.io.haplotypes;

import mimicree.data.haplotypes.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class HaplotypeWriter {
	private BufferedWriter bf; 
	private final String outputFile;
	private Logger logger;
	public HaplotypeWriter(String outputFile, Logger logger)
	{
		this.logger=logger;
		try
		{
			bf=new BufferedWriter(new FileWriter(outputFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.outputFile=outputFile;
	}
	
	
	
	public void write(ArrayList<Haplotype> haplotypes)
	{
		this.logger.info("Writing haplotypes to file " + this.outputFile);
		if(!(haplotypes.size()>0)) throw new IllegalArgumentException("Invalid number of haplotypes for output");
	
		SNPCollection scol=haplotypes.get(0).getSNPCollection();
		for(int i=0; i<scol.size(); i++)
		{
			SNP active=scol.getSNPforIndex(i);
			ArrayList<Character> chars=new ArrayList<Character>();
			for(int k=0; k<haplotypes.size(); k++)
			{
				boolean hasMajor=haplotypes.get(k).hasMajor(i);
				if(hasMajor)
				{
					chars.add(active.majorAllele());
				}
				else
				{
					chars.add(active.minorAllele());
				}
			}
			
			String toWrite=formatOutput(active,chars);
			try
			{
				bf.write(toWrite+"\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		
		try{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		this.logger.info("Finished writing "+haplotypes.size() +" haplotypes");
	}
	
	private String formatOutput(SNP snp,ArrayList<Character> chars)
	{
		StringBuilder sb=new StringBuilder();
		//2L      861026    T      A/T    TT AT AA AA TT
		
		sb.append(snp.genomicPosition().chromosome().toString()+"\t");
		sb.append(snp.genomicPosition().position());
		sb.append("\t");
		sb.append(snp.referenceCharacter());
		sb.append("\t");
		sb.append(snp.majorAllele());
		sb.append("/");
		sb.append(snp.minorAllele());
		for(int i=0; i<chars.size(); i+=2){
			sb.append("\t");
			sb.append(chars.get(i));
			sb.append(chars.get(i+1));
		}
		
		return sb.toString();
		
	}
	
}
