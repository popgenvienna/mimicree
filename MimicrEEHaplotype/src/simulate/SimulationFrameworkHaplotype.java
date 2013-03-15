package simulate;


import java.util.ArrayList;
import java.io.File;
import mimcore.data.*;
import mimcore.data.fitness.*;
import mimcore.io.fitness.*;
import mimcore.data.recombination.*;
import mimcore.io.*;

public class SimulationFrameworkHaplotype {
	private final String haplotypeFile;
	private final String inversionFile;
	private final String recombinationFile;
	private final String chromosomeDefinition;
	private final String additiveFile;
	private final String epistasisFile;
	private final String outputDir;
	private final SimulationMode simMode;
	private final int replicateRuns;



	private final java.util.logging.Logger logger;
	public SimulationFrameworkHaplotype(String haplotypeFile, String inversionFile, String recombinationFile, String chromosomeDefinition, String additiveFile, String epistasisFile,
										String outputDir, SimulationMode simMode, int replicateRuns, java.util.logging.Logger logger)
	{
		// 'File' represents files and directories
		// Test if input files exist
		if(! new File(haplotypeFile).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+haplotypeFile);
		if(! new File(recombinationFile).exists()) throw new IllegalArgumentException("Recombination file does not exist " + recombinationFile);
		// Test if output directory exists
		if(! new File(outputDir).exists()) throw new IllegalArgumentException("Output directory does not exist " + outputDir);
		
		if(!(replicateRuns>0)) throw new IllegalArgumentException("At least one replicate run should be provided; Provided by the user "+replicateRuns);
			
		this.haplotypeFile=haplotypeFile;
		this.inversionFile=inversionFile;
		this.recombinationFile=recombinationFile;
		this.chromosomeDefinition=chromosomeDefinition;
		this.additiveFile=additiveFile;
		this.epistasisFile=epistasisFile;
		this.outputDir=outputDir;
		this.simMode=simMode;
		this.replicateRuns=replicateRuns;
		this.logger=logger;
	}
	
	
	public void run()
	{
		this.logger.info("Starting MimicrEEHaplotype");

		// Load the data
		FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistasisFile,this.logger).loadFitnessFunction();
		ArrayList<DiploidGenome> dipGenomes=new mimcore.io.DiploidGenomeReader(this.haplotypeFile,this.inversionFile,this.logger).readGenomes();
		RecombinationGenerator recGenerator = new RecombinationGenerator(new RecombinationRateReader(this.recombinationFile,this.logger).getRecombinationRate(),
				new ChromosomeDefinitionReader(this.chromosomeDefinition).getRandomAssortmentGenerator());
		
		// Create initial population
		Population population=Population.loadPopulation(dipGenomes, fitnessFunction);
		// Write initial population
		this.logger.info("Writing base population to file");
		new PopulationWriter(population,this.outputDir,0,0,this.logger).write();
		
		ISingleSimulation sim;

		sim=new SingleSimulationTimestamp(population,fitnessFunction,recGenerator,this.outputDir,simMode.getTimestamps(),this.logger);

		

		// Running the replicates
		for(int i=0; i<this.replicateRuns; i++)
		{
			sim.run(i+1);
		}
		
		this.logger.info("Finished simulations");

		
		
	}
}
