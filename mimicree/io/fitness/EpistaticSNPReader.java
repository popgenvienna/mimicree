package mimicree.io.fitness;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import mimicree.data.Chromosome;
import mimicree.data.fitness.*;
import mimicree.data.GenomicPosition;

public class EpistaticSNPReader {
	private BufferedReader bf;
	private String previous=null;
	private Logger logger;
	private String epistaticFile;
	public EpistaticSNPReader(String epistaticFile,Logger logger)
	{
		this.epistaticFile=epistaticFile;
		try
		{
			bf=new BufferedReader(new FileReader(epistaticFile));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger=logger;
	}
	
	
	public EpistaticSNPFitness readEpistaticFitness()
	{
		ArrayList<EpistaticSNP> entrycol=new ArrayList<EpistaticSNP>();
		ArrayList<String> rawentry;
		this.logger.info("Start reading epistatic fitness effects from file "+ this.epistaticFile);
		while((rawentry=getNextEntry())!=null)
		{
			EpistaticSNP entry=parseEntry(rawentry);
			entrycol.add(entry);
			
		}
		
		try
		{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger.info("Finished reading " +entrycol.size() +" epistatic fitness effects");
		return new EpistaticSNPFitness(entrycol);
	}
	
	private EpistaticSNP parseEntry(ArrayList<String> rawentry)
	{
		/*
		 * >0:-0.2
		 * 3R      13478723        A
		 * 2R      18882978        A
		 */
		String header =rawentry.get(0);
		String[] subheader=header.split(":");
		String name=subheader[0].substring(1);
		double s=Double.parseDouble(subheader[1]);
		ArrayList<EpistaticSubeffectSNP> epsnps=new ArrayList<EpistaticSubeffectSNP>();
		for(int i=1; i<rawentry.size(); i++)
		{
			String[] tmp=rawentry.get(i).split("\t");
			GenomicPosition pos=new GenomicPosition(Chromosome.getChromosome(tmp[0]),Integer.parseInt(tmp[1]));
			EpistaticSubeffectSNP es=new EpistaticSubeffectSNP(pos,tmp[2].charAt(0));
			epsnps.add(es);
		}
		// return a new instance of an epistatic effect
		return new EpistaticSNP(name,s,epsnps);
	}
	
	/**
	 * Read the next complete entry; return a list of lines
	 * @return
	 */
	private ArrayList<String> getNextEntry()
	{
		// OMG I hate parsing fasta-like file formats...
		String line;
		ArrayList<String> toret=new ArrayList<String>();
		
		// add the previous entry before even starting with a new one; Only if it is not the first line; in which case no previous entry exited
		if(previous!=null) toret.add(previous);
		
		try
		{
			while((line=bf.readLine())!=null)
			{
				
				if(line.startsWith(">"))
				{
					if(this.previous==null)
					{
						// Consider the first line
						toret.add(line);
						this.previous=line;
					}
					else
					{
						this.previous=line;
						if(!(toret.size()>=3)) throw new IllegalArgumentException("Epistatic SNP effect need to contain at least two distinct SNPs");
						return toret;
					}
				}
				else
				{
					if(line.trim().equals("")) throw new IllegalArgumentException("No empty lines allowed in epistatic effect file");
					toret.add(line);
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		// consider the last line; the last entry is not terminated by a '>'
		if(toret.size()>=3){
			return toret;
		}
		else
		{
			return null;
		}
	}
	
	
	
}
