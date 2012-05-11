package mimicree.io;

import java.io.*;
import java.util.logging.Logger;
import mimicree.data.recombination.*;
import mimicree.data.Chromosome;
import mimicree.data.GenomicPosition;
import java.util.*;

/**
 * Loads the recombination rate from a file
 * @author robertkofler
 *
 */
public class RecombinationRateReader {
	private BufferedReader bf;
	private String recombinationFile;
	private Logger logger;
	
	/**
	 * Contains a single entry of a file
	 * @author robertkofler
	 *
	 */
	
	
	public RecombinationRateReader(String recombinationFile,Logger logger)
	{
		this.recombinationFile=recombinationFile;
		try
		{
			bf=new BufferedReader(new FileReader(recombinationFile));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	private RecombinationWindow parseLine(String line)
	{
		// 2L:0..100000            0.00            0.00            0.00      
		// 2L:100000..200000       0.00            0.00            0.00      
		// 2L:200000..300000       0.00            0.00            1.89      
		// 2L:300000..400000       1.89            1.92            1.95      
		
		String[] a=line.split("\t");
		String[] tmp1=a[0].split(":");
		String[] tmp2=tmp1[1].split("..");
		
		Chromosome chr=Chromosome.getChromosome(tmp1[0]);
		int start=Integer.parseInt(tmp2[0])+1;
		int end=Integer.parseInt(tmp2[1]);
		double recrate=Double.parseDouble(a[2]);
		return new RecombinationWindow(chr,start,end,recrate);
		
	}
	

	
	
	public RecombinationRate getRecombinationRate()
	{
		
		String line;
		ArrayList<RecombinationWindow> entries=new ArrayList<RecombinationWindow>();
		try
		{
			while((line=bf.readLine())!=null)
			{
				entries.add(parseLine(line));
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
		
		return new RecombinationRate(entries);
		
		
		
		
	}
	
	

}