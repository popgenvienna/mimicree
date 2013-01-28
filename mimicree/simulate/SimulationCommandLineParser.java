package mimicree.simulate;

import java.util.*;
import java.util.logging.Logger;

import mimicree.MimicrEE;

public class SimulationCommandLineParser {
	
	/**
	 * Parse the command line arguments and return the results
	 * @param args the command line arguments
	 * @return
	 */
	public static void runMimicreeSimulations(Logger logger, LinkedList<String> args)
	{

		String haplotypeFile="";
		String inversionFile="";
		String recombinationFile="";
		String additiveFile="";
		String epistasisFile="";
		String outputDir="";
		String outputGenRaw="";
		String chromosomeDefinition="";
		int replicateRuns=1;
		boolean selectedOnly=false;

	
		
        while(args.size() > 0)
        {
            String cu=args.remove(0);
            
            if(cu.equals("--haplotypes-g0"))
            {
                haplotypeFile = args.remove(0);
            }
            else if(cu.equals("--inversions-g0"))
            {
                inversionFile = args.remove(0);
            }
            else if(cu.equals("--recombination-rate"))
            {
            	recombinationFile=args.remove(0);
            }
            else if(cu.equals("--additive"))
            {
            	additiveFile=args.remove(0);
            }
            else if(cu.equals("--epistasis"))
            {
            	epistasisFile=args.remove(0);
            }
            else if(cu.equals("--chromosome-definition"))
            {
            	chromosomeDefinition=args.remove(0);
            }
            else if(cu.equals("--output-mode"))
            {
            	outputGenRaw=args.remove(0);
            }
            else if(cu.equals("--replicate-runs"))
            {
            	replicateRuns=Integer.parseInt(args.remove(0));
            }
            else if(cu.equals("--output-dir"))
            {
            	outputDir=args.remove(0);
            }
            else if(cu.equals("--simulate-selected-only"))
            {
            	selectedOnly=true;
            }
            else if(cu.equals("--help"))
            {
            	printHelpMessage();
            }
            else
            {
                throw new IllegalArgumentException("Do not recognize command line option "+cu);
            }
        }
    
        // Parse the string with the generations
        SimulationMode simMode = parseOutputGenerations(outputGenRaw);

        mimicree.simulate.SimulationFramework mimframe= new mimicree.simulate.SimulationFramework(haplotypeFile,inversionFile,recombinationFile,chromosomeDefinition,
        		additiveFile,epistasisFile,outputDir,simMode,replicateRuns,selectedOnly,logger);
        
        mimframe.run();
	}
	
	
	public static void printHelpMessage()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("--haplotypes-g0				the haplotype file\n");
		sb.append("--inversions-g0				the inversion file\n");
		sb.append("--recombination-rate			the recombination rate for windows of fixed size\n");
		sb.append("--additive				the additive fitness effect of SNPs\n");
		sb.append("--epistasis				the epistatic fitness effect of SNPs\n");	
		sb.append("--chromosome-definition			which chromosomes parts constitute a chromosome\n");
		sb.append("--output-mode				either 1.) a coma separated list of generations to output\n");
		sb.append("					or 2.) eg.: abortfixsel:10,20,30,40,50,60\n");
		sb.append("					or 3.) eg.: fixselected10; simulations will run until all selected loci are fixed\n");
		sb.append("					(storing output all 10 generations)\n");
		sb.append("--replicate-runs			how often should the simulation be repeated\n");
		sb.append("--simulate-selected-only		perform simulations only for the selected SNPs\n");
		sb.append("--output-dir				the output directory\n");
		sb.append(MimicrEE.getGeneralHelpmessage());
		System.out.print(sb.toString());
		System.exit(1);
	}
	
	
	public static SimulationMode parseOutputGenerations(String outputGenerationsRaw)
	{
		// Parse a String consistent of a comma-separated list of numbers, to a array of integers
		SimulationMode simMode;

		if(outputGenerationsRaw.toLowerCase().startsWith("fixselected"))
		{
			String[] tmp=outputGenerationsRaw.toLowerCase().split("fixselected");
			int allTimestamp=Integer.parseInt(tmp[1]);
			simMode=SimulationMode.FixSelected;
			ArrayList<Integer> ti=new ArrayList<Integer>();
			ti.add(allTimestamp);
			simMode.setTimestamps(ti);
			simMode.setAbortWhenSelectedFixed(true);
			
		}
		else if(outputGenerationsRaw.toLowerCase().startsWith("abortselfix:"))
		{
			String[] tmp2=outputGenerationsRaw.toLowerCase().split("abortselfix:");
			String timestamps=tmp2[1];

			String [] tmp;
			if(timestamps.contains(","))
			{
				tmp=timestamps.split(",");
			}
			else
			{
				tmp=new String[1];
				tmp[0]=timestamps;
			}
			// Convert everything to int
			ArrayList<Integer> ti=new ArrayList<Integer>();
			for(String s :tmp)
			{
				ti.add(Integer.parseInt(s));
			}
			simMode=SimulationMode.Timestamp;
			simMode.setTimestamps(ti);
			simMode.setAbortWhenSelectedFixed(true);
		}
		else
		{
			String [] tmp;
			if(outputGenerationsRaw.contains(","))
			{
				tmp=outputGenerationsRaw.split(",");
			}
			else
			{
				tmp=new String[1];
				tmp[0]=outputGenerationsRaw;
			}
			// Convert everything to int
			ArrayList<Integer> ti=new ArrayList<Integer>();
			for(String s :tmp)
			{
				ti.add(Integer.parseInt(s));
			}
			simMode=SimulationMode.Timestamp;
			simMode.setTimestamps(ti);
			simMode.setAbortWhenSelectedFixed(false);
		}

		

		return simMode;
	}
}
