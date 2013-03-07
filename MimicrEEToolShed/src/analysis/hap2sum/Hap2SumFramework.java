package analysis.hap2sum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import mimicree.data.fitness.FitnessFunction;
import mimicree.data.statistic.*;
import mimicree.io.fitness.FitnessFunctionLoader;
public class Hap2SumFramework {
	private final ArrayList<String> inputFiles;
	private final String outputFile;
	private final String additiveFile;
	private final String epistaticFile;
	private java.util.logging.Logger logger;
	
	public Hap2SumFramework(ArrayList<String> inputFiles, String additiveFile, String epistaticFile, String outputFile, java.util.logging.Logger logger)
	{
		if(inputFiles.size()<1) throw new IllegalArgumentException("At least one haplotype file needs to be provided");
		for(String file: inputFiles)
		{
			if(! new File(file).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+file);
		}
		
		try
		{
			new FileWriter(outputFile);
        }
        catch(IOException e)
        {
        	throw new IllegalArgumentException("Can not create output file:" +outputFile);
        }
		
		this.inputFiles=inputFiles;
		this.additiveFile=additiveFile;
		this.epistaticFile=epistaticFile;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistaticFile,this.logger).loadFitnessFunction();
		ArrayList<PopulationAlleleCount> pac = new mimicree.io.haplotypes.PopulationAlleleCountReader(inputFiles, logger).readPopulations();
		new mimicree.io.misc.SumWriter(this.outputFile, this.logger).write(pac, fitnessFunction);
	}


}
