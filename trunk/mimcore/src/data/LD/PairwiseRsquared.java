package data.LD;
import data.*;

public class PairwiseRsquared {
	private final GenomicPosition positionA;
	private final GenomicPosition positionB;
	private final int distance;
	private final double rsquared;
	public PairwiseRsquared(GenomicPosition positionA, GenomicPosition positionB, int distance, double rsquared)
	{
		this.positionA=positionA;
		this.positionB=positionB;
		this.distance=distance;
		this.rsquared=rsquared;
	}
	
	public GenomicPosition positionA()
	{
		return this.positionA;
	}

	public GenomicPosition positionB()
	{
		return this.positionB;
	}
	
	public int distance()
	{
		return this.distance;
	}
	
	public double rsquared()
	{
		return this.rsquared;
	}
}
