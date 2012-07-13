package mimicree.simulate;

import mimicree.data.*;
import mimicree.data.fitness.FitnessFunction;
import java.util.*;
import java.util.logging.Logger;
import mimicree.io.PopulationWriter;
import mimicree.data.recombination.RecombinationGenerator;

public class SingleSimulationTimestamp implements ISingleSimulation{
	private final Population population;
	private final FitnessFunction fitness;
	private final RecombinationGenerator recGenerator;
	private final String outputDir;
	private final HashSet<Integer> outputGenerations;
	private final int maxGeneration;
	private Logger logger;

	public SingleSimulationTimestamp(Population population, FitnessFunction fitness, RecombinationGenerator recGenerator, String outputDir,ArrayList<Integer> outputGenerations, Logger logger)
	{

		this.population=population;
		this.fitness=fitness;
		this.outputDir=outputDir;
		
		int max=0;
		HashSet<Integer> toOutput=new HashSet<Integer>();
		for(Integer i : outputGenerations)
		{
			toOutput.add(i);
			if(i > max ) max=i;
		}
		this.maxGeneration=max;
		this.outputGenerations=toOutput;
		this.logger=logger;
		this.recGenerator=recGenerator;
		
	}

	
	public void run(int simulationNumber)
	{
		this.logger.info("Starting simulation replicate number " + simulationNumber);
		this.logger.info("MimicrEE will proceed with forward simulations until generation " + this.maxGeneration);
		
		Population nextPopulation =this.population; 
		// For the number of requested simulations get the next generation, and write it to file if requested
		for(int i=1; i<=this.maxGeneration; i++)
		{
			this.logger.info("Processing generation "+i+ " of replicate run "+simulationNumber);
			nextPopulation=nextPopulation.getNextGeneration(this.fitness,this.recGenerator);
			if(outputGenerations.contains(i)) new PopulationWriter(nextPopulation,this.outputDir,i,simulationNumber,this.logger).write();
			
		}
	}
}
