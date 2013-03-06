package simulate;

import mimcore.data.fitness.FitnessFunction;
import mimcore.data.recombination.RecombinationGenerator;
import mimcore.data.statistic.PACReducer;
import mimcore.data.statistic.PopulationAlleleCount;
import mimcore.data.*;
import mimcore.io.PopulationWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

public class MultiSimulationTimestamp {
	private final Population basePopulation;
	private final FitnessFunction fitness;
	private final RecombinationGenerator recGenerator;
	private final HashSet<Integer> outputGenerations;
	private final int maxGeneration;
	private final int replicateRuns;
	private Logger logger;

	public MultiSimulationTimestamp(Population population, FitnessFunction fitness, RecombinationGenerator recGenerator,
									ArrayList<Integer> outputGenerations, int replicateRuns, Logger logger)
	{

		this.basePopulation=population;
		this.fitness=fitness;
		
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
		this.replicateRuns=replicateRuns;
		
	}

	
	public ArrayList<PopulationAlleleCount> run()
	{
		ArrayList<PopulationAlleleCount> pacs=new ArrayList<PopulationAlleleCount>();
		for(int k =0; k<this.replicateRuns; k++)
		{
			int simulationNumber=k+1;
			this.logger.info("Starting simulation replicate number " + simulationNumber);
			this.logger.info("MimicrEESummary will proceed with forward simulations until generation " + this.maxGeneration);

			pacs.add(new PACReducer(this.basePopulation).reduce());
			Population nextPopulation =this.basePopulation;
			// For the number of requested simulations get the next generation, and write it to file if requested
			for(int i=1; i<=this.maxGeneration; i++)
			{
				this.logger.info("Processing generation "+i+ " of replicate run "+simulationNumber);
				nextPopulation=nextPopulation.getNextGeneration(this.fitness,this.recGenerator);
				if(outputGenerations.contains(i)) pacs.add(new PACReducer(nextPopulation).reduce());
			}
		}
		return pacs;
	}
}
