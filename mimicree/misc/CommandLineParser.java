package mimicree.misc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.*;

public class CommandLineParser {
	
	/**
	 * Parse the command line arguments and return the results
	 * @param args the command line arguments
	 * @return
	 */
	public static CommandLineArguments getCommandLineArguments(String[] arguments)
	{
		LinkedList<String> args=new LinkedList<String>(Arrays.asList(arguments));
		String haplotypeFile="";
		String inversionFile="";
		String recombinationrateFile="";
		String additiveFile="";
		String epistasisFile="";
		String outputDir="";
		String outputGenRaw="";
		String chromosomeDefinition="";
		int replicateRuns=1;
		boolean displayHelp=false;
		boolean detailedLog=false;
	
		
        while(args.size()>0)
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
            	recombinationrateFile=args.remove(0);
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
            else if(cu.equals("--output-generations"))
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
            else if(cu.equals("--help"))
            {
            	displayHelp=true;
            }
            else if(cu.equals("--detailed-log"))
            {
            	detailedLog=true;
            }
            else
            {
                throw new IllegalArgumentException("Do not recognize command line option "+cu);
            }
        }
    
        // Parse the string with the generations
        ArrayList<Integer> outputGen = parseOutputGenerations(outputGenRaw);
        CommandLineArguments ca = new CommandLineArguments(haplotypeFile,inversionFile,recombinationrateFile,chromosomeDefinition, additiveFile,
        		epistasisFile, outputDir, outputGen, replicateRuns, displayHelp,detailedLog);
        return ca;
	}
	
	
	public static String helpMessage()
	{
		StringBuilder sb=new StringBuilder();
		return sb.toString();
	}
	
	
	public static ArrayList<Integer> parseOutputGenerations(String outputGenerationsRaw)
	{
		// Parse a String consistent of a comma-separated list of numbers, to a array of integers
		ArrayList<Integer> toret=new ArrayList<Integer>();
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
		for(String s :tmp)
		{
			toret.add(Integer.parseInt(s));
		}
		return toret;
	}
}
