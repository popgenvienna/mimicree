package simulate;

import mimcore.data.statistic.PopulationAlleleCount;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationFrameworkLimits {
	private final String haplotypeFile;
	private final String recombinationFile;
	private final String chromosomeDefinition;
	private final int selectionCoefficient;
	private final int heterozygousEffect;
	private final int maxGenerations;
	private final int maxFrequency;
	private final int numberOfSelected;
	private final String outputFile;
	private final int replicateRuns;



	private final java.util.logging.Logger logger;
	public SimulationFrameworkLimits(String haplotypeFile, String recombinationFile, String chromosomeDefinition, int selectionCoefficient, int heterozygousEffect,
									 int maxGenerations, int maxFrequency, int numberOfSelected,
									 String outputFile, int replicateRuns, java.util.logging.Logger logger)
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
		if(selectionCoefficient<=0) throw new IllegalArgumentException("Selection coefficient needs to be larger than zero");
		if(numberOfSelected<=0) throw new IllegalArgumentException("Number of selected sites needs to be larger than zero");


		this.outputFile=outputFile;
		this.selectionCoefficient=selectionCoefficient;
		this.heterozygousEffect=heterozygousEffect;
		this.maxGenerations=maxGenerations;
		this.maxFrequency=maxFrequency;
		this.numberOfSelected=numberOfSelected;
		this.haplotypeFile=haplotypeFile;
		this.recombinationFile=recombinationFile;
		this.chromosomeDefinition=chromosomeDefinition;
		this.replicateRuns=replicateRuns;
		this.logger=logger;
	}
	
	
	public void run()
	{
		this.logger.info("Starting MimicrEELimits");

		// Load the data
		//FitnessFunction fitnessFunction=new FitnessFunctionLoader(this.additiveFile,this.epistasisFile,this.logger).loadFitnessFunction();
		//ArrayList<DiploidGenome> dipGenomes=new mimcore.io.DiploidGenomeReader(this.haplotypeFile,"",this.logger).readGenomes();
		//RecombinationGenerator recGenerator = new RecombinationGenerator(new RecombinationRateReader(this.recombinationFile,this.logger).getRecombinationRate(),
	//			new ChromosomeDefinitionReader(this.chromosomeDefinition).getRandomAssortmentGenerator());
		


//		ArrayList<PopulationAlleleCount> pacs=new MultiSimulationTimestamp(Population.loadPopulation(dipGenomes, fitnessFunction)
//				,fitnessFunction,recGenerator,simMode.getTimestamps(),this.replicateRuns,this.logger).run();


	}
}
