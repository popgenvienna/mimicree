package mimicree.data.recombination;

import mimicree.data.*;

public class RecombinationWindow {
	private final Chromosome chromosome;
	private final int startPosition;
	private final int endPosition;
	private final double recRate;
	private final double p_recombination;
	
	public RecombinationWindow(Chromosome chromosome, int startPosition, int endPosition, double recRate)
	{
		this.chromosome=chromosome;
		this.startPosition=startPosition;
		this.endPosition=endPosition;
		this.recRate=recRate;
		this.p_recombination=calculateP(recRate,endPosition-startPosition+1);

	}
	
	
	
	private double calculateP(double recRate,int windowsize)
	{
		// first adjust by windowsize 
		// recRate cM/Mb 		d-> cM (for the window)
		double d=recRate*(((double)(windowsize))/1000000.0);
		double exponent = (-2.0 * d)/100.0;
		double eexponent=Math.pow(Math.E, exponent);
		double p= (1.0 - eexponent) / 2.0;
		
		assert(p>=0.0 && p<=1.0);
		return p;
	}
	
	/**
	 * A random number generator decides whether a recombination event takes place within the given window
	 * @return
	 */
	public boolean hasRecombinationEvent()
	{
		
		double rand=Math.random();
		if(rand<this.p_recombination)return true;
		return false;
	}
	
	/**
	 * Obtain a random postion within the range of the RecombinationWindow 
	 * @return a random position
	 */
	public GenomicPosition getRandomPosition()
	{
				// eg.:  100		  			1 	=           100-1+1 =100 
		int length=(this.endPosition - this.startPosition + 1);
		
		// basically values between 0-99 (random numbers between 0 and 0.999)
		int randAdd= (int)(Math.random()*length);
		
		// a random GenomicPosition within the window
		return new GenomicPosition(chromosome, this.startPosition+randAdd);
		
	}
	
}
