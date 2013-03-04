package io.inversion;

import mimicree.data.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class InversionWriter {
	private BufferedWriter bf;
	private final String outputFile;
	private final Logger logger;
	public InversionWriter(String outputFile, Logger logger)
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
	
	public void write(ArrayList<InversionHaplotype> inversions)
	{
		this.logger.info("Starting to write inversion-haplotypes to file " + this.outputFile );
		writeInversionDefinition();
		writeInversionHaplotypes(inversions);
		
		try{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger.info("Finished writing inversion-haplotypes to file");
		
	}
	
	/**
	 * Write the definition part of an inversion file
	 */
	private void writeInversionDefinition()
	{
		try{
			bf.write("> Inversion definition\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		for(Inversion inv: Inversion.getInversions())
		{
			String towrite=inv.name()+" = "+inv.chromosome().toString()+":"+inv.start()+"-"+inv.end();
			try
			{
				bf.write(towrite+"\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	/**
	 * Write the inversion-haplotypes of an inversion file
	 * @param inversions
	 */
	private void writeInversionHaplotypes(ArrayList<InversionHaplotype> inversions)
	{
		try{
			bf.write("> Population description\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		for(int i=0, counter=1; i<inversions.size(); i+=2,counter++)
		{
			String toWrite = formatInversionString(counter,inversions.get(i),inversions.get(i+1));
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
		
	}
	
	
	private String formatInversionString(int i, InversionHaplotype i1, InversionHaplotype i2)
	{
		StringBuilder sb=new StringBuilder();
		sb.append(i);
		sb.append("\t");
		sb.append(formatInversionArray(i1.getInversions()));
		sb.append(":");
		sb.append(formatInversionArray(i2.getInversions()));
		return sb.toString();
		
	
	}
	private String formatInversionArray(ArrayList<Inversion> invs)
	{
		if(invs.size()==0) return "-";
		if(invs.size()==1) return invs.get(0).name();
		StringBuilder sb=new StringBuilder();
		sb.append(invs.get(0));
		for(int i=0; i<invs.size(); i++)
		{
			sb.append(",");
			sb.append(invs.get(i).name());
		}
		
		return sb.toString();
		
		
		
	}
	
	
}
