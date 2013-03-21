package simulate;

import mimcore.data.DiploidGenome;
import mimcore.data.Population;
import mimcore.data.fitness.*;
import mimcore.data.fitness.FitnessFunction;
import mimcore.data.recombination.RecombinationGenerator;
import mimcore.data.statistic.MatingDistribution;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

public class LimitsSimulationWrapper {
	private final ArrayList<DiploidGenome> diploidGenomes;
	private final FitnessFunction selectionFitness;
	private final FitnessFunction neutralFitness;
	private final RecombinationGenerator recGenerator;
	private final int maxGeneration;
	private Logger logger;


	public LimitsSimulationWrapper(ArrayList<DiploidGenome> diploidGenomes, AdditiveSNPFitness additiveFitness, RecombinationGenerator recGenerator,
								   int maxGeneration, Logger logger)
	{

		this.diploidGenomes=diploidGenomes;
		this.selectionFitness=new FitnessFunction(additiveFitness,new EpistaticSNPFitness(new ArrayList<EpistaticSNP>()));
		this.neutralFitness = new FitnessFunction(additiveFitness.getNeutralAdditiveFitness(),new EpistaticSNPFitness(new ArrayList<EpistaticSNP>()));
		this.recGenerator=recGenerator;
		this.maxGeneration=maxGeneration;
		this.logger = logger;
	}



	
	public SingleSimulationResults run()
	{

		SingleLimitsSimulation selectionSimulation=new SingleLimitsSimulation(this.diploidGenomes,this.selectionFitness,this.recGenerator,this.maxGeneration,this.logger);
		SingleLimitsSimulation neutralSimulation=new SingleLimitsSimulation(this.diploidGenomes,this.neutralFitness,this.recGenerator,this.maxGeneration,this.logger);

		this.logger.info("Starting neutral simulation");
		SimulationResults neutralResults=neutralSimulation.run();
		if(neutralResults==null) return null;
		this.logger.info("Starting non-neutral simulation (ie with selection)");
		SimulationResults selectionResults=selectionSimulation.run();
		if(selectionResults==null) return null;

		return new SingleSimulationResults(selectionResults.getMatingDistribution(),neutralResults.getMatingDistribution(),
				selectionResults.getTotalSNPNumber(),selectionResults.getFixCorrect(),neutralResults.getFixCorrect());
	}



}


class SingleLimitsSimulation
{
	private final Population population;
	private final FitnessFunction fitness;
	private final RecombinationGenerator recGen;
	private final int maxGenerations;
	private Logger logger;

	        public SingleLimitsSimulation(ArrayList<DiploidGenome> genomes, FitnessFunction fitness, RecombinationGenerator recGen, int maxGenerations, Logger logger)
			{
				this.population = Population.loadPopulation(genomes,fitness);
				this.fitness=fitness;
				this.recGen=recGen;
				this.maxGenerations=maxGenerations;
				this.logger = logger;
			}

	public SimulationResults run()
	{
		this.logger.info("MimicrEE will proceed with forward simulations until all selected SNPs are fixed, ");
		Population nextPopulation=this.population;
		int counter=1;
		ArrayList<MatingDistribution> matingDistris=new ArrayList<MatingDistribution>();
		while(!nextPopulation.areSelectedFixed(this.fitness))
		{
			if(counter > this.maxGenerations)
			{
				this.logger.info("Abborting simulation; SNPs did not fix in "+ this.maxGenerations+ " generations; Will restart simulations");
				return null;
			}
			this.logger.info("Processing generation "+counter);
			nextPopulation=nextPopulation.getNextGeneration(this.fitness,this.recGen);
			matingDistris.add(nextPopulation.getMatingDistribution());
			counter++;
		}
		int fixCorrect=this.fitness.getAdditiveSNPFitness().countAdditiveFixedCorrectly(nextPopulation);
		this.logger.info("Finished simulations in "+counter + " generations");
		return new SimulationResults(matingDistris,fitness.getAdditiveSNPFitness().getAdditiveSNPs().size(),fixCorrect);
	}


}

class SimulationResults
{
	private final int fixCorrect;
	private final int totalSNPNumber;
	private final ArrayList<MatingDistribution> matingDistribution;
	public SimulationResults(ArrayList<MatingDistribution> matingDistribution, int totalSNPNumber, int fixCorrect)
	{
		this.fixCorrect=fixCorrect;
		this.matingDistribution=new ArrayList<MatingDistribution>(matingDistribution);
		this.totalSNPNumber=totalSNPNumber;
	}

	public int getFixCorrect()
	{return this.fixCorrect;}

	public int getTotalSNPNumber()
	{return this.totalSNPNumber;}

	public ArrayList<MatingDistribution> getMatingDistribution()
	{
		return new ArrayList<MatingDistribution>(this.matingDistribution);
	}



}


