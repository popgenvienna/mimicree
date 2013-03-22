package simulate;

import mimcore.data.haplotypes.SNP;
import mimcore.data.haplotypes.SNPCollection;
import mimcore.data.statistic.MatingDistribution;
import mimcore.data.statistic.PopulationAlleleCount;
import mimcore.io.misc.ISummaryWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SimulationResultsWriter{
	public final String outputFile;
	public BufferedWriter bf;
	public Logger logger;
	public SimulationResultsWriter(String outputFile, Logger logger)
	{
		this.outputFile=outputFile;
		this.logger=logger;
		try
		{
			bf=new BufferedWriter(new FileWriter(outputFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void write(ArrayList<SingleSimulationResults> simRes)
	{

		this.logger.info("Writing simulation results to file "+this.outputFile);
		writeMainSummary(simRes);
		writeSelectionMatingDistribution(simRes);
		
		try{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		this.logger.info("Finished writing summary file");
	}
	

	private void writeMainSummary(ArrayList<SingleSimulationResults> simRes)
	{
		for(SingleSimulationResults sr: simRes)
		{
			StringBuilder sb=new StringBuilder();
			sb.append("simres"); sb.append('\t');
			sb.append(sr.getTotalSNPNumber()); sb.append('\t');
			sb.append(sr.getFixNeutral()); sb.append('\t');
			sb.append(sr.getFixSelection()); sb.append('\t');
			sb.append(sr.getGenerationsNeutral()); sb.append('\t');
			sb.append(sr.getGenerationsSelection());
			try{
				 bf.write(sb.toString()+"\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}

	}


	private void writeSelectionMatingDistribution(ArrayList<SingleSimulationResults> simRes)
	{
		int repeatIndex=0;
		for(SingleSimulationResults sr: simRes)
		{
			ArrayList<MatingDistribution> selmat=sr.getSelectionMatings();
			for(int generation=0; generation<selmat.size(); generation++)
			{
				MatingDistribution active= selmat.get(generation);
				StringBuilder sb = new StringBuilder();
				sb.append("matdist"); sb.append("\t");
				sb.append(repeatIndex+1); sb.append("\t");
				sb.append(generation+1);
				for(int i=0; i< active.length(); i++)
				{
					sb.append("\t");
					sb.append(active.get(i));
				}
				try
				{
					bf.write(sb.toString()+"\n");
				}
				catch(IOException e)
				{
					e.printStackTrace();
					System.exit(0);
				}


			}
			repeatIndex++;
		}
	}


	

	

}
