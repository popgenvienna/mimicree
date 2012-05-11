package mimicree.data.recombination;

import mimicree.data.*;

public class RecombinationWindow {
	private final Chromosome chromosome;
	private final int startPosition;
	private final int endPosition;
	private final double recRate;

	public RecombinationWindow(Chromosome chromosome, int startPosition, int endPosition, double recRate)
	{
		this.chromosome=chromosome;
		this.startPosition=startPosition;
		this.endPosition=endPosition;
		this.recRate=recRate;

	}
	
	/**
	 * A random number generator decides whether a recombination event takes place within the given window
	 * @return
	 */
	private boolean hasRecombinationEvent()
	{
		double rand=Math.random();
		return false;
	}
	
	private GenomicPosition getRandomPosition()
	{
		return null;
	}
	
}
