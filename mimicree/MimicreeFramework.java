package mimicree;


import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import mimicree.data.*;
import mimicree.data.fitness.*;
import mimicree.io.fitness.*;
import mimicree.data.recombination.*;
import mimicree.io.*;
import mimicree.simulate.SingleSimulation;

public class MimicreeFramework {
	private final String haplotypeFile;
	private final String inversionFile;
	private final String recombinationFile;
	private final String chromosomeDefinition;
	private final String additiveFile;
	private final String epistasisFile;
	private final String outputDir;
	private final ArrayList<Integer> outputGenerations;
	private final int replicateRuns;
	private final int threads;

	private final java.util.logging.Logger logger;
	public MimicreeFramework(String haplotypeFile, String inversionFile, String recombinationFile, String chromosomeDefinition, String additiveFile, String epistasisFile,
			String outputDir, ArrayList<Integer> outputGenerations, int replicateRuns,int threads, java.util.logging.Logger logger)
	{
		// 'File' represents files and directories
		// Test if input files exist
		if(! new File(haplotypeFile).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+haplotypeFile);
		if(! new File(recombinationFile).exists()) throw new IllegalArgumentException("Recombination file does not exist " + recombinationFile);
		// Test if output directory exists
		if(! new File(outputDir).exists()) throw new IllegalArgumentException("Output directory does not exist " + outputDir);
		
		// simulations should be done for 'n' generations
		int tmaxgeneration=0;
		for(int i: outputGenerations)
		{
			if(i>tmaxgeneration) tmaxgeneration=i;
		}
		if(!(tmaxgeneration>0)) throw new IllegalArgumentException("Runtime of the simulations must be >0 simulations; Provided by user "+tmaxgeneration);
		// te
		if(!(replicateRuns>0)) throw new IllegalArgumentException("At least one replicate run should be provided; Provided by the user "+replicateRuns);
		if(!(threads>0)) throw new IllegalArgumentException("Number of threads needs to be larger than zero");
		
		this.haplotypeFile=haplotypeFile;
		this.inversionFile=inversionFile;
		this.recombinationFile=recombinationFile;
		this.chromosomeDefinition=chromosomeDefinition;
		this.additiveFile=additiveFile;
		this.epistasisFile=epistasisFile;
		this.outputDir=outputDir;
		this.outputGenerations=new ArrayList<Integer>(outputGenerations);
		this.replicateRuns=replicateRuns;
		this.logger=logger;
		this.threads=threads;
	}
	
	
	public void run()
	{
		this.logger.info("Starting MimicrEE");

		// Load the data
		FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistasisFile,this.logger).loadFitnessFunction();
		ArrayList<DiploidGenome> dipGenomes= new mimicree.io.DiploidGenomeReader(this.haplotypeFile,this.inversionFile,this.logger).readDiploidGenomes();
		RecombinationLandscape recLandscape = new RecombinationLandscape(new RecombinationRateReader(this.recombinationFile,this.logger).getRecombinationRate(),
				new ChromosomeDefinitionReader(this.chromosomeDefinition).getRandomAssortmentGenerator());
		
		// Create initial population
		Population population=Population.loadPopulation(dipGenomes, recLandscape, fitnessFunction);
		
		ExecutorService executor=Executors.newFixedThreadPool(threads);
		ArrayList<Callable<Object>> call=new ArrayList<Callable<Object>>();
		for(int i=0; i<this.replicateRuns; i++)
		{
			call.add(Executors.callable(new SingleSimulation(i+1,population,fitnessFunction,this.outputDir,this.outputGenerations,this.logger)));
		}
		
		this.logger.info("Starting simulations using "+this.threads+" threads");
		try
		{	
			// Run them all!
			executor.invokeAll(call);	
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.logger.info("Finished simulations");
		this.logger.info("Thank you for using MimicrEE");
		
		
	}
}
