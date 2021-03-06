package mimcore.data;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.javaws.exceptions.InvalidArgumentException;
import mimcore.data.recombination.*;
import mimcore.data.fitness.FitnessFunction;
import mimcore.data.fitness.MatingFunction;
import mimcore.data.statistic.MatingDistribution;
import mimcore.misc.MimicreeThreadPool;


/**
 * A population
 * is a collection of specimen (individuals)
 * @author robertkofler
 *
 */
public class Population {
	private final ArrayList<Specimen> specimen;
	// The matings that lead to the certain population
	public Population(ArrayList<Specimen> specimen)
	{

		this.specimen=new ArrayList<Specimen>(specimen);
	}
	/**
	 * Initialize a new population using a collection of diploid genomes, the recombination rate and a fitness function
	 * @param genomes a collection of diploid genomes which will constitute the final population
	 * @param fitnessFunction the fitness function will decided the fitness of a specimen
	 * @return
	 */
	public static Population loadPopulation(ArrayList<DiploidGenome> genomes,FitnessFunction fitnessFunction)
	{
		ArrayList<Specimen> specimen=new ArrayList<Specimen>();
		for(DiploidGenome genome: genomes)
		{
			Specimen s=fitnessFunction.getSpecimen(genome,0,0);
			specimen.add(s);
		}
		
		// Create a new population
		return new Population(specimen);
	}

	
	/**
	 * Obtain the next generation of the population;
	 * The next generation will depend on the environment as the environment determines
	 * the fitness of any specimen and thus it's mating success

	 * @return
	 */
	public Population getNextGeneration(FitnessFunction fitnessFunction, RecombinationGenerator recGenerator)
	{
		MatingFunction mf=MatingFunction.getMatingFunction(this);
		SpecimenGenerator specGen=new SpecimenGenerator(mf,fitnessFunction,recGenerator,this.size());

		return new Population(specGen.getSpecimen());
	}

	/**
	 * Return the distribution of matings that led to the specific population
	 * @return
	 */
	public MatingDistribution getMatingDistribution()
	{
		     // First creat a parent name, offspring matrix; Thus count for each parent the number of offspring
			HashMap<Long,Integer> matingCounts=new HashMap<Long, Integer>();
		   	for(Specimen spec : this.specimen)
			   {
				   long mother=spec.mother();
				   long father=spec.father();
				   if(mother==0 || father ==0) continue;

				   if(!matingCounts.containsKey(mother)) matingCounts.put(mother,0);
				   if(!matingCounts.containsKey(father)) matingCounts.put(father,0);
				   matingCounts.put(father,matingCounts.get(father)+1);
				   matingCounts.put(mother,matingCounts.get(mother)+1);
			   }


		    // Determine the highest number of offspring any parent has
			int max=0;
			for(Map.Entry<Long,Integer> e : matingCounts.entrySet())
			{
				if( e.getValue() > max) max=e.getValue();
			}

			int[] matingDistribution=new int[max+1];
			// number of individuals without mating; is simply the population size (= individuals that could mate) minus individuals which actually mated.
			int loosers= this.specimen.size()-matingCounts.size();
			matingDistribution[0]=loosers;
		    // initialize as zero of course except the first :)
			for(int i=1; i< matingDistribution.length; i++)
			{
				matingDistribution[i]=0;
			}

		    // convert this to a list, indicating how many parents have 0 offspring
			for(Map.Entry<Long,Integer> e : matingCounts.entrySet())
			{
				int val=e.getValue();
				matingDistribution[val]++;
			}
			return new MatingDistribution(matingDistribution);
	}

	
	/**
	 * Retrieve the specimen constituting a population
	 * @return
	 */
	public ArrayList<Specimen> getSpecimen()
	{
		return new ArrayList<Specimen>(this.specimen);
	}
	
	/**
	 * Return the sum of the fitness of all specimen for the given population
	 * @return
	 */
	public double fitnessSum()
	{
		double toret=0;
		for(Specimen spec: this.specimen)
		{
			toret+=spec.fitness();
		}
		return toret;
	}
	
	public int size()
	{
		return this.specimen.size();
	}
	
