package mimcore.data.statistic;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 3/21/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatingDistribution {

	// Matrix with number of successfull matings vs count (can be imagined as similar to family size distribution)
	private final int[] matingDistribution;
	public MatingDistribution(int[] matingDistribution)
	{
		this.matingDistribution=matingDistribution;
	}
	public int length()
	{
		return matingDistribution.length;
	}

	/**
	 * Get the number of occurences for a given number of matings;
	 * @param index
	 * @return
	 */
	public int get(int index)
	{
		return this.matingDistribution[index];
	}



}
