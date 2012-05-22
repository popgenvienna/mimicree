package mimicree.analysis.measurefitness;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import mimicree.io.fitness.FitnessWriter;

import mimicree.data.DiploidGenome;
import mimicree.data.Population;
import mimicree.data.fitness.FitnessFunction;
import mimicree.io.fitness.FitnessFunctionLoader;

public class AnalyseFitnessFramework {
	private final String haplotypeFile;
	private final String additiveFile;
	private final String epistasisFile;
	private final String outputFile;
	
	public AnalyseFitnessFramework(String haplotypeFile, String additiveFile, String epistasisFile, String outputFile, Logger logger)
	{
		
		if(! new File(haplotypeFile).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+haplotypeFile);
        try{
        	new FileWriter(outputFile);
        }
        catch(IOException e)
        {
        	throw new IllegalArgumentException("Can not create output file:" +outputFile);
        }
		
		this.haplotypeFile=haplotypeFile;
		this.epistasisFile=epistasisFile;
		this.additiveFile=additiveFile;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		this.logger.info("Starting measuring of fitness of population from file "+this.haplotypeFile);
		FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistasisFile,this.logger).loadFitnessFunction();
		ArrayList<DiploidGenome> dipGenomes= new mimicree.io.DiploidGenomeReader(this.haplotypeFile,"",this.logger).readDiploidGenomes();
		Population population=Population.loadPopulation(dipGenomes, fitnessFunction);
		new FitnessWriter(this.outputFile,this.logger).write(population.getSpecimen());
		this.logger.info("Finished measuring fitness");
		this.logger.info("Thank you for using MimicrEE");
		
	}
	private Logger logger;
	
	
	
	
	

}
