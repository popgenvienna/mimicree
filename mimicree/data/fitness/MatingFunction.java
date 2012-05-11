package mimicree.data.fitness;

import mimicree.data.*;

/**
 * Mating function generates couples for mating for a given population of specimen.
 * Mating success is directly proportional to fitness
 * @author robertkofler
 *
 */
public class MatingFunction {
	
	private final Population population;
	public MatingFunction(Population population)
	{
		this.population=population;
	}
	
	/**
	 * Choose a couple for mating
	 * @return
	 */
	public Specimen[] getCouple()
	{
		return null;
	}

}
