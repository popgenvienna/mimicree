package simulate;

import mimcore.data.DiploidGenome;
import mimcore.data.Population;
import mimcore.data.fitness.*;
import mimcore.data.fitness.FitnessFunction;
import mimcore.data.recombination.RecombinationGenerator;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

public class LimitsSimulationWrapper {
	private final ArrayList<DiploidGenome> diploidGenomes;
	private final FitnessFunction fitness;
	private final FitnessFunction neutralFitness;
	private final RecombinationGenerator recGenerator;
	private final int maxGeneration;
	private Logger logger;


	public LimitsSimulationWrapper(ArrayList<DiploidGenome> diploidGenomes, AdditiveSNPFitness additiveFitness, RecombinationGenerator recGenerator,
								   int maxGeneration, Logger logger)
	{

		this.diploidGenomes=diploidGenomes;
		this.fitness=new FitnessFunction(additiveFitness,new EpistaticSNPFitness(new ArrayList<EpistaticSNP>()));
		this.neutralFitness = new FitnessFunction(additiveFitness.getNeutralAdditiveFitness(),new EpistaticSNPFitness(new ArrayList<EpistaticSNP>()));
		this.recGenerator=recGenerator;
		this.maxGeneration=maxGeneration;
		this.logger = logger;
	}



	
	public SingleSimulationResults run()
	{


		/*
		for(int k =0; k<this.replicateRuns; k++)
		{
			int simulationNumber=k+1;
			this.logger.info("Starting simulation replicate number " + simulationNumber);
			this.logger.info("MimicrEESummary will proceed with forward simulations until generation " + this.maxGeneration);

			this.logger.info("Recording base population at generation of replicate " + simulationNumber);
			pacs.add(new PACReducer(this.basePopulation).reduce());
			Population nextPopulation =this.basePopulation;
			// For the number of requested simulations get the next generation, and write it to file if requested
			for(int i=1; i<=this.maxGeneration; i++)
			{
				this.logger.info("Processing generation "+i+ " of replicate run "+simulationNumber);
				nextPopulation=nextPopulation.getNextGeneration(this.fitness,this.recGenerator);
				if(outputGenerations.contains(i))
				{
					this.logger.info("Recording population at generation "+i+" of replicate "+simulationNumber);
					pacs.add(new PACReducer(nextPopulation).reduce());
				}
			}

		}
		*/
	}



}


class SingleLimitsSimulation
{
	private final Population pop;
	private final RecombinationGenerator recGen;
	private final int maxGenerations;

	        public SingleLimitsSimulation(ArrayList<DiploidGenome> genomes, FitnessFunction function, RecombinationGenerator recGen, int maxGenerations)
			{
				pop = Population.loadPopulation(genomes,function);
				this.recGen=recGen;
				this.maxGenerations=maxGenerations;

			}

}

class SimulationResults
{

}


