package mimicree.simulate;

import mimicree.data.*;
import mimicree.data.fitness.FitnessFunction;
import java.util.*;
import java.util.logging.Logger;
import mimicree.io.PopulationWriter;
import mimicree.data.recombination.RecombinationGenerator;

public class SingleSimulationFixSelected implements ISingleSimulation{
	private final Population population;
	private final FitnessFunction fitness;
	private final RecombinationGenerator recGenerator;
	private final String outputDir;
	private final int snapshot;
	private Logger logger;

	public SingleSimulationFixSelected(Population population, FitnessFunction fitness, RecombinationGenerator recGenerator, String outputDir,int snapshot, Logger logger)
	{

		this.population=population;
		this.fitness=fitness;
		this.outputDir=outputDir;
		this.snapshot=snapshot;
		this.logger=logger;
		this.recGenerator=recGenerator;
		
	}
	
	
	public void run(int simulationNumber)
	{
		Population nextPopulation=this.population;
		int counter=1;
		while(!nextPopulation.areSelectedFixed())
		{
			this.logger.info("Processing generation "+counter+ " of replicate run "+simulationNumber);
			nextPopulation=nextPopulation.getNextGeneration(this.fitness,this.recGenerator);
			if(counter%snapshot==0) new PopulationWriter(nextPopulation,this.outputDir,counter,simulationNumber,this.logger).write();
			counter++;
		}

	}
}
