package mimcore.misc;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 6/20/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class MimicrEERandom {

	private static Random rand=null;

	public static void setSeed(int seed, int threadCount)
	{
		if(threadCount>1) throw new IllegalArgumentException("When seting a seed for the random numbers the number of threads has to be exactly 1");
		rand=new Random(seed);
	}



	public static double getDouble()
	{
		if(rand==null)
		{
			return Math.random();
		}
		else
		{
			return rand.nextDouble();
		}
	}

}
