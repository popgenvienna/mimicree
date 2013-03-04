package io.fitness;

import data.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class FitnessWriter {
	private BufferedWriter bf;
	private final String outputFile;
	private final Logger logger;
	public FitnessWriter(String outputFile, Logger logger)
	{
		this.logger=logger;
		this.outputFile=outputFile;
		try
		{
			bf=new BufferedWriter(new FileWriter(this.outputFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void write(ArrayList<Specimen> specimens)
	{
		this.logger.info("Starting to write fitness to file " + this.outputFile );
		writeFitness(specimens);
		
		try{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger.info("Finished writing fitness to file");
		
	}
	

	
	/**
	 * Write the inversion-haplotypes of an inversion file
	 * @param inversions
	 */
	private void writeFitness(ArrayList<Specimen> specimens)
	{

		
		for(int i=0,counter=1; i<specimens.size(); i++,counter++)
		{

			Specimen s=specimens.get(i);
			StringBuilder sb=new StringBuilder();
			sb.append(counter);sb.append('\t');
			sb.append(Math.log10(s.fitness())); sb.append('\t');
			sb.append(Math.log10(s.additiveFitness())); sb.append('\t');
			sb.append(Math.log10(s.epistaticFitness()));
			try
			{
				bf.write(sb.toString()+"\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
		
	}
	
	
	
	
}
