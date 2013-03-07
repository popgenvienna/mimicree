package analysis.hap2sum;

import java.util.*;
import java.util.logging.Logger;


public class Hap2SumParser {
	
	
	public static void parseCommandline(Logger logger, LinkedList<String> args)
	{
		ArrayList<String> haplotypeFiles=new ArrayList<String>();
		String outputFile="";
		String additiveFile="";
		String epistaticFile="";
		
		 while(args.size()>0)
	     {
	            String cu=args.remove(0);
	            
	            if(cu.equals("-h") || cu.equals("--haplotypes"))
	            {
	                haplotypeFiles.add(args.remove(0));
	            }
	            else if(cu.equals("--output"))
	            {
	            	outputFile=args.remove(0);
	            }
	            else if(cu.equals("--additive"))
	            {
	            	additiveFile=args.remove(0);
	            }
	            else if(cu.equals("--epistasis"))
	            {
	            	epistaticFile=args.remove(0);
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
		 
		 
		 Hap2SumFramework h2sf=new Hap2SumFramework(haplotypeFiles,additiveFile,epistaticFile,outputFile,logger);
		 h2sf.run();
	}
	
	public static void printHelp()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("mode hap2sum: convert haplotypes into a sync file\n");
		sb.append("-h|--haplotypes					the haplotypes\n");
		sb.append("--additive					additive SNP effects\n");
		sb.append("--epistatic					epistatic SNP effects\n");
		sb.append("--output					the output file (sync)\n");
		sb.append("--mode					the analysis mode of operation; see manual for supported modes\n");
		sb.append("--version					the analysis mode of operation; see manual for supported modes\n");
		sb.append("--detailed-log				print detailed log messages\n");
		sb.append("--help					print the help\n");
		System.out.print(sb.toString());
		System.exit(1);
	}

}
