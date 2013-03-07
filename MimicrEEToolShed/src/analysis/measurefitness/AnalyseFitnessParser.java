package analysis.measurefitness;

import java.util.LinkedList;
import java.util.logging.Logger;

import mimicree.MimicrEE;

public class AnalyseFitnessParser {
	
	public static void parseArguments(Logger logger, LinkedList<String> args)
	{
		String haplotypeFile="";
		String additiveFile="";
		String epistasisFile="";
		String outputFile="";
	
		 while(args.size()>0)
	        {
	            String cu=args.remove(0);
	            
	            if(cu.equals("--haplotypes"))
	            {
	                haplotypeFile = args.remove(0);
	            }
	            else if(cu.equals("--additive"))
	            {
	            	additiveFile=args.remove(0);
	            }
	            else if(cu.equals("--epistasis"))
	            {
	            	epistasisFile=args.remove(0);
	            }
	            else if(cu.equals("--output-file"))
	            {
	            	outputFile=args.remove(0);
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
		 
		 AnalyseFitnessFramework aff=new AnalyseFitnessFramework(haplotypeFile,additiveFile,epistasisFile,outputFile,logger);
		 aff.run();
	}
	public static void printHelpMessage()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("mode: measure-fitness; Measure the fitness of the given haplotypes\n");
		sb.append("--haplotypes					the haplotypes for which to measure the fitness\n");
		sb.append("--additive					a file containing the additive fitness effects\n");
		sb.append("--output-file					the fitness for the haplotypes\n");
		sb.append(MimicrEE.getGeneralHelpmessage());
		System.out.print(sb.toString());
		System.exit(1);
	}

}
