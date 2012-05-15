package mimicree.simulate;

import mimicree.data.*;
import mimicree.data.fitness.FitnessFunction;
import java.util.*;
import java.util.logging.Logger;
import mimicree.io.PopulationWriter;

public class SingleSimulation {
	private Population population;
	private final FitnessFunction fitness;
	private final String outputDir;
	private final HashSet<Integer> outputGenerations;
	private final int maxGeneration;
	private final int simulationNumber;
	private Logger logger;

	public SingleSimulation(int simulationNumber, Population population, FitnessFunction fitness, String outputDir,ArrayList<Integer> outputGenerations, Logger logger)
	{
		this.simulationNumber=simulationNumber;
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
		
	}

	
	public void run()
	{
		// For the number of requested simulations get the next generation, and write it to file if requested
		for(int i=1; i<=this.maxGeneration; i++)
		{
			this.population=population.getNextGeneration(this.fitness);
			if(outputGenerations.contains(i)) new PopulationWriter(this.population,this.outputDir,i,this.simulationNumber,this.logger).write();
			
		}
	}
}
