package simulate;

import mimcore.data.*;
import mimcore.data.fitness.*;
import mimcore.data.statistic.PopulationAlleleCount;
import mimcore.io.fitness.*;
import mimcore.data.recombination.*;
import mimcore.io.*;
import mimcore.io.misc.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationFrameworkSummary {
	private final String haplotypeFile;
	private final String recombinationFile;
	private final String chromosomeDefinition;
	private final String additiveFile;
	private final String epistasisFile;
	private final String outputFile;
	private final OutputFileType outputFileType;
	private final SimulationMode simMode;
	private final int replicateRuns;



	private final java.util.logging.Logger logger;
	public SimulationFrameworkSummary(String haplotypeFile, String recombinationFile, String chromosomeDefinition, String additiveFile, String epistasisFile,
									  String outputFile,OutputFileType outputFileType, SimulationMode simMode, int replicateRuns, java.util.logging.Logger logger)
	{
		// 'File' represents files and directories
		// Test if input files exist
		if(! new File(haplotypeFile).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+haplotypeFile);
		if(! new File(recombinationFile).exists()) throw new IllegalArgumentException("Recombination file does not exist " + recombinationFile);

		try
		{
			// Check if the output file can be created
			new File(outputFile).createNewFile();

		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		if(!(replicateRuns>0)) throw new IllegalArgumentException("At least one replicate run should be provided; Provided by the user "+replicateRuns);

		this.outputFile=outputFile;
		this.outputFileType=outputFileType;
		this.haplotypeFile=haplotypeFile;
		this.recombinationFile=recombinationFile;
		this.chromosomeDefinition=chromosomeDefinition;
		this.additiveFile=additiveFile;
		this.epistasisFile=epistasisFile;
		this.simMode=simMode;
		this.replicateRuns=replicateRuns;
		this.logger=logger;
	}
	
	
	public void run()
	{
		this.logger.info("Starting MimicrEESummary");

		// Load the data
		FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistasisFile,this.logger).loadFitnessFunction();
		RecombinationGenerator recGenerator = new RecombinationGenerator(new RecombinationRateReader(this.recombinationFile,this.logger).getRecombinationRate(),
				new ChromosomeDefinitionReader(this.chromosomeDefinition).getRandomAssortmentGenerator());

		ArrayList<DiploidGenome> dipGenomes=new mimcore.io.DiploidGenomeReader(this.haplotypeFile,"",this.logger).readGenomes();

		


		ArrayList<PopulationAlleleCount> pacs=new MultiSimulationTimestamp(Population.loadPopulation(dipGenomes, fitnessFunction)
				,fitnessFunction,recGenerator,simMode.getTimestamps(),this.replicateRuns,this.logger).run();

		ISummaryWriter sw;
		if(this.outputFileType == OutputFileType.Sum)
		{
			sw= new SumWriter(this.outputFile,this.logger);
		}
		else if(this.outputFileType == OutputFileType.Sync)
		{
			 sw = new SyncWriter(this.outputFile,this.logger);
		}
		else
		{
			throw new IllegalArgumentException("Unknow output file: "+this.outputFile.toString());
		}
		sw.write(pacs);
		this.logger.info("Finished simulations");
	}
}
