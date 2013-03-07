package analysis.rsquaredrandom;

import java.util.*;
import java.util.logging.Logger;

import mimicree.MimicrEE;

public class RsquaredRandomParser {
	
	
	public static void parseCommandline(Logger logger, LinkedList<String> args)
	{
		String haplotypeFile="";
		String outputFile="";
		int randomSamples=0;
		boolean intraChromosomal=false;
		
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
	            else if(cu.equals("--random-samples"))
	            {
	            	randomSamples = Integer.parseInt(args.remove(0));
	            }
	            else if(cu.equals("--intra-chromosomal"))
	            {
	            	intraChromosomal=true;
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
		 
		 RsquaredRandomFramework r2f=new RsquaredRandomFramework(haplotypeFile,outputFile,randomSamples,intraChromosomal,logger);
		 r2f.run();
	}
	
	public static void printHelp()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("mode rsquared: calculates pairwise linkage disequilibrium between SNPs\n");
		sb.append("--haplotypes					the haplotypes\n");
		sb.append("--output					the output file\n");
		sb.append("--random-samples					the number of random pairs of SNPs to sample\n");
		sb.append("--intra-chromosomal				flag; sample random pairs of SNPs only on the same chromosome; default=inter-chromosomal\n");
		sb.append(MimicrEE.getGeneralHelpmessage());
		System.out.print(sb.toString());
		System.exit(1);
	}

}
