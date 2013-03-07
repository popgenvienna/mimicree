package analysis.hap2sync;

import java.util.*;
import java.util.logging.Logger;

import mimicree.MimicrEE;

public class Hap2SyncParser {
	
	
	public static void parseCommandline(Logger logger, LinkedList<String> args)
	{
		ArrayList<String> haplotypeFiles=new ArrayList<String>();
		String outputFile="";
		
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
	            else if(cu.equals("--help"))
	            {
	            	printHelp();
	            }
	            else
	            {
	            	 throw new IllegalArgumentException("Do not recognize command line option "+cu);
	            }
	     }		
		 
		 
		 Hap2SyncFramework h2sf=new Hap2SyncFramework(haplotypeFiles,outputFile,logger);
		 h2sf.run();
	}
	
	public static void printHelp()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("mode hap2sync: convert haplotypes into a sync file\n");
		sb.append("-h|--haplotypes					the haplotypes\n");
		sb.append("--output					the output file (sync)\n");
		sb.append(MimicrEE.getGeneralHelpmessage());
		System.out.print(sb.toString());
		System.exit(1);
	}

}
