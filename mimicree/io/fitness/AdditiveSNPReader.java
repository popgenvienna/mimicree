package mimicree.io.fitness;

import java.io.*;
import java.util.*;
import mimicree.data.GenomicPosition;
import mimicree.data.Chromosome;
import mimicree.data.fitness.*;
import java.util.logging.Logger;

public class AdditiveSNPReader {
	
	private BufferedReader bf;
	private String additiveFile;
	private Logger logger;
	public AdditiveSNPReader(String additiveFile, Logger logger)
	{
		this.additiveFile=additiveFile;
		try{
			bf=new BufferedReader(new FileReader(additiveFile));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger=logger;
	}
	
	/**
	 * Retrieve the additive fitness effects of SNPs from a file
	 * @return
	 */
	public AdditiveSNPFitness readAdditiveFitness()
	{
		ArrayList<AdditiveSNP> addSNPs=new ArrayList<AdditiveSNP>();
		this.logger.info("Start reading additive fitness effects from file "+this.additiveFile);
		String line;
		try
		{
			while((line=bf.readLine())!=null)
			{
				addSNPs.add(parseLine(line));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
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
		
		
		this.logger.info("Finished reading " + addSNPs.size() + " additive fitness effects of SNPs");
		return new AdditiveSNPFitness(addSNPs);
	}
	
	
	
	private AdditiveSNP parseLine(String line)
	{ 
		//  0		1				2		3		4
		//  X       3929069 		C       -0.2    0.5
		//  3R      23302904        A       -0.2    0.5

		String[] a=line.split("\t");
		GenomicPosition gp=new GenomicPosition(Chromosome.getChromosome(a[0]),Integer.parseInt(a[1]));
		return new AdditiveSNP(gp,a[2].charAt(0),Double.parseDouble(a[3]),Double.parseDouble(a[4]));
		
	}
	

}