	public boolean isFixed(GenomicPosition position)
	{
		int countAncestral=0;
		int hapCount=this.specimen.size()*2;
		for(Specimen spec: this.specimen)
		{
			if(spec.getGenome().getHaplotypeA().hasAncestral(position)) countAncestral++;
			if(spec.getGenome().getHaplotypeB().hasAncestral(position)) countAncestral++;
		}
		if(countAncestral==0 || countAncestral==hapCount) return true;
		return false;
	}

	/**
	 * Determine whether the ancestral allele fixed
	 * @param position
	 * @return
	 */
	public boolean isAncestralFixed(GenomicPosition position)
	{
		int countAncestral=0;
		int hapCount=this.specimen.size()*2;
		for(Specimen spec: this.specimen)
		{
			if(spec.getGenome().getHaplotypeA().hasAncestral(position)) countAncestral++;
			if(spec.getGenome().getHaplotypeB().hasAncestral(position)) countAncestral++;
		}
		if(! (countAncestral==0 || countAncestral==hapCount)) throw new IllegalArgumentException("Population is not yet fixed");
		if(countAncestral==hapCount)
		{
			return true;
		}
		return false;
	}


	
	public boolean areSelectedFixed(FitnessFunction fitness)
	{
		if(fitness.getAdditiveSNPFitness().areAdditiveFixed(this) && fitness.getEpistaticSNPFitness().areEpistaticFixed(this)) return true;
		return false;
	}
	
}

// --------------------------------------------------------------------------- //
// ----------------------------------- HELPER -------------------------------- //
// -------------------------- GET SPECIMEN IN MULTITHREADING ----------------- //
// --------------------------------------------------------------------------- //


class SpecimenGenerator
{
	private final MatingFunction mf;
	private final int populationSize;
	private final FitnessFunction ff;
	private final RecombinationGenerator recGen;

	
	public SpecimenGenerator(MatingFunction mf,FitnessFunction ff, RecombinationGenerator recGen, int populationSize)
	{
		if(populationSize<1) throw new IllegalArgumentException("Population size must be larger than zero");
		this.mf=mf;
		this.populationSize=populationSize;
		this.ff=ff;
		this.recGen=recGen;


	}

	public ArrayList<Specimen> getSpecimen()
	{
		SpecimenCollector col=new SpecimenCollector();
		ExecutorService executor=MimicreeThreadPool.getExector();
		ArrayList<Callable<Object>> call=new ArrayList<Callable<Object>>();

		for(int i=0; i<this.populationSize; i++)
		{
			call.add(Executors.callable(new SingleSpecimenGenerator(this.mf,this.ff,this.recGen,col)));
		}

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
		ArrayList<Specimen> specs=col.getSpecimen();
		assert(specs.size()==populationSize);
		return specs;
	}


	
	private void  run()
	{



	}
	

}

/**
 * Only a synchronized wrapper for a ArrayList
 * @author robertkofler
 *
 */
class SpecimenCollector
{
	private final ArrayList<Specimen> specs =new ArrayList<Specimen>();


	public SpecimenCollector(){}
	
	public synchronized void addSpecimen(Specimen specimen)
			{
		this.specs.add(specimen);
	}

	
	public synchronized ArrayList<Specimen> getSpecimen()
	{
		return new ArrayList<Specimen>(this.specs);
	}
}

/**
 * Sinlge generator for a specimen
 * @author robertkofler
 *
 */
class SingleSpecimenGenerator implements Runnable
{
	private final MatingFunction mf;
	private final SpecimenCollector collector;
	private final FitnessFunction ff;
	private final RecombinationGenerator recGenerator;
	public SingleSpecimenGenerator(MatingFunction mf, FitnessFunction ff, RecombinationGenerator recGen, SpecimenCollector collector)
	{
		this.mf=mf;
		this.collector=collector;
		this.ff=ff;
		this.recGenerator=recGen;
	}
	
	public void run()
	{
		Specimen[] merryCouple= mf.getCouple();
		HaploidGenome semen	=merryCouple[0].getGamete(recGenerator);
		HaploidGenome egg	=merryCouple[1].getGamete(recGenerator);
		DiploidGenome fertilizedEgg=new DiploidGenome(semen,egg);
		Specimen spec=ff.getSpecimen(fertilizedEgg,merryCouple[0].name(),merryCouple[1].name());
		collector.addSpecimen(spec);
	}
	
}

