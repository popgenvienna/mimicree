package simulate;

import mimcore.data.statistic.MatingDistribution;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 3/21/13
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingleSimulationResults {

	private final int fixSelection;
	private final int fixNeutral;
	private final int totalSNPNumber;
	private final ArrayList<MatingDistribution> selectionMatings;
	private final ArrayList<MatingDistribution> neutralMatings;
	public SingleSimulationResults(ArrayList<MatingDistribution> selectionMatings, ArrayList<MatingDistribution> neutralMatings, int totalSNPNumber, int fixSelection, int fixNeutral)
	{
		this.selectionMatings=new ArrayList<MatingDistribution>(selectionMatings);
		this.neutralMatings=new ArrayList<MatingDistribution>(neutralMatings);
		this.totalSNPNumber=totalSNPNumber;
		this.fixNeutral=fixNeutral;
		this.fixSelection=fixSelection;

	}

	public int getFixSelection()
	{return this.fixSelection;}

	public int getFixNeutral()
	{return this.fixNeutral;}

	public int getTotalSNPNumber()
	{return this.totalSNPNumber;}

	public ArrayList<MatingDistribution> getNeutralMatings()
	{
		return new ArrayList<MatingDistribution>(this.neutralMatings);
	}

	public ArrayList<MatingDistribution> getSelectionMatings()
	{
		return new ArrayList<MatingDistribution>(this.selectionMatings);
	}





}
