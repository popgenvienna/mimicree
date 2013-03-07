package analysis.rsquared;

import java.util.*;
import java.util.logging.Logger;


public class RsquaredParser {
	
	
	public static void parseCommandline(Logger logger, LinkedList<String> args)
	{
		String haplotypeFile="";
		String outputFile="";
		int maxDistance=0;
		
		 while(args.size()>0)
	     {
	            String cu = args.remove(0);
	            
	            if(cu.equals("--haplotypes"))
	            {
	                haplotypeFile = args.remove(0);
	            }
	            else if(cu.equals("--output"))
	            {
	            	outputFile = args.remove(0);
	            }
	            else if(cu.equals("--max-distance"))
	            {
	            	maxDistance = Integer.parseInt(args.remove(0));
	            }
	            else if(cu.equals("--help"))
	            {
	            	printHelp();
	            }
	            else
	            {
	            	 throw new IllegalArgumentException("Do not recognize command line option "+cu);
	            }
	     }		
		 
		 RsquaredFramework r2f=new RsquaredFramework(haplotypeFile,outputFile,maxDistance,logger);
		 r2f.run();
	}
	
	public static void printHelp()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("mode rsquared: calculates pairwise linkage disequilibrium between SNPs\n");
		sb.append("--haplotypes					the haplotypes\n");
		sb.append("--output					the output file\n");
		sb.append("--max-distance					the maximum distance between two SNPs for calculating R^2\n");
		sb.append("--mode					the analysis mode of operation; see manual for supported modes\n");
		sb.append("--version					the analysis mode of operation; see manual for supported modes\n");
		sb.append("--detailed-log				print detailed log messages\n");
		sb.append("--help					print the help\n");
		System.out.print(sb.toString());
		System.exit(1);
	}

}
